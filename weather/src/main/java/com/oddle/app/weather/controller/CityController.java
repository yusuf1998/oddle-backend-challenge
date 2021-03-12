package com.oddle.app.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.repositories.DaoWthCity;

@RestController
@RequestMapping("/api/ct")
public class CityController {

    @Autowired
    private DaoWthCity daoWthCity;

    private final Logger logger = LoggerFactory.getLogger(CityController.class);
    
    @GetMapping("/cities")
    public List<WthCity> getCities() {
        return daoWthCity.findAll();
    }

}