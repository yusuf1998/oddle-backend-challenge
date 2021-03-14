package com.oddle.app.weather.controller.io_models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // lombok
public class WeatherInput implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String dtFrom   ;
    private String dtEnd    ;
}
