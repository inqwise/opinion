package com.inqwise.opinion.infrastructure.systemFramework;

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.Objects;
import java.util.UUID;

public class ApplicationLog {

	
	public static ApplicationLog getLogger(Class<?> type){
		return new ApplicationLog(type);
	}
	
	public static ApplicationLog getLogger(String name){
		return new ApplicationLog(name);
	}
	
	public static ApplicationLog getLogger(){
		return new ApplicationLog(Objects.requireNonNull(getCallerClass(2), "callerClass"));
	}
	
	public static Class<?> getCallerClass(){
		return getCallerClass(1);
	}
	
	public static Class<?> getCallerClass(int depth){
		return
		StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
				.walk(stream -> stream.map(StackFrame::getDeclaringClass)
                .skip(depth)
                .findFirst().orElse(null));
	}
	
	private org.apache.logging.log4j.Logger log;
	
	protected ApplicationLog(Class<?> type) {
		log = org.apache.logging.log4j.LogManager.getLogger(type);
	}
	
	protected ApplicationLog(String name) {
		log = org.apache.logging.log4j.LogManager.getLogger(name);
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
