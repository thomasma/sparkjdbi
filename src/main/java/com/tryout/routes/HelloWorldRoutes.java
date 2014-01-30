package com.tryout.routes;

import static spark.Spark.get;
import spark.Request;
import spark.Response;
import spark.Route;

import com.tryout.RouteRegistrar;
import com.tryout.domain.Clock;

public class HelloWorldRoutes implements RouteRegistrar {

	public void routes() {

		get(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello World!";
			}
		});

		get(new JsonTransformerRoute("/currenttime") {
			@Override
			public Object handle(Request request, Response response) {
				return new Clock();
			}
		});
	}

}
