package com.oddle.app.weather.controller.io_models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oddle.app.weather.models.open_weather.*;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OpenWeatherOutput {
	
	private int cityId;
	private String cityName;
	private int cod;
	private int timeZone;
	private Coordinate coordinate;
	private String base;
	private SystemData systemData;
	private long calculationDateTime;
	private int visibility;
	private MainData mainData;
	private Wind wind;
	private Rain rain;
	private Snow snow;
	private Clouds clouds;
	private List<Weather> weather;
	
	@JsonProperty("cityId")
	public int getCityId() {
		return cityId;
	}
	
	@JsonProperty("id")
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	@JsonProperty("cityName")
	public String getCityName() {
		return cityName;
	}
	
	@JsonProperty("name")
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public int getCod() {
		return cod;
	}
	
	public void setCod(int cod) {
		this.cod = cod;
	}
	
	@JsonProperty("timeZone")
	public int getTimeZone() {
		return timeZone;
	}
	
	@JsonProperty("timezone")
	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}
	
	@JsonProperty("coordinate")
	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	@JsonProperty("coord")
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	@JsonProperty("systemData")
	public SystemData getSystemData() {
		return systemData;
	}
	
	@JsonProperty("sys")
	public void setSystemData(SystemData systemData) {
		this.systemData = systemData;
	}
	
	@JsonProperty("calculationDateTime")
	public long getCalculationDateTime() {
		return calculationDateTime;
	}
	
	@JsonProperty("dt")
	public void setCalculationDateTime(long calculationDateTime) {
		this.calculationDateTime = calculationDateTime;
	}
	
	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	@JsonProperty("mainData")
	public MainData getMainData() {
		return mainData;
	}
	
	@JsonProperty("main")
	public void setMainData(MainData mainData) {
		this.mainData = mainData;
	}
	
	public Wind getWind() {
		return wind;
	}
	
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	
	public Rain getRain() {
		return rain;
	}
	
	public void setRain(Rain rain) {
		this.rain = rain;
	}
	
	public Snow getSnow() {
		return snow;
	}
	
	public void setSnow(Snow snow) {
		this.snow = snow;
	}
	
	public Clouds getClouds() {
		return clouds;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public List<Weather> getWeather() {
		return weather;
	}
	
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}
}
