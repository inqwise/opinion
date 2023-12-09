package com.inqwise.opinion.library.entities;

import java.util.*;
import javax.servlet.http.*;

/**
 * A request object with a new param set (all other params hidden)
 */
public class LocalParamHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public LocalParamHttpServletRequestWrapper(HttpServletRequest request) {
	super(request);
	m = new HashMap();
    }

    public void setQueryString(String s) {
	qs = s;
    }

    public String getQueryString() {
	return qs;
    }

    public void setParameterMap(Map m) {
	this.m = m;
    }

    public void setParameterValue(String key, String val) {
	m.put(key, new String[] { val });
    }

    public void setParameterValues(String key, String[] val) {
	m.put(key, val);
    }

    public Map getParameterMap() {
	return m;
    }

    public Enumeration getParameterNames() {
	return Collections.enumeration(m.keySet());
    }

    public String[] getParameterValues(String key) {
	return (String[])m.get(key);
    }

    public String getParameter(String key) {
	String res = null;
	String[] v = (String[])getParameterValues(key);
	if (v != null)
	    res = v[0];
	return res;
    }

    private Map m;
    private String qs;

}