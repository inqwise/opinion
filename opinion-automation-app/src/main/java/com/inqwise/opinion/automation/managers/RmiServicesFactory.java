package com.inqwise.opinion.automation.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.inqwise.opinion.automation.services.RmiServiceBase;
import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

public class RmiServicesFactory {
	private static ApplicationLog logger = ApplicationLog.getLogger(RmiServicesFactory.class);
	private static LinkedHashMap<Class<?>, RmiServiceBase> _startedServices = new LinkedHashMap<>();
	private static Object _instanceLocker = new Object();
	
	public static <T extends RmiServiceBase> T startService(Registry reg, Class<T> serviceClass, Class<?> serviceContractClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, RemoteException, MalformedURLException, AlreadyBoundException{
		
		T service = null;
		if(!_startedServices.containsKey(serviceContractClass)){
			synchronized (_instanceLocker) {
				if(!_startedServices.containsKey(serviceContractClass)){
					rmiStarter(serviceContractClass);
					service = (T)serviceClass.newInstance();
					String serviceName = serviceContractClass.getSimpleName();
					try{						// ... and bind it with the RMI Registry
						reg.rebind(serviceName, service);
					} catch (ConnectException ex){
						logger.error(ex, "startService: rmiregistry not executed");
						throw(ex);
					}
					
					_startedServices.put(serviceContractClass, service);
				}
			}
		}
		return service;
	}
	
	
	
	public static RmiServiceBase stopService(Registry reg, Class<?> serviceContractClass){
		RmiServiceBase instance = null;
		
		if(_startedServices.containsKey(serviceContractClass)){
			synchronized (_instanceLocker) {
				if(_startedServices.containsKey(serviceContractClass)){
					instance = _startedServices.remove(serviceContractClass);
					String serviceName = serviceContractClass.getSimpleName();
					try {
						reg.unbind (serviceName);
					} catch (Exception e) {
						logger.error(e, "stopService: Failed to unbound '%s'", serviceContractClass.getName());
					}
				}
			}
		}
		return instance;
	}
	
	public static final String POLICY_FILE_NAME = "/security.policy";
	private static String getLocationOfPolicyFile() {
		try {            
			File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".policy");            
			InputStream is = RmiServiceBase.class.getResourceAsStream(POLICY_FILE_NAME);            
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));            
			int read = 0;            
			while((read = is.read()) != -1) {                
				writer.write(read);            
			}            
			writer.close();            
			tempFile.deleteOnExit();            
			return tempFile.getAbsolutePath();        
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void rmiStarter(Class<?> clazzToAddToServerCodebase) {
		
		StringBuilder codebase = new StringBuilder();
		codebase.append(clazzToAddToServerCodebase.getProtectionDomain().getCodeSource().getLocation());
		codebase.append(" ");
		codebase.append(IOperationResult.class.getProtectionDomain().getCodeSource().getLocation());
		//codebase.append(" ");
		//codebase.append("file:/C:\\bin");
		System.setProperty("java.rmi.server.codebase", codebase.toString());
		//System.setProperty("java.rmi.server.codebase", clazzToAddToServerCodebase.getProtectionDomain().getCodeSource().getLocation().toString());
		
		logger.info("codebase: '%s'", System.getProperty("java.rmi.server.codebase"));
		System.setProperty("java.security.policy", getLocationOfPolicyFile());
		logger.info("policy: '%s'", System.getProperty("java.security.policy"));
		if(System.getSecurityManager() == null) {            
			System.setSecurityManager(new SecurityManager());
		}        
	}


	public static void stopAll(Registry reg) {
		while(!_startedServices.isEmpty()){
			Class<?> key = _startedServices.keySet().iterator().next();
			stopService(reg, key);
		}
	}
}
