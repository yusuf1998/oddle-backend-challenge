package com.oddle.app.weather.models.open_weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {
    private float speed;
	private float degree;
	private float gust;
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	@JsonProperty("degree")
	public float getDegree() {
		return degree;
	}
	
	@JsonProperty("deg")
	public void setDegree(float degree) {
		this.degree = degree;
	}
	
	public float getGust() {
		return gust;
	}
	
	public void setGust(float gust) {
		this.gust = gust;
	}
}
