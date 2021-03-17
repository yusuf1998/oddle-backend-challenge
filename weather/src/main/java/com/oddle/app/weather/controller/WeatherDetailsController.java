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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import antlr.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.oddle.app.weather.controller.io_models.OpenWeatherOutput;
import com.oddle.app.weather.controller.io_models.WeatherInput;
import com.oddle.app.weather.controller.io_models.WeatherOutput;
import com.oddle.app.weather.exception.ResourceNotFoundException;
import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.models.WthWeatherBase;
import com.oddle.app.weather.models.WthWeatherDetails;
import com.oddle.app.weather.repositories.DaoWthCity;
import com.oddle.app.weather.repositories.DaoWthWeatherBase;
import com.oddle.app.weather.repositories.DaoWthWeatherDetails;

@RestController
@RequestMapping("/api")
public class WeatherDetailsController {

    @Autowired
    private DaoWthWeatherDetails daoWthWeatherDetails;
    @Autowired
    private DaoWthCity daoWthCity;
    @Autowired
    private DaoWthWeatherBase daoWthWeatherBase;

    private final String apiKey = "6415ef2c3620496afb77cef8539e0e8d";
    private final Logger logger = LoggerFactory.getLogger(WeatherDetailsController.class);

    @GetMapping("")
    public Map<String, Object> getMessage() {
        return Collections.singletonMap("message", "Welcome to Oddle Backend Challenge");
    } 

    @GetMapping("/weathers")
    public Map<String, Object> getWeather(@RequestParam(value = "city") String cityName) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now());

        RestTemplate restTemplate = new RestTemplate();
        StringBuilder sb = new StringBuilder();

        if(cityName.isEmpty()){
            response.put("status"   , HttpStatus.NOT_ACCEPTABLE);
            response.put("message"  , "Please input city"); 
        }else{
            sb.setLength(0);
            sb.append("https://api.openweathermap.org/data/2.5/weather?q=");
            sb.append(cityName);
            sb.append("&appid=");
            sb.append(apiKey);
            try {
                OpenWeatherOutput weatherRsp = restTemplate.getForObject(sb.toString(), OpenWeatherOutput.class);
                response.put("status"   , HttpStatus.OK);
                response.put("message"  , "Weather data found");
                response.put("content"  , weatherRsp);

            } catch(Exception e) {
                response.put("status", HttpStatus.NOT_FOUND);
                response.put("message"  , "Weather data not found");

            }
        }        
    	
    	return response;
    }

    @GetMapping("/weathers/today/{cityId}")
    public Map<String, Object> getTodayWeatherByCity(@PathVariable(value = "cityId") long cityId) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();

        List<WeatherOutput> output = new ArrayList<>();

        LocalDate today = LocalDate.now();     
        LocalDate tomorrow = today.plusDays(1);     
    
        List<WthWeatherDetails> weatherDetails = daoWthWeatherDetails.findByCityIdAndRangeDt(cityId, today, tomorrow);   
        WthCity cityBase =  daoWthCity.findById(cityId);
        
        response.put("timestamp", LocalDateTime.now());

        if(weatherDetails.size()>0){
            for (WthWeatherDetails row : weatherDetails) {
                WeatherOutput tr = new WeatherOutput();
                    tr.setWeatherDetailsId(row.getId()           );
                    tr.setWeatherDt       (row.getWeatherDt()    );
                    tr.setTemperature     (row.getTemperature()  );
                    tr.setTempMin         (row.getTempMin()      );
                    tr.setTempMax         (row.getTempMax()      );
                    tr.setPressure        (row.getPressure()     );
                    tr.setHumidity        (row.getHumidity()     );
                    tr.setCity            (cityBase              );
                    setWeatherDesc        (row, tr               );           
                
                output.add(tr);
             }
             response.put("status"   , HttpStatus.OK);
             response.put("message"  , "Weather data found");     
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Weather data not found");
        }
        
        response.put("content"  , output);        
        return response;
    }

    @PostMapping("/weathers")
    public Map<String, Object> saveWeather(@Valid @RequestBody WthWeatherDetails wthWeatherDetails) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status"   , HttpStatus.OK);

        WthWeatherDetails weatherDetails = wthWeatherDetails;
        
        daoWthWeatherDetails.saveAndFlush(weatherDetails);
        
        response.put("message"  , "Data Inserted");
        response.put("content"  , weatherDetails);

        return response;
    }    

    @GetMapping("/weathers/{cityId}")
    public Map<String, Object> getWeatherByCityAndPeriod(
        @PathVariable(value = "cityId") long cityId
       ,@Valid @RequestBody WeatherInput input )throws Exception {
        Map<String, Object> response = new LinkedHashMap<>();

        List<WeatherOutput> output = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDt = LocalDate.parse(input.getDtFrom(),formatter); 
        LocalDate endDt   = LocalDate.parse(input.getDtEnd(),formatter); 

        List<WthWeatherDetails> weatherDetails = daoWthWeatherDetails.findByCityIdAndRangeDt(cityId, startDt, endDt);   
        WthCity cityBase =  daoWthCity.findById(cityId);  


        response.put("timestamp", LocalDateTime.now());
        
        if(weatherDetails.size() > 0){
            for (WthWeatherDetails row : weatherDetails) {
                WeatherOutput tr = new WeatherOutput();
                    tr.setWeatherDetailsId(row.getId()           );
                    tr.setWeatherDt       (row.getWeatherDt()    );
                    tr.setTemperature     (row.getTemperature()  );
                    tr.setTempMin         (row.getTempMin()      );
                    tr.setTempMax         (row.getTempMax()      );
                    tr.setPressure        (row.getPressure()     );
                    tr.setHumidity        (row.getHumidity()     );
                    tr.setCity            (cityBase              );
                    setWeatherDesc        (row, tr               );           
                
                output.add(tr);             
            }
            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data weather found");
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data weather not found");
        }       
        response.put("content"  , output);

		return response;
    }

    private void setWeatherDesc(WthWeatherDetails row, WeatherOutput tr) {
        if(daoWthWeatherBase.findById(row.getWeatherBaseId()) != null){
            tr.setWeatherBase(daoWthWeatherBase.findById(row.getWeatherBaseId()));
        }else{
            tr.setWeatherBase(new WthWeatherBase());
        }
    }

    @PutMapping("/weathers/{id}")
    public Map<String, Object> updateWeather(@PathVariable(value = "id") long id,@Valid @RequestBody WthWeatherDetails weatherDetails) throws ResourceNotFoundException {
        
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        WthWeatherDetails nWtDetails = daoWthWeatherDetails.findById(id);
        if(nWtDetails != null){
            nWtDetails.setCityId(weatherDetails.getCityId());
            nWtDetails.setWeatherBaseId(weatherDetails.getWeatherBaseId());
            nWtDetails.setWeatherDt(weatherDetails.getWeatherDt());
            nWtDetails.setTemperature(weatherDetails.getTemperature());
            nWtDetails.setTempMin(weatherDetails.getTempMin());
            nWtDetails.setTempMax(weatherDetails.getTempMax());
            nWtDetails.setPressure(weatherDetails.getPressure());
            nWtDetails.setHumidity(weatherDetails.getHumidity());

            daoWthWeatherDetails.save(nWtDetails);

            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data weather updated");
            response.put("content"  , nWtDetails);
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data weather not found");
        }   
        return response;
    }

    @DeleteMapping("/weathers/{id}")
    public Map<String, Object> deleteWeather(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());

        WthWeatherDetails weatherDetails = daoWthWeatherDetails.findById(id);

        if(weatherDetails != null){
            daoWthWeatherDetails.delete(weatherDetails);
            response.put("status"   , HttpStatus.OK);
            response.put("message"  , "Data weather deleted");
        }else{
            response.put("status"   , HttpStatus.NOT_FOUND);
            response.put("message"  , "Data weather not found");
        }
         
        return response;
    }
}