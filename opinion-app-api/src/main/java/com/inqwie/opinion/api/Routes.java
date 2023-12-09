package com.inqwie.opinion.api;

import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

import com.inqwise.opinion.api.controller.CollectorController;
import com.inqwise.opinion.api.controller.CounterController;
import com.inqwise.opinion.api.controller.FrontController;
import com.inqwise.opinion.api.controller.PayController;
import com.inqwise.opinion.api.controller.RqController;

public final class Routes {
	public static void define(RestExpress server){
		server.uri("/servlet/DataPostmaster/{lang}", RqController.getInstance())
		///servlet/DataPostmaster/en_US?rq=
		.method(HttpMethod.GET)
		.noSerialization();
		
		server.uri("/collector", CollectorController.getInstance())
		///collector?rq=
		.method(HttpMethod.GET, HttpMethod.POST)
		.noSerialization();
		
		server.uri("/counter", CounterController.getInstance())
		///collector?rq=
		.method(HttpMethod.GET)
		.noSerialization();

		server.uri("/site", FrontController.getInstance())
		///collector?rq=
		.method(HttpMethod.GET, HttpMethod.POST)
		.noSerialization();
		
		server.uri("/pay/{action}/{processorTypeId}", PayController.getInstance())
		.method(HttpMethod.GET)
		.noSerialization();
	}
}
