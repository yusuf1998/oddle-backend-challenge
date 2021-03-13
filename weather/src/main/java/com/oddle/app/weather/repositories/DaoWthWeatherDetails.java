package com.oddle.app.weather.repositories;

import com.oddle.app.weather.models.WthCity;
import com.oddle.app.weather.models.WthWeatherDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoWthWeatherDetails extends JpaRepository<WthWeatherDetails,Long> {
	WthCity findById(long id);    
}
