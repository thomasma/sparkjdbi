package com.tryout;

import static spark.Spark.after;

import com.tryout.db.DB;
import com.tryout.routes.HelloWorldRoutes;
import com.tryout.routes.NotesRoutes;

public class Application {
	public static void main(String[] args) {
		// initialize the database connections
		DB.initialize();

		// register routes
		new HelloWorldRoutes().routes();
		new NotesRoutes().routes();

		// after filter (for all routes)...
		// you can be route specific using new Filter("/hello/*")
		after((request, response) -> {
			response.header("yourheader", "header value here");
		});
	}
}
