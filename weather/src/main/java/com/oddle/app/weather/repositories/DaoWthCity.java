package com.oddle.app.weather.repositories;


import com.oddle.app.weather.models.WthCity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DaoWthCity extends JpaRepository<WthCity,Long> {

	WthCity findById(long id);

	@Query(value = "\n  SELECT MAX(ID) "
    			 + "\n  FROM WTH_CITY"
            , nativeQuery = true
            )
    public long getMaxId();
    
}
