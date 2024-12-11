package com.railwayBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railwayBooking.model.Train;

public interface TrainRepository extends JpaRepository<Train,Integer>{

}
