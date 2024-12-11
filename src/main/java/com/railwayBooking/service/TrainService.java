package com.railwayBooking.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railwayBooking.model.Route;
import com.railwayBooking.model.Train;
import com.railwayBooking.repository.RouteRepository;
import com.railwayBooking.repository.TrainRepository;

import java.util.*;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;

    public TrainService(TrainRepository trainRepository, RouteRepository routeRepository) {
        this.trainRepository = trainRepository;
        this.routeRepository=routeRepository;
    }

    @Transactional
    public Train addTrain(Train train) throws Exception{
       
       List<Route> routes=train.getRoutes();
       Collections.sort(routes,(a,b)->a.getStationNumber()-b.getStationNumber());

       int lastStation=routes.get(routes.size()-1).getStationNumber();
       if(routes.size()!=lastStation){
        throw new Exception("Please add all the stations with station number from 1 to n");
       }     

        HashSet<Integer> set=new HashSet<>();
        for(Route route : train.getRoutes()){
            if(set.contains(route.getStationNumber())) throw new Exception("Please add unique station number for all stations from 1 to n");
            set.add(route.getStationNumber());
        }

        Train savedTrain=trainRepository.save(train);
        for(Route route : train.getRoutes()){
            route.setTrain(savedTrain);
        }
        routeRepository.saveAll(savedTrain.getRoutes());
        trainRepository.save(savedTrain);

        return savedTrain;
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }
}
