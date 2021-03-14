package com.oddle.app.weather.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.oddle.app.weather.exception.ResourceNotFoundException;
import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.models.WthWeatherDetails;
import com.oddle.app.weather.repositories.DaoWthCity;
import com.oddle.app.weather.repositories.DaoWthWeatherDetails;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private DaoWthCity daoWthCity;

    @Autowired
    private DaoWthWeatherDetails daoWthWeatherDetails;

    private final Logger logger = LoggerFactory.getLogger(CityController.class);
    
    @GetMapping("/cities")
    public Map<String, Object> getCities() {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        List<WthCity> cityList = daoWthCity.findAll();

        if(cityList.size() > 0){
            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data city found");
            response.put("content"  , cityList         );
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data city not found");
        }
        return response;
    }

    @PostMapping("/cities")
    public Map<String, Object> saveCity(@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status"   , HttpStatus.OK);

        WthCity cityBase = wthCity;

        daoWthCity.saveAndFlush(cityBase);

        response.put("message"  , "Data city inserted");
        response.put("content"  , cityBase            );
        
        return response;
    }

    @GetMapping("/cities/{id}")
    public Map<String, Object> getCityById(@PathVariable(value = "id") long id) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("timestamp", LocalDateTime.now());
        
            WthCity wthCity = daoWthCity.findById(id);

            if(wthCity != null){
                response.put("status"   , HttpStatus.OK      );       
                response.put("message"  , "Data city found"  );
                response.put("content"  , wthCity            );
            }else{
                response.put("status"   , HttpStatus.NOT_FOUND);
                response.put("message"  , "Data city not found"  );
            }           

        return response;
    }

    @PutMapping("/cities/{id}")
    public Map<String, Object> updateCity(@PathVariable(value = "id") long id,@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        WthCity cityBase = daoWthCity.findById(id);

        if(cityBase != null){
            cityBase.setName(wthCity.getName());
            cityBase.setCountry(wthCity.getCountry());
            cityBase.setState(wthCity.getState());
            cityBase.setLongt(wthCity.getLongt());
            cityBase.setLat(wthCity.getLat());
    
            daoWthCity.save(cityBase);

            response.put("status"   , HttpStatus.OK      );    
            response.put("message"  , "Data updated"     );
            response.put("content"  ,  cityBase          );
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND      );    
            response.put("message"  , "Data not found"   );
        }

        return response;
    }

    @DeleteMapping("/cities/{id}")
    public Map<String, Object> deleteCity(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
               
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
       
        WthCity cityBase = daoWthCity.findById(id);
        if(cityBase != null){
            List<WthWeatherDetails> weatherDetailList = daoWthWeatherDetails.findByCityId(id);
            if(weatherDetailList.size() > 0){                
                response.put("status"   , HttpStatus.NOT_ACCEPTABLE);
                response.put("message"  , "Cannot delete city that written in weather details!");
            }else{
                daoWthCity.delete(cityBase);
                response.put("status"   , HttpStatus.OK);
                response.put("message"  , "Data deleted");
            }
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data not found");
        }                
        return response;
    }

}