package com.oddle.app.weather.models.open_weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    private double longitude;
	private double latitude;
	
	@JsonProperty("longitude")
	public double getLongitude() {
		return longitude;
	}
	
	@JsonProperty("lon")
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@JsonProperty("latitude")
	public double getLatitude() {
		return latitude;
	}
	
	@JsonProperty("lat")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
