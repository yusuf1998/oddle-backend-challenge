package com.oddle.app.weather.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.websocket.Encoder.Text;

import lombok.Data;

@Data
@Entity
@Table(name = "wth_weather_Base")
public class WthWeatherBase implements Serializable {

    private static final long serialVersionUID = -8546777162330650796L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  
    private long id;

    @Column(name = "weather_name", nullable = true)  
    private String weatherName;

    @Column(name = "weather_description", nullable = true)  
    private String weatherDescription;
}
