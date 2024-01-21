package com.inqwise.opinion.automation;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import com.inqwise.opinion.automation.common.errorHandle.AutomationOperationResult;
import com.inqwise.opinion.automation.common.jobs.JobSettings;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

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