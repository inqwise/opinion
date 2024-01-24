package com.inqwise.opinion.api;

import org.json.JSONException;
import org.restexpress.RestExpress;
import org.restexpress.exception.BadRequestException;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import org.restexpress.serialization.DefaultSerializationProvider;

import com.inqwise.opinion.api.systemFramework.ApplicationConfiguration;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;

import jakarta.xml.bind.ValidationException;

public final class RestExpressService {
	
	static ApplicationLog logger = ApplicationLog.getLogger(RestExpressService.class);
	
	private static boolean started = false;
	
	public static void start(){
		if(!started){
			synchronized (RestExpressService.class) {
				if(!started){
					RestExpress server = initializeServer();
					server.awaitShutdown();
				}
			}
		}
	}
	
	public static void stop(){
		if(started){
			synchronized (RestExpressService.class) {
				if(started){
					
				}
			}
		}
	}
	
	
	
	public static RestExpress initializeServer()
	{
		RestExpress.setDefaultSerializationProvider(new DefaultSerializationProvider());
		RestExpress server = new RestExpress()
				
				.setName(ApplicationConfiguration.Service.getName())
				//.setBaseUrl(ApplicationConfiguration.Service.getBaseUrl())
				.setPort(ApplicationConfiguration.Service.getPort())
				.setMaxContentSize(65536)
				//.setDefaultFormat(Format.TXT)
				//.putResponseProcessor(Format.TXT, TxtSerializationProcessor.getInstance().)
				//.putResponseProcessor(Format.XML, ResponseProcessors.xml())
				//.putResponseProcessor(Format.WRAPPED_JSON, ResponseProcessors.wrappedJson())
				//.putResponseProcessor(Format.WRAPPED_XML, ResponseProcessors.wrappedXml())
				.addMessageObserver(new SimpleConsoleLogMessageObserver());

		if(ApplicationConfiguration.Service.getExecutorThreadPoolSize() > 0) {
			server.setExecutorThreadCount(ApplicationConfiguration.Service.getExecutorThreadPoolSize());
		}
		
		Routes.define(server);
		configureMetrics(server);


		//new RoutesMetadataPlugin()							// Support basic discoverability.
		//		.register(server)
		//		.parameter(Parameters.Cache.MAX_AGE, 86400);	// Cache for 1 day (24 hours).


		//new CacheControlPlugin()							// Support caching headers.
		//		.register(server);


		mapExceptions(server);
		server.bind();
		server.awaitShutdown();
		
		return server;
    }

	private static void configureMetrics(RestExpress server)
    {
	    if (ApplicationConfiguration.Metrics.isEnabled())
		{
	    	
	    	//MetricRegistry registry = new MetricRegistry();
	    	//new MetricsPlugin(registry).register(server);
	    	/*
			if (ApplicationConfiguration.Metrics.isGraphiteEnabled())
			{
				final Graphite graphite = new Graphite(new InetSocketAddress(ApplicationConfiguration.Metrics.getGraphiteHost(), ApplicationConfiguration.Metrics.getGraphitePort()));
				final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
					.prefixedWith(ApplicationConfiguration.Metrics.getPrefix())
					.convertRatesTo(TimeUnit.SECONDS)
					.convertDurationsTo(TimeUnit.MILLISECONDS)
					.filter(MetricFilter.ALL)
					.build(graphite);
				reporter.start(ApplicationConfiguration.Metrics.getPublishSeconds(), TimeUnit.SECONDS);
			}
			else
			{
				logger.warn("*** Graphite Metrics Publishing is Disabled ***");
			}*/
		}
		else
		{
			logger.warn("*** Metrics Generation is Disabled ***");
		}
    }

    private static void mapExceptions(RestExpress server)
    {
    	server.mapException(ValidationException.class, BadRequestException.class);
    	server.mapException(JSONException.class, BadRequestException.class);
    }
}
