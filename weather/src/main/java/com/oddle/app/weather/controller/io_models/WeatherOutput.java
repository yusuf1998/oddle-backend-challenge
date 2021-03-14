package com.oddle.app.weather.controller.io_models;

import java.io.Serializable;
import java.time.LocalDate;
import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.models.WthWeatherBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // lombok
public class WeatherOutput implements Serializable
{
    private static final long serialVersionUID = 0L;

    private long   weatherDetailsId ;
 //   private long   cityId           ;
    private LocalDate   weatherDt        ;
    private WthCity city            ;
    private WthWeatherBase weatherBase;
//    private long   weatherBaseId    ;
    private double temperature      ;
    private double tempMin          ;
    private double tempMax          ;
    private double pressure         ;
    private double humidity         ;
}
