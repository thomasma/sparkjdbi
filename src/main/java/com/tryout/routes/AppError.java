package com.tryout.routes;

public class AppError {
	private String message;

	public AppError(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
