package com.inqwise.opinion.infrastructure.systemFramework;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

import com.maxmind.db.MaxMindDbConstructor;
import com.maxmind.db.MaxMindDbParameter;
import com.maxmind.db.Reader;

public class GeoIpManager {
	static final ApplicationLog logger = ApplicationLog.getLogger(GeoIpManager.class);
	
	private static GeoIpManager instance = null;
	private File database;
	
	public static synchronized GeoIpManager getInstance() throws IOException {
		if(instance == null) {
			instance = new GeoIpManager();
		} 
		return instance;
	}
	
	GeoIpManager() throws IOException {
		this(BaseApplicationConfiguration.GeoIp.getGeoIpPath());
	}
	
	GeoIpManager(String path) throws IOException{
		database = new File(path);
	}
	
	public String getCountryName(String ipAddress) throws UnknownHostException, IOException{
		try (Reader reader = new Reader(database)) {
			LookupResult result = reader.get(InetAddress.getByName(ipAddress), LookupResult.class);
			if(null == result) return null;
			return result.getCountry().getName();
        }
	}

	public String getCountryCode(String ipAddress) throws UnknownHostException, IOException {
		try (Reader reader = new Reader(database)) {
			LookupResult result = reader.get(InetAddress.getByName(ipAddress), LookupResult.class);
			if(null == result) return null;
			return result.getCountry().getIsoCode();
        }
	}
	
	public static class LookupResult {
        private final Country country;

        @MaxMindDbConstructor
        public LookupResult (@MaxMindDbParameter(name="country") Country country) {
            this.country = country;
        }

        public Country getCountry() {
            return this.country;
        }
    }

    public static class Country {
        private final String isoCode;
		private Map<String, String> names;

        @MaxMindDbConstructor
        public Country (@MaxMindDbParameter(name="iso_code") String isoCode, @MaxMindDbParameter(name="names") Map<String,String> names) {
            this.isoCode = isoCode;
			this.names = names;
        }

        public String getIsoCode() {
            return this.isoCode;
        }
        
        public String getName() {
            return this.names.get("en");
        }
        
        public Set<String> getRegions() {
            return this.names.keySet();
        }
    }
}
