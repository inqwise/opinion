package com.inqwie.opinion.api;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class Initializer {

	static ApplicationLog logger = ApplicationLog.getLogger(Initializer.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		start();
	}

	public static void start(){
		
		RestExpressService.start();
		logger.info("Add Shutdown Hook");
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { Initializer.stop(); }
		});

		logger.info("Started");
	}
	
	public static void stop(){
		RestExpressService.stop();
	}
}
