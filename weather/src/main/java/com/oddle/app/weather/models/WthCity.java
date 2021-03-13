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
@Table(name = "wth_city")
public class WthCity implements Serializable {

    private static final long serialVersionUID = -8546777162330650796L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")
    private long id;
   
    @Column(name = "name", nullable = true)
    @NotEmpty(message="* Please Enter City Name")
    private String name;

    @Column(name = "state", nullable = true)
    private String state;

    @Column(name = "country", nullable = true)
    @NotEmpty(message="* Please Enter Coutry Name")
    private String country;

    @Column(name = "longt", nullable = true)  
    private Double longt;

    @Column(name = "lat", nullable = true)  
    private Double lat;

}
