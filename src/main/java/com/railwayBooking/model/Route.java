package com.railwayBooking.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer stationNumber;

    private String stationName;

    private int passengers=0;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonIgnore
    private Train train;

    public Route(String stationName){
        this.stationName=stationName;
    }

}
