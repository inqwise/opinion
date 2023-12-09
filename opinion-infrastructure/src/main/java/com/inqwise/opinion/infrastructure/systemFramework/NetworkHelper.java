package com.inqwise.opinion.infrastructure.systemFramework;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkHelper {
	public static final String X_FORWARDED_FOR_HEADER_NAME = "X-FORWARDED-FOR";

	private static final String internalIPs = 
			"166\\.41\\.8\\.X" + "|" +"12\\.16\\.X\\.X" + "|" +
			"12\\.22\\.X\\.X" + "|" +"132\\.23\\.X\\.X" + "|";

	private static final Pattern p = Pattern.compile("^(?:" + internalIPs.replace("X",
            "(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])")+")$");

	/*
	public static String getClientIp(HttpServletRequest request){
		String remoteAddr = request.getRemoteAddr();
		String xForwardedForHeader = request.getHeader(X_FORWARDED_FOR_HEADER_NAME);
		remoteAddr = identifyClientIp(remoteAddr, xForwardedForHeader);
		return remoteAddr;    
	}
	*/
	
	public static String identifyClientIp(String remoteAddr,
			String xForwardedForHeader) {
		if (null != xForwardedForHeader) {
			remoteAddr = xForwardedForHeader;
			String[] ipList = remoteAddr.split(",");
			for (String ipAddress : ipList) {
				remoteAddr = ipAddress;
				Matcher m = p.matcher(ipAddress);
				if (!m.matches()) break;
			}
		}
		return remoteAddr;
	}
	
	public static Integer ipAddressToInt(String clientIp){
		Integer result = null;
		try {
			InetAddress addr = InetAddress.getByName(clientIp);
			byte[] rawIPV = addr.getAddress();
			result = byteArrayToInt(rawIPV);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int byteArrayToInt(byte[] b) 
	{
	    return   b[3] & 0xFF |
	            (b[2] & 0xFF) << 8 |
	            (b[1] & 0xFF) << 16 |
	            (b[0] & 0xFF) << 24;
	}

	public static byte[] intToByteArray(int a)
	{
	    return new byte[] {
	        (byte) ((a >> 24) & 0xFF),
	        (byte) ((a >> 16) & 0xFF),   
	        (byte) ((a >> 8) & 0xFF),   
	        (byte) (a & 0xFF)
	    };
	}
	
	public static String getLocalHostName() {
		try {
			return java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "u-n-k";
		}
	}
}

