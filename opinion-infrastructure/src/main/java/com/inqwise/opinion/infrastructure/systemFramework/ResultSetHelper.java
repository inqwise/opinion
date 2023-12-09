package com.inqwise.opinion.infrastructure.systemFramework;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import javax.print.DocFlavor.STRING;

import org.json.JSONException;
import org.json.JSONObject;


public final class ResultSetHelper {
	
	public static String optString(ResultSet reader, String key) throws SQLException{
		return optString(reader, key, null);
	}
	
	public static String optString(ResultSet reader, String key, String defaultValue) throws SQLException{
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : reader.getString(key);
	}

	public static <T> T opt(ResultSet reader, String key, Class<T> c) throws SQLException{
		return opt(reader, key, (T)null);
	}
	
	public static <T> T opt(ResultSet reader, String key, T defaultValue) throws SQLException{
		return null == reader.getObject(key) ? defaultValue : (T)reader.getObject(key);
	}
	
	public static Long optLong(ResultSet reader, String key) throws SQLException{
		return optLong(reader, key, null);
	}
	
	public static Long optLong(ResultSet reader, String key, Long defaultValue) throws SQLException{
		return null == reader.getObject(key) ? defaultValue : Long.valueOf(reader.getLong(key));
	}

	public static Integer optInt(ResultSet reader, String key, Integer defaultValue) throws SQLException {
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : Integer.valueOf(reader.getInt(key));
	}

	public static boolean exists(ResultSet reader, String key) throws SQLException{
		for (int i = 1; i <= reader.getMetaData().getColumnCount(); i++) {
			String actualKey = reader.getMetaData().getColumnLabel(i);
			if(key.equals(actualKey)){
				return true;
			}
		}
		return false;
	}
	
	public static Integer optInt(ResultSet reader, String key) throws SQLException {
		return optInt(reader, key, null);
	}

	public static Date optDate(ResultSet reader, String key) throws SQLException{
		return optDate(reader, key, null);
	}
	
	public static Date optDate(ResultSet reader, String key, Date defaultValue) throws SQLException {
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : Convert.toDate(reader.getTimestamp(key));
	}

	public static Boolean optBool(ResultSet reader, String key) throws SQLException {
		return optBool(reader, key, null);
	}
	
	public static Boolean optBool(ResultSet reader, String key, Boolean defaultValue) throws SQLException {
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : Boolean.valueOf(reader.getBoolean(key));
	}

	public static JSONObject optJsonObject(ResultSet reader, String key) throws SQLException, JSONException {
		return optJsonObject(reader, key, null);
	}
	public static JSONObject optJsonObject(ResultSet reader, String key, JSONObject defaultValue) throws SQLException, JSONException {
		return null == reader.getObject(key) ? defaultValue : new JSONObject(reader.getString(key));
	}

	public static UUID optUUID(ResultSet reader, String key) throws SQLException {
		return optUUID(reader, key, null);
	}
	
	public static UUID optUUID(ResultSet reader, String key, UUID defaultValue) throws SQLException {
		return null == reader.getObject(key) ? defaultValue : UUID.fromString(reader.getString(key));
	}

	public static BigDecimal getDecimal(ResultSet reader, String key) throws SQLException {
		return BigDecimal.valueOf(reader.getDouble(key));
	}
	
	public static BigDecimal optBigDecimal(ResultSet reader, String key) throws SQLException {
		return optBigDecimal(reader, key, null);
	}
	
	public static BigDecimal optBigDecimal(ResultSet reader, String key, BigDecimal defaultValue) throws SQLException {
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : BigDecimal.valueOf(reader.getDouble(key));
	}

	public static Double optDouble(ResultSet reader, String key) throws SQLException {
		return optDouble(reader, key, null);
	}
	
	public static Double optDouble(ResultSet reader, String key, Double defaultValue) throws SQLException {
		return !exists(reader, key) || null == reader.getObject(key) ? defaultValue : Double.valueOf(reader.getDouble(key));
	}
}
