package com.oddle.app.weather.repositories;

import java.time.LocalDate;
import java.util.List;

import com.oddle.app.weather.models.WthWeatherDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoWthWeatherDetails extends JpaRepository<WthWeatherDetails,Long> {
	WthWeatherDetails findById(long id);    
	//List<WthWeatherDetails> findByCityIdAndWeatherDtAfterAndWeatherDtBefore(Long cityId, Date endDt, Date fromDt);
	

	@Query( value= "\n select *                        "
	             + "\n  from wth_weather_details wtd   "
	             + "\n  where 1=1                      "                                               
				 + "\n  and wtd.city_id = :cityId      "
				 + "\n  and (wtd.weather_dt between DATE(:fromDt) and DATE(:endDt))"
				 + "\n  order by wtd.weather_dt desc   "
	, nativeQuery = true)

List<WthWeatherDetails> findByCityIdAndRangeDt(@Param("cityId") long cityId
											 , @Param("fromDt") LocalDate fromDt
											 , @Param("endDt")  LocalDate endDt);

}
