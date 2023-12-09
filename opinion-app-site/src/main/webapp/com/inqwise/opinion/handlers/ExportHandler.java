package com.inqwise.opinion.handlers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.casper.data.model.CDataCacheContainer;
import net.casper.data.model.CDataGridException;
import net.casper.data.model.CDataRowSet;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.json.JSONException;
import org.json.JSONObject;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.JSONHelper;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.systemFramework.GeoIpManager;
import com.inqwise.opinion.opinion.common.ExportType;
import com.inqwise.opinion.opinion.common.IPostmasterContext;
import com.inqwise.opinion.opinion.common.opinions.IOpinion;
import com.inqwise.opinion.opinion.facade.front.MSExcelUtil;
import com.inqwise.opinion.opinion.managers.OpinionsManager;
import com.inqwise.opinion.opinion.managers.ResultsManager;


public class ExportHandler extends HandlerBase {

	//private static final Format formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
	private static final short maxColumnWidth = MSExcelUtil.pixel2WidthUnits(250);
	
	
	private static final String COLLECTOR_COLUMN_NAME = "Collector";
	private static final String PARTICIPANT_COLUMN_NAME = "Participant";
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 6709686893527006548L;
	private static final String PARTICIPANT_INSERT_DATE_COLUMN_NAME = "Date";
	private static final String CLIENT_IP_COLUMN_NAME = "IP Address";
	private static final String CLIENT_COUNTRY_COLUMN_NAME = "Country";
	private static final String COMPLETED_COLUMN_NAME = "Completed";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	} 
	
	@Override
	protected String getContentType() {
		return "application/CSV";
	}

	@Override
	protected SignInType getSignInType() {
		return SignInType.AfterLogin;
	}

	@Override
	protected byte[] process(JSONObject input, IPostmasterContext context)
			throws Exception {
		
		byte[] output = null;
		IOperationResult result = context.validateSignIn();
		context.setHeader("Expires:", "0"); // eliminates browser caching
		
		IAccount account = null;
		if(null == result) {
			OperationResult<IAccount> accountResult = context.getAccount();
			if(accountResult.hasError()){
				result = accountResult;
			} else {
				account = accountResult.getValue();
			}
		}
		
		IOpinion opinion = null;
		long opinionId = 0l;
		boolean zipped = false;
		
		if(null == result){
			opinionId = input.getLong("opinionId");
			zipped = input.optBoolean("zipped");
			
			OperationResult<IOpinion> opinionResult = OpinionsManager.getOpinion(opinionId, account.getId());
			if(opinionResult.hasError()){
				result = opinionResult;
			} else {
				opinion = opinionResult.getValue();
			}
		}
			
		if(null == result){
			ExportType exportType = ExportType.fromString(JSONHelper.optStringTrim(input, "exportType", ExportType.Participants.getValue()));
			switch(exportType){
				case Participants:
					output = exportParticipants(input, context, account, opinionId, opinion.isRtl(), zipped);
					break;
				case Opinion:
					output = exportOpinion(input, context, account, opinionId, zipped);
					break;
				default:
					throw new Error("Unexpected exportType " + exportType);
			}
		} else {
			output = result.toJson().toString().getBytes();
		}
		return output;
	}

	private byte[] exportOpinion(JSONObject input, IPostmasterContext context,
			IAccount account, long opinionId, boolean zipped) throws IOException, JSONException {
		
		byte[] result;
		String fileName = JSONHelper.optStringTrim(input, "name", "opinion#"+ opinionId);
		
		String strOutput = exportOpinion(input, account, opinionId).toString();
		if(zipped){
			context.setContentType("application/zip");
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".zip\"");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			ZipEntry ze = new ZipEntry(fileName +".opinion.json");
    		zos.putNextEntry(ze);
    		zos.write(strOutput.getBytes());
			result = baos.toByteArray();
		} else {
			context.setContentType("application/json");
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".opinion.json\"");
			
			result = strOutput.getBytes();
		}
		
		return result;
	}

	private byte[] exportParticipants(JSONObject input,
			IPostmasterContext context, IAccount account, long opinionId, boolean isRtl, boolean zipped)
			throws CDataGridException, IOException {
		byte[] output;
		List<Long> sessionIds = JSONHelper.toListOfLong(JSONHelper.optJsonArray(input, "sessionIds")); 
		Long[] sessionsArr = null == sessionIds ? null : (Long[]) sessionIds.toArray(new Long[sessionIds.size()]);
		boolean includePartial = JSONHelper.optBoolean(input, "includePartial", true);
		
		TreeMap <Long, Integer> headerIdsMap = new TreeMap<>();
		
		CDataCacheContainer ds = ResultsManager.getAllResults(opinionId, account.getId(), sessionsArr, includePartial, headerIdsMap);
		
		String[] dynamicColumnsHeader = new String[headerIdsMap.size()];
		
		
		CDataRowSet rows = ds.getAll();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> dict = null;
		LinkedHashMap<String, CellProcessor> headerSet = new LinkedHashMap<>();
		headerSet.put(COLLECTOR_COLUMN_NAME, new NotNull());
		headerSet.put(PARTICIPANT_COLUMN_NAME, new NotNull(new NotNull()));
		headerSet.put(PARTICIPANT_INSERT_DATE_COLUMN_NAME, new org.supercsv.cellprocessor.FmtDate("MMM dd, yyyy HH:mm:ss"));
		headerSet.put(CLIENT_IP_COLUMN_NAME, new NotNull());
		headerSet.put(CLIENT_COUNTRY_COLUMN_NAME, new NotNull());
		
		if(includePartial){
			headerSet.put(COMPLETED_COLUMN_NAME, new NotNull());
		}
		
		String participantGuid = null;
		while(rows.next()){
			String currentParticipantGuid = rows.getString("answer_session_id");
			if(null == dict || (!participantGuid.equals(currentParticipantGuid))){
				dict = new LinkedHashMap<String, Object>();
				participantGuid = currentParticipantGuid;
				list.add(dict); 
				
				// Add required columns:
				dict.put(PARTICIPANT_COLUMN_NAME, currentParticipantGuid);
				dict.put(COLLECTOR_COLUMN_NAME, rows.getString("collector_name") + " #" + rows.getLong("collector_id"));
				dict.put(PARTICIPANT_INSERT_DATE_COLUMN_NAME, account.addDateOffset(rows.getDate("participant_insert_date")));
				String clientIp = rows.getString("client_ip");
				String countryName = null;
				try{
					countryName = GeoIpManager.getInstance().getCountryName(clientIp);
				} catch(IOException ex){
					logger.error(ex, "getCountryName() : Unable to load GeoIp DB");
				}
				dict.put(CLIENT_IP_COLUMN_NAME, clientIp);
				dict.put(CLIENT_COUNTRY_COLUMN_NAME, countryName);
				if(includePartial){ 
					dict.put(COMPLETED_COLUMN_NAME, rows.getBoolean("is_completed"));
				}
			}
			String columnPrefix = rows.getString("control_key");
			if(null == columnPrefix){
				columnPrefix = rows.getString("control_content");
			}
			Long controlId = rows.getLong("control_id");
			String columnName = columnPrefix + " #" + controlId;
			
			Integer index = headerIdsMap.get(controlId);
			if(null != index) { // The control has not been deleted
				if(null == dynamicColumnsHeader[index]){
					dynamicColumnsHeader[index] = columnName;
				}
				
				dict.put(columnName, rows.getString("answer_value"));
			}
		}
		
		for (int i = 0; i < dynamicColumnsHeader.length; i++) {
			String columnName = dynamicColumnsHeader[i];
			headerSet.put(columnName, new Optional()); // add if not exist
		}
		
		String[] header = (String[]) headerSet.keySet().toArray(new String[headerSet.size()]);
		String fileName = JSONHelper.optString(input, "name", "overallResults#"+ opinionId);
		switch (JSONHelper.optString(input, "format", "csv")) {
		case "excel":
			output = makeXlsx(header, list, context, opinionId, fileName, isRtl, zipped);
			break;
		default:
			output = makeCsv(context, opinionId, list, headerSet, header, fileName, zipped);
			break;
		}
		return output;
	}

	private byte[] makeCsv(IPostmasterContext context, long opinionId,
			List<Map<String, Object>> list,
			LinkedHashMap<String, CellProcessor> headerSet, String[] header, String fileName, boolean zipped)
			throws IOException {
		byte[] output;
		
		if(zipped){
			context.setContentType("application/zip");
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".zip\"");
		} else {
			context.setContentType(getContentType());
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".csv\"");
		}
		
		ICsvMapWriter mapWriter = null;
		Path path = Paths.get(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString() + ".csv");
		logger.debug("ParticipantsExcelHandler: tempFilePath: '%s'", path.toString());
		
		OutputStreamWriter sw = new OutputStreamWriter( new FileOutputStream(path.toString()),Charset.forName("UTF-8"));
		sw.write('\ufeff');	
		
		try {
			mapWriter = new CsvMapWriter(sw, CsvPreference.STANDARD_PREFERENCE);
			CellProcessor[] processors = headerSet.values().toArray(new CellProcessor[headerSet.size()]);
			
			// write the header
			mapWriter.writeHeader(header);
			
			for(Map<String, Object> item : list){
				mapWriter.write(item, header, processors);
			}
		} finally {
			if( mapWriter != null ) {
			mapWriter.close();
			}
		}
		
		if(zipped){
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			ZipEntry ze = new ZipEntry(fileName + ".csx");
    		zos.putNextEntry(ze);

    		FileInputStream in = new FileInputStream(path.toString());
    		 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
    		
    		in.close();
    		zos.closeEntry();
    		zos.close();
    		
    		output = baos.toByteArray();
		} else {
			output = Files.readAllBytes(path);
			Files.deleteIfExists(path);
		}
		
		
		return output;
	}
	
	private byte[] makeXlsx(String[] header, List<Map<String, Object>> dataRows, IPostmasterContext context, long opinionId, String fileName, boolean isRtl, boolean zipped) throws IOException{
		
		if(zipped){
			context.setContentType("application/zip");
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".zip\"");
		} else {
			context.setContentType("application/vnd.openxml");
			context.setHeader("Content-Disposition", "attachment;filename=\""+ fileName +".xlsx\"");
		}
		
		byte[] output;
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		
		CreationHelper cHelper = wb.getCreationHelper();
		Sheet sh = wb.createSheet("Overall Results");
		sh.setRightToLeft(isRtl);
		
		int rownum = 0;
		sh.setRepeatingRows(new CellRangeAddress(0, 0, 0, 0));
		// Create header
		Row row = sh.createRow(rownum++);
				
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 12);
		f.setColor( IndexedColors.BLACK.getIndex() );
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// Set cell style and formatting
		cs.setFont(f);
		//cs.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		cs.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		
		for(int cellnum = 0; cellnum < header.length; cellnum++){
			String columnName = StringEscapeUtils.escapeHtml4(header[cellnum]);
			
			Cell cell = row.createCell(cellnum);
			cell.setCellStyle(cs);
			cell.setCellValue(columnName);
			sh.autoSizeColumn(cellnum, true);
			
			if(sh.getColumnWidth(cellnum) > maxColumnWidth){
				sh.setColumnWidth(cellnum, maxColumnWidth);
				
			}
		}

		for (Map<String, Object> dataRow : dataRows) {
			row = sh.createRow(rownum++);
			
			for(int cellnum = 0; cellnum < header.length; cellnum++){
				String columnName = header[cellnum];
				
				Cell cell = row.createCell(cellnum);
				Object cellObject = dataRow.get(columnName);
				if(null != cellObject){
					if(cellObject instanceof Date){
						CellStyle cellStyle = wb.createCellStyle();
						cellStyle.setDataFormat(cHelper.createDataFormat().getFormat("m/d/yyyy"));
						cell.setCellValue((Date)cellObject);
						cell.setCellStyle(cellStyle);
					} else if(cellObject instanceof Double){
						cell.setCellValue((Double)cellObject);
					} else {
						cell.setCellValue(cHelper.createRichTextString(cellObject.toString()));
					}
				}
			}
		}
		
		sh.setAutoFilter(new CellRangeAddress(0, rownum, 0, header.length-1));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream out = null;
		
		if(zipped){
			@SuppressWarnings("resource")
			ZipOutputStream zos = new ZipOutputStream(baos);
			ZipEntry ze = new ZipEntry(fileName + ".xlsx");
    		zos.putNextEntry(ze);
    		out = zos;
		} else {
			out = baos;
		}
		
		try{
			wb.write(out);
		} finally {
			out.close();
		}
		
		output = baos.toByteArray();
		
		
		// dispose of temporary files backing this workbook on disk
		wb.dispose();
		
		return output;
	}
	
	public JSONObject exportOpinion(JSONObject input, IAccount account, long opinionId) throws IOException, JSONException{
		JSONObject output = null;
		BaseOperationResult result = null;
		IOpinion opinion = null;
		
		if (null == result) {
			OperationResult<IOpinion> getSurveyResult = OpinionsManager
					.getOpinion(opinionId, account.getId());
			if (getSurveyResult.hasError()) {
				result = getSurveyResult;
			} else {
				opinion = getSurveyResult.getValue();
			}
		}
		
		if(null == result){
			output = opinion.getExportJson();
		} else {
			output = result.toJson();
		}
		
		return output;
	}

	@Override
	protected NotSignInBehaviour getNotSignInBehaviour() {
		return NotSignInBehaviour.Error;
	}
}
