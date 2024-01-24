package com.inqwise.opinion.services;

import java.net.MalformedURLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.inqwise.opinion.automation.EventsServiceClient;

public class Initializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// Initialize eventProvider
		try {
			EventsServiceClient.getInstance();
		} catch (Exception e) {
			throw new Error("Unable to start EventProvider client.", e);
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
}
