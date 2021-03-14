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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
public class WeatherController {

    @Autowired
    private DaoWthWeatherDetails daoWthWeatherDetails;
    @Autowired
    private DaoWthCity daoWthCity;
    @Autowired
    private DaoWthWeatherBase daoWthWeatherBase;

    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping("")
    public Map<String, Object> getMessage() {
        return Collections.singletonMap("message", "Welcome to Oddle Backend Challenge");
    } 

    @GetMapping("/weathers/today/{cityId}")
    public List<WthWeatherDetails> getTodayWeatherByCity(@PathVariable(value = "cityId") long cityId) {
        return daoWthWeatherDetails.findAll();
    }

    @PostMapping("/weathers")
    public Map<String, Boolean> saveWeather(@Valid @RequestBody WthWeatherDetails wthWeatherDetails) throws ResourceNotFoundException {
      
        daoWthWeatherDetails.saveAndFlush(wthWeatherDetails);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("Inserted", Boolean.TRUE);

        return response;
    }

    @GetMapping("/weathers/{cityId}")
    public List<WeatherOutput> getWeatherByCityAndPeriod(
        @PathVariable(value = "cityId") long cityId
       ,@Valid @RequestBody WeatherInput input )throws Exception {

        List<WeatherOutput> output = new ArrayList<>();

        Date dtFrom = new SimpleDateFormat("yyyy-MM-dd").parse(input.getDtFrom());
        Date dtEnd = new SimpleDateFormat("yyyy-MM-dd").parse(input.getDtEnd());

        List<WthWeatherDetails> weatherDetails = daoWthWeatherDetails.findByCityIdAndRangeDt(cityId, dtFrom, dtEnd);   
        WthCity cityBase =  daoWthCity.findById(cityId);   

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

        logger.info("Okee output {}", output);

		return output;
    }

    private void setWeatherDesc(WthWeatherDetails row, WeatherOutput tr) {
        if(daoWthWeatherBase.findById(row.getWeatherBaseId()) != null){
            tr.setWeatherBase(daoWthWeatherBase.findById(row.getWeatherBaseId()));
        }else{
            tr.setWeatherBase(new WthWeatherBase());
        }
    }

    @PutMapping("/weathers/{id}")
    public Map<String, Boolean> updateWeather(@PathVariable(value = "id") long id,@Valid @RequestBody WthWeatherDetails weatherDetails) throws ResourceNotFoundException {
        logger.info("Okee WeathersDetails {}", weatherDetails);
 
        WthWeatherDetails nWtDetails = daoWthWeatherDetails.findById(id);
        nWtDetails.setCityId(weatherDetails.getCityId());
        nWtDetails.setWeatherBaseId(weatherDetails.getWeatherBaseId());
        nWtDetails.setWeatherDt(weatherDetails.getWeatherDt());
        nWtDetails.setTemperature(weatherDetails.getTemperature());
        nWtDetails.setTempMin(weatherDetails.getTempMin());
        nWtDetails.setTempMax(weatherDetails.getTempMax());
        nWtDetails.setPressure(weatherDetails.getPressure());
        nWtDetails.setHumidity(weatherDetails.getHumidity());

        daoWthWeatherDetails.save(nWtDetails);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);

        return response;
    }

    @DeleteMapping("/weathers/{id}")
    public Map<String, Boolean> deleteWeather(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
       
        WthWeatherDetails weatherDetails = daoWthWeatherDetails.findById(id);

        daoWthWeatherDetails.delete(weatherDetails);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
                
        return response;
    }
}