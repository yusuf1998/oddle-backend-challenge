package com.oddle.app.weather.models.open_weather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    private int id;
	private String group;
	private String description;
	private String icon;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonProperty("group")
	public String getGroup() {
		return group;
	}
	
	@JsonProperty("main")
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
