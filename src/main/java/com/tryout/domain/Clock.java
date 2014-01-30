package com.tryout.domain;

public class Clock {
	private long timeOfTheDay;

	public Clock() {
		timeOfTheDay = System.currentTimeMillis();
	}

	public long getTimeOfTheDay() {
		return timeOfTheDay;
	}

	public void setTimeOfTheDay(long timeOfTheDay) {
		this.timeOfTheDay = timeOfTheDay;
	}
}
