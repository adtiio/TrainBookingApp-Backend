package com.railwayBooking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.railwayBooking.model.Route;
import com.railwayBooking.model.Train;
import com.railwayBooking.repository.TrainRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    private final TrainRepository trainRepository;

    public BookingService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Transactional
    public synchronized int bookTicket(int trainId, int src, int dest) throws Exception{
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new Exception("Train not found"));

        List<Route> routes = train.getRoutes();

        int availableTickets = availableTicket(train, src, dest);
        if (availableTickets <= 0) {
            throw new Exception("No tickets available for this route!");
        }

        synchronized (train) {
            int srcCount = routes.get(src).getPassengers();
            int destCount = routes.get(dest).getPassengers();
            routes.get(src).setPassengers(srcCount + 1);
            routes.get(dest).setPassengers(destCount - 1);
        }
        return availableTicket(train,src,dest);
    }

    public int availableTicket(Train train, int src, int dest) {
        List<Route> routes = train.getRoutes();
        int curr = 0;
        int maxi = 0;

        for (int i = 0; i < routes.size(); i++) {
            curr += routes.get(i).getPassengers();
            if (i >= src && i < dest) {
                maxi = Math.max(maxi, curr);
            }
        }
        return train.getTotalTickets() - maxi;
    }
}
