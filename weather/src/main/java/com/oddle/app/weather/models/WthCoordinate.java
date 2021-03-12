package com.oddle.app.weather.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "wth_coordinate")
public class WthCoordinate implements Serializable {

    private static final long serialVersionUID = -8546777162330650796L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)  
    private long id;

    @Column(name = "city_id", nullable = true)
    @NotEmpty(message="* Please Enter City Id")
    private String cityId;

    @Column(name = "long", nullable = true)  
    private String longt;

    @Column(name = "lat", nullable = true)  
    private String lat;
}
