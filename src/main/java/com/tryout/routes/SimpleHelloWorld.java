package com.tryout.routes;

import static spark.Spark.get;
import spark.Request;
import spark.Response;
import spark.Route;

public class SimpleHelloWorld {
	public static void main(String[] args) {
		get(new Route("/hello/:name") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello " + request.params("name") + "!";
			}
		});
	}
}