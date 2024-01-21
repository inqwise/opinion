package com.inqwise.opinion.htmlto;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.htmlto.HtmlConverterService;
import com.inqwise.opinion.htmlto.HtmltoType;

public class HtmltoTestHandler extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7790875292838093204L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		HtmltoType toType = HtmltoType.Pdf;
		String documentContent = "<div>Hello htmlto</div>";
		String name = "test";
		
		try {
			ServletOutputStream outStream = response.getOutputStream();
			URL url = new URL("http://localhost:9999/ws/htmlto?wsdl");
	        QName qname = new QName("", "", "HtmlConverterService", "http://services.htmlto.opinion.com/");
	        
	        Service service = Service.create(url, javax.xml.namespace.QName.valueOf(name));
	        HtmlConverterService converterService = service.getPort(HtmlConverterService.class);
	        
			byte[] file = converterService.convert(documentContent, toType);
			//switch(toType) {
			//	case Pdf : 
					response.setHeader("Content-Disposition"," attachment; filename=\"" + name + ".PDF\"");
					outStream.write(file);
					if(null != file){
						response.setContentType("application/octet-stream");
					}
			//		break;
			//	case Jpeg : 
			//		response.setHeader("Content-Disposition"," attachment; filename=\"" + name + ".JPEG\"");
			//		result = ParsersManager.htmlToJpeg(outStream, ParsersManager.cleanHtml(documentContent, request.getCharacterEncoding()));
			//		if(!result.hasError()){
			//			response.setContentType("image/jpeg");
			//		}
			//		break;
			//	default :
			//		result = new BaseHtoOperationResult(HtoErrorCode.ArgumentWrong, "Unimplemented type received");
			//		break;
			//}
			
			//if(result.hasError()){
			//	response.setHeader("Content-Disposition", null);
			//	outStream.write(result.toString().getBytes());
			//}
			
			//outStream.close();
			//*/
			
		} catch(Throwable t){
			t.printStackTrace();
	    }
	}

}
