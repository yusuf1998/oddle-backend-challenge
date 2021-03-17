package com.oddle.app.weather.models.open_weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clouds {
	
	private short cloudiness;

	@JsonProperty("cloudiness")
	public short getCloudiness() {
		return cloudiness;
	}

	@JsonProperty("all")
	public void setCloudiness(short cloudiness) {
		this.cloudiness = cloudiness;
	}
	
}
