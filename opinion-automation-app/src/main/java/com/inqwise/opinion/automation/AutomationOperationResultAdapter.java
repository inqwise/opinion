package com.inqwise.opinion.automation;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.JobSettings;

public class AutomationOperationResultAdapter extends XmlAdapter<String, AutomationOperationResult<?>>{

	@Override
	public String marshal(AutomationOperationResult<?> v) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JAXBContext context = JAXBContext.newInstance(AutomationOperationResult.class, JobSettings[].class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(v,System.out);
		return out.toString();
	}

	@Override
	public AutomationOperationResult<?> unmarshal(String v) throws Exception {
		JAXBContext context = JAXBContext.newInstance(AutomationOperationResult.class, JobSettings[].class);
		Unmarshaller u = context.createUnmarshaller();
		return (AutomationOperationResult<?>) u.unmarshal(new StringReader(v));
	}
       
}