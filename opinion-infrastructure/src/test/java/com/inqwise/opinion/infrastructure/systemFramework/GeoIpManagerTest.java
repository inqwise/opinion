package com.inqwise.opinion.infrastructure.systemFramework;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeoIpManagerTest {
	static final ApplicationLog logger = ApplicationLog.getLogger(GeoIpManagerTest.class);
	private GeoIpManager geoIpManager;
	
	@BeforeEach
	protected void setUp() throws Exception {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		String path = URLDecoder.decode(Objects.requireNonNull(cl.getResource("GeoLite2-Country.mmdb").getPath()), Charset.defaultCharset());
		logger.debug("path: %s", path);
		
		geoIpManager = new GeoIpManager(path);
	}
	
	@Test
	void testCountryCode() throws Exception {
		logger.debug("testCountryCode");
		var countryCode = geoIpManager.getCountryCode("1.0.0.0");
		Assertions.assertEquals("AU", countryCode);
	}
	
	@Test
	void testCountryName() throws Exception {
		logger.debug("testCountryName");
		
		var countryName = geoIpManager.getCountryName("1.0.0.0");
		Assertions.assertEquals("Australia", countryName);
	}
	
	@Test
	void testInvalidIp() throws Exception {
		logger.debug("testInvalidIp");
		
		var countryName = geoIpManager.getCountryName("255.255.255.255");
		Assertions.assertNull(countryName);
	}
}
