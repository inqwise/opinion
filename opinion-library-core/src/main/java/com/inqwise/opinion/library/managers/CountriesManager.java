package com.inqwise.opinion.library.managers;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.inqwise.opinion.infrastructure.dao.IResultSetCallback;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.infrastructure.systemFramework.GeoIpManager;
import com.inqwise.opinion.library.common.countries.ICountry;
import com.inqwise.opinion.library.common.countries.IStateProvince;
import com.inqwise.opinion.library.common.countries.ITimeZone;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.dao.CountriesDataAccess;
import com.inqwise.opinion.library.entities.CountryEntity;
import com.inqwise.opinion.library.entities.StateProviceEntity;
import com.inqwise.opinion.library.entities.TimeZoneEntity;

public class CountriesManager {
	private static ApplicationLog logger = ApplicationLog.getLogger(CountriesManager.class);
	
	private static Map<String, ICountry> countriesByIso2;
	private static List<ICountry> countries;
	public static ICountry getCountryByIp(String ipAddress) throws IOException{
		
		String countryCode = GeoIpManager.getInstance().getCountryCode(ipAddress);
		
		if(null == countriesByIso2){
			synchronized (CountriesManager.class) {
				if(null == countriesByIso2){
					OperationResult<List<ICountry>> countriesResult = getCountries();
					if(countriesResult.hasError()){
						throw new Error(countriesResult.toString());
					} else {
						List<ICountry> countries = countriesResult.getValue();
						countriesByIso2 = new TreeMap<String, ICountry>();
						for (ICountry country : countries) {
							countriesByIso2.put(country.getIso2(), country);
						}
					}
				}
			}
		}
		
		if(null == countryCode){
			return null;
		} else {
			return countriesByIso2.get(countryCode);
		}
	}
	
	public static OperationResult<List<ICountry>> getCountries(){
		OperationResult<List<ICountry>> result = new OperationResult<List<ICountry>>();
		try{
			final List<ICountry> countries = new ArrayList<ICountry>();
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							ICountry country = new CountryEntity(reader);
							countries.add(country);							
						}
					}
				}
			};
			
			CountriesDataAccess.getCountries(callback);
			
			if(countries.size() == 0){
				result.setError(ErrorCode.NoResults);
			} else {
				result.setValue(countries);
			}
			
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getCountries() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<List<IStateProvince>> getStatesProvinces(Integer countryId){
		OperationResult<List<IStateProvince>> result = new OperationResult<List<IStateProvince>>();
		try{
			
			final List<IStateProvince> states = new ArrayList<IStateProvince>();
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							IStateProvince state = new StateProviceEntity(reader);
							states.add(state);							
						}
					}
				}
			};
			
			CountriesDataAccess.getStatesProvinces(callback, countryId);
			
			if(states.size() == 0){
				result.setError(ErrorCode.NoResults);
			} else {
				result.setValue(states);
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getStatesProvinces() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		
		return result;
	}
	
	public static OperationResult<List<ITimeZone>> getTimeZones(){
		OperationResult<List<ITimeZone>> result = new OperationResult<List<ITimeZone>>();
		try{
			final List<ITimeZone> list = new ArrayList<ITimeZone>();
			IResultSetCallback callback = new IResultSetCallback() {
				
				public void call(ResultSet reader, int generationId) throws Exception {
					while(reader.next()){
						if(0 == generationId){
							ITimeZone timezone = new TimeZoneEntity(reader);
							list.add(timezone);							
						}
					}
				}
			};
			
			CountriesDataAccess.getTimeZones(callback);
			
			if(list.size() == 0){
				result.setError(ErrorCode.NoResults);
			} else {
				result.setValue(list);
			}
		} catch(Exception ex){
			UUID errorId = logger.error(ex, "getTimeZones() : Unexpected error occured.");
			result.setError(ErrorCode.GeneralError, errorId);
		}
		return result;
	}
}
