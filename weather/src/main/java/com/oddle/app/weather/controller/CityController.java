package com.oddle.app.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.oddle.app.weather.exception.ResourceNotFoundException;
import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.repositories.DaoWthCity;
import com.oddle.app.weather.repositories.DaoWthCoordinate;

@RestController
@RequestMapping("/api/ct")
public class CityController {

    @Autowired
    private DaoWthCity daoWthCity;

    @Autowired
    private DaoWthCoordinate daoWthCoordinate;

    private final Logger logger = LoggerFactory.getLogger(CityController.class);
    
    @GetMapping("/cities")
    public List<WthCity> getCities() {
        return daoWthCity.findAll();
    }

    @PostMapping("/cities")
    public Map<String, Boolean> saveCity(@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
      
        daoWthCity.saveAndFlush(wthCity);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("Inserted", Boolean.TRUE);

        return response;
    }

    @GetMapping("/cities/{id}")
    public WthCity getCityById(@PathVariable(value = "id") long id) {
            WthCity wthCity = daoWthCity.findById(id);
        return wthCity;
    }

    @PutMapping("/cities/{id}")
    public Map<String, Boolean> updateCity(@PathVariable(value = "id") long id,@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
        logger.info("Okee {}", wthCity);
 
        WthCity cityBase = daoWthCity.findById(id);
        cityBase.setName(wthCity.getName());
        cityBase.setCountry(wthCity.getCountry());
        cityBase.setState(wthCity.getState());
        cityBase.setLongt(wthCity.getLongt());
        cityBase.setLat(wthCity.getLat());

        daoWthCity.save(cityBase);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);

        return response;
    }

    @DeleteMapping("/cities/{id}")
    public Map<String, Boolean> deleteCity(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
       
        WthCity cityBase = daoWthCity.findById(id);

        daoWthCity.delete(cityBase);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
                
        return response;
    }

}