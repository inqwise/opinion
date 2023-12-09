package com.inqwise.opinion.opinion.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.inqwise.opinion.infrastructure.dao.IDataFillable;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.ResultSetHelper;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.opinion.common.ITheme;
import com.inqwise.opinion.opinion.common.opinions.OpinionType;
import com.inqwise.opinion.opinion.dao.ThemesDataAccess;

public class ThemeEntity implements ITheme {
	public static ApplicationLog logger = ApplicationLog.getLogger(ThemeEntity.class);
	
	private long id;
	private String name;
	private boolean isTemplate;
	private OpinionType opinionType;
	private String cssContent;
	
	public ThemeEntity(ResultSet reader) throws SQLException {
		setId(reader.getInt("theme_id"));
		setName(ResultSetHelper.optString(reader, "theme_name"));
		setTemplate(reader.getBoolean("is_template"));
		setOpinionType(OpinionType.fromInt(reader.getInt("opinion_type_id")));
		setCssContent(ResultSetHelper.optString(reader, "css_content"));
	}

	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.ITheme#getId()
	 */
	@Override
	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.inqwise.opinion.opinion.entities.ITheme#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	@Override
	public boolean isTemplate() {
		return isTemplate;
	}
	
	@Override
	public String getCssContent(){
		return cssContent;
	}
	
	public OpinionType getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(OpinionType opinionType) {
		this.opinionType = opinionType;
	}

	public void setCssContent(String cssContent) {
		this.cssContent = cssContent;
	}

	@Override
	public JSONObject getExportJson() throws JSONException {
		JSONObject output = new JSONObject();
		output.put(JsonNames.CSS_CONTENT, getCssContent());
		output.put(JsonNames.NAME, getName());
		output.put(JsonNames.OPINION_TYPE_ID, getName());
		
		return output;
	}
}
