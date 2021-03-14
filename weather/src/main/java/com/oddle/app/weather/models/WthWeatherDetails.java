package com.oddle.app.weather.models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "wth_weather_details")
public class WthWeatherDetails implements Serializable {

    private static final long serialVersionUID = -8546777162330650796L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  
    private long id;

    @Column(name = "weather_dt", nullable = true)  
    private LocalDate weatherDt;

    @Column(name = "city_id", nullable = true)  
    private long cityId;

    @Column(name = "weather_base_id")  
    private long weatherBaseId;

    @Column(name = "temperature", nullable = true)  
    private double temperature;

    @Column(name = "temp_min", nullable = true)  
    private double tempMin;
    
    @Column(name = "temp_max", nullable = true)  
    private double tempMax;

    @Column(name = "pressure", nullable = true)  
    private double pressure;

    @Column(name = "humidity", nullable = true)  
    private double humidity;

    // @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "city_id", insertable = false, updatable = false)
	// @Fetch(FetchMode.JOIN)
	// private WthCity city;
}
