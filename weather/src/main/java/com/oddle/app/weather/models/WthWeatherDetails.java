package com.oddle.app.weather.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
    private Date weatherDt;

    @Column(name = "city_id", nullable = true)  
    private Double cityId;

    @Column(name = "weather_base_id", nullable = true)  
    private Double WeatherBaseId;

    @Column(name = "temperature", nullable = true)  
    private Double temperature;

    @Column(name = "temp_min", nullable = true)  
    private Double tempMin;
    
    @Column(name = "temp_max", nullable = true)  
    private Double tempMax;

    @Column(name = "pressure", nullable = true)  
    private Double pressure;

    @Column(name = "humidity", nullable = true)  
    private Double humidity;
}
