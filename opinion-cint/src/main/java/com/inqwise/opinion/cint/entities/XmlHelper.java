package com.cint.entities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cint.common.IXmlOwnerCallback;

class XmlHelper {
	public static void parse(Element element, IXmlOwnerCallback callback){
    	NodeList nodes = element.getChildNodes();
    	if(null != nodes){
	    	for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if(Node.ELEMENT_NODE == node.getNodeType()){
					Element actualElement = (Element)node; 
					callback.onElementFound(actualElement);
				}
			}
    	}
    }
	
	private static DateFormat _formater;
	public static Date parseDate(String input){
		if(null == _formater){
			_formater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		}
		
		try {
			return (null == input ? null : _formater.parse(input));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
