package com.inqwise.opinion.infrastructure.systemFramework;

import java.util.UUID;

import org.apache.log4j.Logger;

public class ApplicationLog{

	
	public static ApplicationLog getLogger(Class<?> clazz){
		return new ApplicationLog(clazz);
	}
	
	org.apache.log4j.Logger log;
	
	protected ApplicationLog(Class<?> clazz) {
		log = Logger.getLogger(clazz);
	}

	public UUID error(Throwable t, String message){
		UUID errorId = UUID.randomUUID();
		log.error(message + "  :" + errorId, t);
		return errorId;
	}

	public UUID error(Throwable t, String format, Object... args){
		String formattedMessage = String.format(format, args);
		UUID errorId = UUID.randomUUID();
		log.error(formattedMessage + "  :" + errorId, t);
		return errorId;
	}
	
	public UUID error(String format, Object... args){
		String formattedMessage = String.format(format, args);
		UUID errorId = UUID.randomUUID();
		log.error(formattedMessage + "  :" + errorId);
		return errorId;
	}
	
	public void debug(String format, Object... args){
		log.debug(String.format(format, args));
	}
	
	public void info(String format, Object... args){
		log.info(String.format(format, args));
	}
	
	public UUID error(String message){
		UUID errorId = UUID.randomUUID();
		log.error(message + "  :" + errorId);
		return errorId;
	}
	
	public void debug(String message){
		log.debug(message);
	}
	
	public void info(String message){
		log.info(message);
	}

	public void warn(String string) {
		log.warn(string);
	}
	
	public void warn(String format, Object... args){
		log.warn(String.format(format, args));
	}
	
	public boolean IsDebugEnabled(){
		return log.isDebugEnabled();
	}
	
	public boolean IsInfoEnabled(){
		return log.isInfoEnabled();
	}
}
