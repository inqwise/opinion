package com.inqwise.opinion.automation.common;

import java.io.Serializable;
import java.util.UUID;

public abstract class FireEventArgs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5620485583754365498L;
	public FireEventArgs(int sourceId) {
		this.sourceId = sourceId;
	}

	private int sourceId;
	public int getSourceId(){
		return sourceId;
	}
	
	private ClientArgs client;
	public ClientArgs getClient(){
		return client;
	}
	
	public FireEventArgs withClient(int sourceId, String sessionId,
			String geoCountryCode, String clientIp){
		this.client = new ClientArgs(sourceId, sessionId, geoCountryCode, clientIp);
		return this;
	}
	
	public class ClientArgs implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5543965875595087002L;
		private int sourceId;
		public ClientArgs(int sourceId, String sessionId,
				String geoCountryCode, String clientIp) {
			super();
			this.sourceId = sourceId;
			this.sessionId = sessionId;
			this.geoCountryCode = geoCountryCode;
			this.clientIp = clientIp;
		}
		private String sessionId;
		private String geoCountryCode;
		private String clientIp;
		
		public int getSourceId() {
			return sourceId;
		}
		public String getSessionId() {
			return sessionId;
		}
		public String getGeoCountryCode() {
			return geoCountryCode;
		}
		public String getClientIp() {
			return clientIp;
		}
	} 
}
