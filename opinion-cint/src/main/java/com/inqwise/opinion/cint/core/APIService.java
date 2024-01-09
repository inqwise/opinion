package com.inqwise.opinion.cint.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import com.inqwise.opinion.cint.exception.ClientActionRequiredException;
import com.inqwise.opinion.cint.exception.HttpErrorException;
import com.inqwise.opinion.cint.exception.InvalidCredentialException;
import com.inqwise.opinion.cint.exception.InvalidResponseDataException;
import com.inqwise.opinion.cint.exception.MissingCredentialException;
import com.inqwise.opinion.cint.systemFramework.CintConfig;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

/**
 * Wrapper class for api calls
 * 
 */
public class APIService {

	private static final ApplicationLog Logger = ApplicationLog.getLogger(APIService.class);
			
	private String endPoint;
	private HttpConfiguration httpConfiguration = new HttpConfiguration();
	private Map<String, String> headers = new HashMap<String, String>();

	/**
	 * Set all the http related parameters from the config file
	 * 
	 * @param serviceName
	 * @throws SSLConfigurationException
	 * @throws NumberFormatException
	 */
	public APIService() throws NumberFormatException {
		endPoint = String.format(CintConfig.getEndPoint(), CintConfig.getApiToken());
		httpConfiguration.setTrustAll(CintConfig.isTrustAllConnection());

		try {
			httpConfiguration.setConnectionTimeout(CintConfig.getConnectionTimeout());
			httpConfiguration.setMaxRetry(CintConfig.getMaxRetry());
			httpConfiguration.setReadTimeout(CintConfig.getReadTimeout());
			httpConfiguration.setMaxHttpConnection(CintConfig.getMaxHttpConnection());
		} catch (NumberFormatException nfe) {
			Logger.debug(nfe.getMessage());
			throw nfe;
		}
	}

	/**
	 * makes a request to specified end point.
	 * @param tokenSecret
	 * @param accessToken
	 * @return response String
	 * @throws HttpErrorException
	 * @throws InterruptedException
	 * @throws InvalidResponseDataException
	 * @throws ClientActionRequiredException
	 * @throws MissingCredentialException
	 * @throws SSLConfigurationException
	 * @throws InvalidCredentialException
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 * @throws UnknownHostException 
	 * @throws MalformedURLException 
	 * @throws OAuthException
	 */
	public ResponseValue makeRequest(String urlPart, RequestType requestType,
			String messageBody, boolean isRequiredSignature) 
					throws GeneralSecurityException, MalformedURLException,
					UnknownHostException, IOException, InvalidResponseDataException,
					HttpErrorException, ClientActionRequiredException, InterruptedException {

		ConnectionManager connectionMgr = ConnectionManager.getInstance();
		HttpConnection connection = connectionMgr.getConnection();
		String url = Constants.EMPTY_STRING;

		if(urlPart.startsWith("http")){
			url = urlPart;
		} else {
			url = endPoint + urlPart;
		}
		
		Logger.debug("makeRequest : url='%s'", url);
		
		httpConfiguration.setEndPointUrl(endPoint);
		AuthenticationService auth = new AuthenticationService();
		if(isRequiredSignature){
			headers = auth.getCintHeaders(CintConfig.getApiToken(), CintConfig.getSecret(), url, messageBody);
		}
		connection.createAndConfigureHttpConnection(httpConfiguration, requestType, url);
		Logger.debug("makeRequest : Conection creation finished");
		
		ResponseValue response = connection.execute(messageBody, headers, requestType); 
		if(Logger.IsInfoEnabled()){
			Logger.info("type: '%s', value: '%s'", response.getType(), response.getValue());
		}
		return response;

	}

	public String getEndPoint() {
		return endPoint;
	}

}
