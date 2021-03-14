package com.oddle.app.weather.repositories;
import com.oddle.app.weather.models.WthWeatherBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoWthWeatherBase extends JpaRepository<WthWeatherBase,Long> {
    WthWeatherBase findById(long id);    

}
