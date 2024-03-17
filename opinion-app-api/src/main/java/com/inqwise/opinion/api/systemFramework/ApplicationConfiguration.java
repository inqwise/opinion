package com.inqwise.opinion.api.systemFramework;

import com.inqwise.opinion.infrastructure.systemFramework.BaseApplicationConfiguration;
import org.restexpress.RestExpress;

public class ApplicationConfiguration extends BaseApplicationConfiguration {
	
	public final static class Service {

		private static final String BASE_URL_KEY = "service.baseUrl";
		public static String getBaseUrl() {
			return getValue(BASE_URL_KEY, "");
		}
		
		private static final String EXECUTOR_THREAD_POOL_SIZE_KEY = "service.thredPoolSize";
		public static int getExecutorThreadPoolSize() {
			return Integer.parseInt(getValue(EXECUTOR_THREAD_POOL_SIZE_KEY, "0"));
		}
		
		private static final String PORT_KEY = "service.port";
		public static int getPort() {
			return Integer.parseInt(getValue(PORT_KEY, String.valueOf(8083)));
		}
		
		private static final String NAME_KEY = "service.name";
		public static String getName() {
			return getValue(NAME_KEY, "inqwise-api");
		}
	}
	
	public final static class Metrics {
		private static final String IS_ENABLED_KEY = "metrics.enabled";
		public static boolean isEnabled() {
			return getValue(IS_ENABLED_KEY, "false").equalsIgnoreCase("true");
		}
		
		private static final String IS_GRAPHITE_ENABLED_KEY = "metrics.graphite.enabled";
		public static boolean isGraphiteEnabled() {
			return getValue(IS_GRAPHITE_ENABLED_KEY, "false").equalsIgnoreCase("true");
		}
		
		private static final String GRAPHITE_HOST_KEY = "metrics.graphite.host";
		public static String getGraphiteHost() {
			return getValue(GRAPHITE_HOST_KEY, true);
		}
		
		private static final String PREFIX_KEY = "metrics.prefix";
		public static String getPrefix() {
			return getValue(PREFIX_KEY, true);
		}
		
		private static final String PUBLISH_IN_SECONDS_KEY = "metrics.publishInSeconds";
		public static long getPublishSeconds() {
			return Integer.parseInt(getValue(PUBLISH_IN_SECONDS_KEY, "5"));
		}
		
		private static final String GRAPHITE_PORT_KEY = "metrics.graphite.port";
		public static int getGraphitePort() {
			return Integer.parseInt(getValue(GRAPHITE_PORT_KEY, true));
		}
		
	}
}
