package com.railwayBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.railwayBooking.model.Route;

import jakarta.persistence.LockModeType;

public interface RouteRepository extends JpaRepository<Route,Integer>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Route> findByTrainIdAndStationNameBetween(int trainId, String startStation, String endStation);

    Route findByStationNameAndTrain_Id(String stationName, int trainId);

   
}
