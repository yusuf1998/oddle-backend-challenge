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
import com.oddle.app.weather.models.WthWeatherBase;
import com.oddle.app.weather.models.WthWeatherDetails;
import com.oddle.app.weather.repositories.DaoWthWeatherBase;
import com.oddle.app.weather.repositories.DaoWthWeatherDetails;

@RestController
@RequestMapping("/api")
public class WeatherBaseController {

    @Autowired
    private DaoWthWeatherBase daoWthWeatherBase;

    @Autowired
    private DaoWthWeatherDetails daoWthWeatherDetails;

    private final Logger logger = LoggerFactory.getLogger(WeatherBaseController.class);
    
    @GetMapping("/wtbases")
    public Map<String, Object> getAllWthBases() {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        List<WthWeatherBase> wthBaseList = daoWthWeatherBase.findAll();

        if(wthBaseList.size() > 0){
            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data weather base found");
            response.put("content"  , wthBaseList         );
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data weather base not found");
        }

        return response;
    }

    @PostMapping("/wtbases")
    public Map<String, Object> saveCity(@Valid @RequestBody WthWeatherBase wthWeatherBase) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status"   , HttpStatus.OK);

        WthWeatherBase wthBase = wthWeatherBase;

        daoWthWeatherBase.saveAndFlush(wthBase);

        response.put("message"  , "Data weather base inserted");
        response.put("content"  , wthBase            );
        
        return response;
    }

    @GetMapping("/wtbases/{id}")
    public Map<String, Object> getCityById(@PathVariable(value = "id") long id) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("timestamp", LocalDateTime.now());
        
            WthWeatherBase wthWeatherBase = daoWthWeatherBase.findById(id);

            if(wthWeatherBase != null){
                response.put("status"   , HttpStatus.OK      );       
                response.put("message"  , "Data weather base found"  );
                response.put("content"  , wthWeatherBase             );
            }else{
                response.put("status"   , HttpStatus.NOT_FOUND);
                response.put("message"  , "Data weather base not found" );
            }           
        return response;
    }

    @PutMapping("/wtbases/{id}")
    public Map<String, Object> updateCity(@PathVariable(value = "id") long id,@Valid @RequestBody WthWeatherBase wthWeatherBase) throws ResourceNotFoundException {
 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        WthWeatherBase wthBase = daoWthWeatherBase.findById(id);

        if(wthBase != null){
            wthBase.setWeatherName(wthWeatherBase.getWeatherName());
            wthBase.setWeatherDescription(wthWeatherBase.getWeatherDescription());
    
            daoWthWeatherBase.save(wthBase);

            response.put("status"   , HttpStatus.OK      );    
            response.put("message"  , "Data updated"     );
            response.put("content"  ,  wthBase           );
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND    );    
            response.put("message"  , "Data not found"        );
        }

        return response;
    }

    @DeleteMapping("/wtbases/{id}")
    public Map<String, Object> deleteCity(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
               
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
       
        WthWeatherBase wthBase = daoWthWeatherBase.findById(id);
        if(wthBase != null){
            List<WthWeatherDetails> weatherDetailList = daoWthWeatherDetails.findByWeatherBaseId(id);
            if(weatherDetailList.size() > 0){  
                for (WthWeatherDetails row : weatherDetailList) {
                    WthWeatherDetails wthDetails = daoWthWeatherDetails.findById(row.getId());
                        wthDetails.setWeatherBaseId(0);
                    daoWthWeatherDetails.save(wthDetails);
                }
            }              
            daoWthWeatherBase.delete(wthBase);
            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data deleted");
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data not found");
        }                
        return response;
    }

}