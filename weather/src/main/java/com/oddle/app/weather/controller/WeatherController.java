package com.oddle.app.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class WeatherController {

    @GetMapping("")
    public Map<String, Object> getWeathers() {
        return Collections.EMPTY_MAP;
    }
}