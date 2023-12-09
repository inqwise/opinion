package com.cint.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import com.cint.exception.ClientActionRequiredException;
import com.cint.exception.HttpErrorException;
import com.cint.exception.InvalidResponseDataException;
import com.cint.exception.SSLConfigurationException;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

/**
 * Wrapper class used for HttpsURLConnection
 * 
 * 
 */
public class HttpConnection {
	private SSLContext sslContext;
	private HttpConfiguration config;
	private HttpsURLConnection connection;
	private boolean DefaultSSL = true;
	
	private static final ApplicationLog Logger = ApplicationLog.getLogger(HttpConnection.class);

	/**
	 * Executes HTTP request
	 * 
	 * @param url
	 * @param messageBody
	 * @param headers
	 * @return String response
	 * @throws InvalidResponseDataException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws HttpErrorException
	 * @throws ClientActionRequiredException
	 */
	public final ResponseValue execute(String messageBody,
			Map<String, String> headers, RequestType requestType) throws InvalidResponseDataException,
			IOException, InterruptedException, HttpErrorException,
			ClientActionRequiredException {
		String successResponse = Constants.EMPTY_STRING;
		ValueType responseType = ValueType.Undefined;
		String errorResponse = Constants.EMPTY_STRING;
		BufferedReader reader = null;
		OutputStreamWriter writer = null;
		if(null != messageBody){
			connection.setRequestProperty("Content-Length", String.valueOf(messageBody.length()));
			
		} else if (requestType == RequestType.Put) {
			
			connection.setRequestProperty("Content-Length", "0");
		}
		
		connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
		connection.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
		
		if(headers != null){
			setHttpHeaders(this.connection, headers);
		}
		
		
		
		try {
			for (int retry = 0; retry < this.config.getMaxRetry(); retry++) {

				if (retry > 0) {
					Logger.debug(" Retry  No : "
							+ retry + "...");
					try {
						Thread.sleep(this.config.getRetryDelay());
					} catch (InterruptedException ie) {
						throw ie;
					}
				}
				
				try {
					if(requestType == RequestType.Post || requestType == RequestType.Put){
						writer = new OutputStreamWriter(
								this.connection.getOutputStream(), Constants.ENCODING_FORMAT);
						
						if(null != messageBody){
							writer.write(messageBody);
						}
						writer.flush();
						writer.close();
					} 
					
					int responsecode = connection.getResponseCode();
					
					reader = new BufferedReader(new InputStreamReader(
								connection.getInputStream(),
								Constants.ENCODING_FORMAT));
					
					switch(responsecode){
					case 200:
						successResponse = read(reader);
						responseType = ValueType.Xml;
						reader.close();
						Logger.debug("Response : " + successResponse);
						if (successResponse.length() <= 0) {
							throw new InvalidResponseDataException(
									successResponse);
						}
						break;
					case 201:
						successResponse = connection.getHeaderField("Location");
						responseType = ValueType.Location;
						reader.close();
						Logger.debug("Response : " + successResponse);
						if (successResponse.length() <= 0) {
							throw new InvalidResponseDataException(
									successResponse);
						}
						break;
					case 204:
						reader.close();
						responseType = ValueType.NoValue;
						break;
					default:
						successResponse = read(reader);
						reader.close();
						throw new ClientActionRequiredException(
								"Response Code : " + responsecode
										+ " with response : " + successResponse);
					}
					
					break; // Exit from for
					
				} catch (IOException e) {
					try {
						int responsecode = connection.getResponseCode();
						if (connection.getErrorStream() != null) {
							reader = new BufferedReader(new InputStreamReader(
									connection.getErrorStream(),
									Constants.ENCODING_FORMAT));
							errorResponse = read(reader);
							reader.close();
							Logger.warn("Error code : " + responsecode
											+ " with response : "
											+ errorResponse);
						}
						if (responsecode < 500) {
							throw new HttpErrorException("Error code : "
									+ responsecode + " url : " + connection.getURL() + 
									" with response : "	+ errorResponse, responsecode);
						}
					} catch (IOException io) {
						throw io;
					}
				}
			}

			if (responseType != ValueType.NoValue && successResponse.length() <= 0) {
				throw new HttpErrorException(
						"retry fails..  check log for more information");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
		return new ResponseValue(successResponse, responseType) ;
	}

	private String read(BufferedReader reader) throws IOException {
		String inputLine = Constants.EMPTY_STRING;
		StringBuilder response = new StringBuilder();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		return response.toString();
	}

	/**
	 * Set headers for HttpsURLConnection object
	 * 
	 * @param conn
	 * @param headers
	 */
	private void setHttpHeaders(HttpURLConnection conn,
			Map<String, String> headers) {
		Iterator<Map.Entry<String, String>> itr = headers.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> pairs = itr.next();
			String key = pairs.getKey();
			String value = pairs.getValue();
			conn.addRequestProperty(key, value);
		}
	}

	/**
	 * Set ssl parameters for client authentication
	 * 
	 * @param certPath
	 * @param certKey
	 * @param trustAll
	 * @throws SSLConfigurationException
	 */
	public void setupClientSSL(String certPath, String certKey, boolean trustAll)
			throws SSLConfigurationException {
		try {
			if (isDefaultSSL()) {
				this.sslContext = SSLUtil.getDefaultSSLContext(trustAll);
			} else {
				this.sslContext = SSLUtil.setupClientSSL(certPath, certKey,
						trustAll);
			}
		} catch (Exception e) {
			throw new SSLConfigurationException(e.getMessage(), e);
		}
	}

	/**
	 * Create and configure HttpsURLConnection object
	 * 
	 * @param clientConfiguration
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void createAndConfigureHttpConnection(
			HttpConfiguration clientConfiguration, RequestType requestType, String strUrl)
			throws MalformedURLException, UnknownHostException, IOException {
		this.config = clientConfiguration;
		try {
			URL url = new URL(strUrl);

			this.connection = (HttpsURLConnection) url
						.openConnection();

			//if(null != this.sslContext){
				//this.connection.setSSLSocketFactory(this.sslContext
				//	.getSocketFactory());
			//}
			//if (isDefaultSSL()) {
			//	this.connection.setHostnameVerifier(hv);
			//}

			System.setProperty("http.maxConnections",
					String.valueOf(this.config.getMaxHttpConnection()));
			System.setProperty("sun.net.http.errorstream.enableBuffering",
					"true");

			//if(requestType == RequestType.Post){
				this.connection.setDoInput(true);
			//}
			this.connection.setDoOutput(true);
			
			//this.connection.setRequestMethod("POST");
			this.connection.setRequestMethod(requestType.getValue());
			this.connection.setConnectTimeout(this.config
					.getConnectionTimeout());
			this.connection.setReadTimeout(this.config.getReadTimeout());

		} catch (MalformedURLException me) {
			throw me;
		} catch (UnknownHostException uhe) {
			throw uhe;
		} catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * Class used to relax host name verification.
	 */
	private HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostname, SSLSession session) {
			return true;
		}
	};

	public boolean isDefaultSSL() {
		return DefaultSSL;
	}

	public void setDefaultSSL(boolean defaultSSL) {
		DefaultSSL = defaultSSL;
	}

}