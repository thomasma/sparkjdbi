package com.tryout.routes;

import static spark.Spark.get;

import com.tryout.RouteRegistrar;
import com.tryout.domain.Clock;

public class HelloWorldRoutes implements RouteRegistrar {

	public void routes() {

		get("/hello", (request, response) -> {
			return "Hello World!";
		});

		get("/hello/:name", (request, response) -> {
			return "Hello " + request.params("name") + "!";
		});

		get("/currenttime", (request, response) -> {
			return new Clock();
		}, new JsonTransformer());
	}

}
