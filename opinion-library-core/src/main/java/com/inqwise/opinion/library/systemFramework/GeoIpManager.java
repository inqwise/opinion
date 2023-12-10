package com.inqwise.opinion.library.systemFramework;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.maxmind.geoip.LookupService;

public class GeoIpManager {
	static final ApplicationLog logger = ApplicationLog.getLogger(GeoIpManager.class);
	private static GeoIpManager instance = null;
	private boolean done = false;
	
	public static GeoIpManager getInstance() throws IOException {
		if(instance == null) {
			instance = new GeoIpManager();
		} else if(instance.done){
        	try {
				instance.finalize();
			} catch (Throwable t) {
				logger.error(t, "getInstance() : finilize failed.");
			}
        	instance = new GeoIpManager();
		}
		return instance;
	}
	
	private GeoIpManager() throws IOException{
		 cl = new LookupService(ApplicationConfiguration.GeoIp.getGeoIpPath(),
				LookupService.GEOIP_STANDARD );
	}
	
	protected void finalize() throws Throwable
	{
		finish();
	}
	
	public void finish(){
		if(!done){
			done = true;
		}
	}
	
	public String getCountryName(String ipAddress){
		return cl.getCountry(ipAddress).getName();
	}

	public String getCountryCode(String ipAddress) {
		return cl.getCountry(ipAddress).getCode();
	}
	
	public String getCountryCodeOrNull(String ipAddress) {
		return StringUtils.trimToNull(StringUtils.remove(cl.getCountry(ipAddress).getCode(), "--"));
	}
}
