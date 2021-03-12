package com.oddle.app.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

import com.oddle.app.weather.repositories.DaoWthCity;

@RestController
@RequestMapping("/api/wt")
public class WeatherController {

    @Autowired
    private DaoWthCity daoWthCity;

    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping("")
    public Map<String, Object> getMessage() {
        return Collections.singletonMap("message", "Welcome to Oddle Backend Challenge");
    } 

}