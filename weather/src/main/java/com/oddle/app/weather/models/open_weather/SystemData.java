package com.oddle.app.weather.models.open_weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemData {
	
	private int id;
	private int type;
	private String message;
	private String countryCode;
	private long sunriseTime;
	private long sunsetTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@JsonProperty("countryCode")
	public String getCountryCode() {
		return countryCode;
	}
	
	@JsonProperty("country")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@JsonProperty("sunriseTime")
	public long getSunriseTime() {
		return sunriseTime;
	}
	
	@JsonProperty("sunrise")
	public void setSunriseTime(long sunriseTime) {
		this.sunriseTime = sunriseTime;
	}
	
	@JsonProperty("sunsetTime")
	public long getSunsetTime() {
		return sunsetTime;
	}
	
	@JsonProperty("sunset")
	public void setSunsetTime(long sunsetTime) {
		this.sunsetTime = sunsetTime;
	}
	
}
