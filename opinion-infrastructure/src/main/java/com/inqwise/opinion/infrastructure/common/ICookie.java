package com.inqwise.opinion.infrastructure.common;

import org.json.JSONObject;


public interface ICookie {

	/*
	 StringBuffer sb = new StringBuffer();


        sb.append(escape(jo.getString("name")));
        sb.append("=");
        sb.append(escape(jo.getString("value")));
        if (jo.has("expires")) {
            sb.append(";expires=");
            sb.append(jo.getString("expires"));
        }
        if (jo.has("domain")) {
            sb.append(";domain=");
            sb.append(escape(jo.getString("domain")));
        }
        if (jo.has("path")) {
            sb.append(";path=");
            sb.append(escape(jo.getString("path")));
        }
        if (jo.optBoolean("secure")) {
            sb.append(";secure");
        }
        return sb.toString();

	 */
	
	String getDomain();

	Integer getMaxAge();

	String getName();

	String getPath();

	String getValue();

	boolean isSecure();

	void setValue(String value);
}
