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
// this controller running on api map url request //
@RequestMapping("/api") 
public class CityController {

    @Autowired
    private DaoWthCity daoWthCity;

    @Autowired
    private DaoWthWeatherDetails daoWthWeatherDetails;

    private final Logger logger = LoggerFactory.getLogger(CityController.class);
    
    // catch request for GET method //
    // http://localhost:8080/api/cities // 
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

    
    //   catch request for POST method    //
    //   http://localhost:8080/api/cities    //
    @PostMapping("/cities")
    public Map<String, Object> saveCity(@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
        // Create response output //
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status"   , HttpStatus.OK);

        WthCity cityBase = wthCity;

        daoWthCity.saveAndFlush(cityBase);

        response.put("message"  , "Data city inserted");
        response.put("content"  , cityBase            );
        
        // Call response output as return to consumer //
        return response;
    }

    //   catch request for GET method  pathVariable id    //
    //  ex http://localhost:8080/api/cities/1 => 1 is {id}    //
    @GetMapping("/cities/{id}")
    public Map<String, Object> getCityById(@PathVariable(value = "id") long id) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("timestamp", LocalDateTime.now());
        
            // find city data by id //
            WthCity wthCity = daoWthCity.findById(id);

            // check if data not nul //
            if(wthCity != null){
            // then set response value and set content get from city data //
                response.put("status"   , HttpStatus.OK      );       
                response.put("message"  , "Data city found"  );
                response.put("content"  , wthCity            );
            }else{
             // if null //   
            // then set response value message is not found //
                response.put("status"   , HttpStatus.NOT_FOUND);
                response.put("message"  , "Data city not found"  );
            }           

        return response;
    }


    //   catch request for PUT/UPDATE method     //
    //   request using path and requestBody     //
    //  ex http://localhost:8080/api/cities/1 => 1 is {id}    //
    @PutMapping("/cities/{id}")
    public Map<String, Object> updateCity(@PathVariable(value = "id") long id,@Valid @RequestBody WthCity wthCity) throws ResourceNotFoundException {
 
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        // find city data by id //
        WthCity cityBase = daoWthCity.findById(id);

        // check if data not nul //
        if(cityBase != null){
            
        // then set response value and set content get from city data //
            cityBase.setName(wthCity.getName());
            cityBase.setCountry(wthCity.getCountry());
            cityBase.setState(wthCity.getState());
            cityBase.setLongt(wthCity.getLongt());
            cityBase.setLat(wthCity.getLat());

         // update city data //
            daoWthCity.save(cityBase);

            response.put("status"   , HttpStatus.OK      );    
            response.put("message"  , "Data updated"     );
            response.put("content"  ,  cityBase          );
        }else{
            // if null //   
            // then set response value message is not found //
            response.put("status"   , HttpStatus.NOT_FOUND      );    
            response.put("message"  , "Data not found"   );
        }

        // send response to consumer //
        return response;
    }


     //   catch request for DELETE method     //
     //   request using pathVariable          //
    //  ex http://localhost:8080/api/cities/1 => 1 is {id}    //
    @DeleteMapping("/cities/{id}")
    public Map<String, Object> deleteCity(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        // Create response output //         
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
       
        // find city data by id //
        WthCity cityBase = daoWthCity.findById(id);

        // check if data not nul //
        if(cityBase != null){
        // get weatherDetails by city id  //
            List<WthWeatherDetails> weatherDetailList = daoWthWeatherDetails.findByCityId(id);
        // check if city data included in weather details table //
            if(weatherDetailList.size() > 0){
                // if city data count > 0 in weather details //
                // set response to ignore delete process //                
                response.put("status"   , HttpStatus.NOT_ACCEPTABLE);
                response.put("message"  , "Cannot delete city that written in weather details!");
            }else{
                // if city data count < 0 in weather details //
                // delete city data by id //
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