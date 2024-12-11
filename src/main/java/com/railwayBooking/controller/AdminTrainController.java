package com.railwayBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.railwayBooking.config.ApiKeyConfig;
import com.railwayBooking.model.Train;
import com.railwayBooking.repository.RouteRepository;
import com.railwayBooking.repository.TrainRepository;
import com.railwayBooking.service.TrainService;

@RestController
@RequestMapping("/api/admin")
public class AdminTrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ApiKeyConfig apiKeyConfig;

    @PostMapping("/addTrain")
    public ResponseEntity<?> addTrain(
            @RequestBody Train train,
            @RequestHeader("x-api-key") String apiKey) {
        if (!apiKeyConfig.validateAdminApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            Train addedTrain = trainService.addTrain(train);
            return ResponseEntity.ok(addedTrain);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTrain")
    public ResponseEntity<?> deleteAllTrainsAndRoutes(
            @RequestHeader("x-api-key") String apiKey) {
        if (!apiKeyConfig.validateAdminApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        try {
            trainRepository.deleteAll();
            routeRepository.deleteAll();
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
