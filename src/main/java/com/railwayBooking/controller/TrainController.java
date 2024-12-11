package com.railwayBooking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.railwayBooking.config.JwtProvider;
import com.railwayBooking.model.Booking;
import com.railwayBooking.model.Route;
import com.railwayBooking.model.Train;
import com.railwayBooking.model.User;
import com.railwayBooking.repository.BookingRepository;
import com.railwayBooking.repository.RouteRepository;
import com.railwayBooking.repository.TrainRepository;
import com.railwayBooking.repository.UserRepository;
import com.railwayBooking.service.BookingService;
import com.railwayBooking.service.TrainService;


@RestController
@RequestMapping("/api/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public ResponseEntity<?> getAllTrains() {
        return ResponseEntity.ok(trainService.getAllTrains());
    }

    @PostMapping("/{trainId}/book")
    public ResponseEntity<?> bookTickets(
            @PathVariable int trainId,
            @RequestParam int src,
            @RequestParam int dest,
            @RequestHeader("Authorization") String token) {
        try {
            String email = JwtProvider.getEmailFromJwtToken(token);
            Train train = trainRepository.findById(trainId).orElseThrow();
            User user=userRepository.findByEmail(email);
            String source=train.getRoutes().get(src-1).getStationName();
            String destination=train.getRoutes().get(dest-1).getStationName();

            int ticketsLeft = bookingService.bookTicket(trainId, src - 1, dest - 1);

            

            Booking booking=new Booking();
            booking.setUsername(user.getUsername());
            booking.setBookingTime(LocalDate.now());
            booking.setStatus("Confirm");
            booking.setSource(source);
            booking.setDestination(destination);
            Booking savedBooking=bookingRepository.save(booking);


            return ResponseEntity.ok("Ticket successfully booked!" +"\nBooking details: "+savedBooking+"\navailable tickets from source: "
                            +source +" to destination: "+destination +" are: "+ticketsLeft);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{trainId}/bookByName")
    public ResponseEntity<?> bookTicketByName(
            @PathVariable int trainId,
            @RequestParam String source,
            @RequestParam String destination,
            @RequestHeader("Authorization") String token) {
        try {
            String email = JwtProvider.getEmailFromJwtToken(token);
            Route srcRoute = routeRepository.findByStationNameAndTrain_Id(source, trainId);
            Route destRoute = routeRepository.findByStationNameAndTrain_Id(destination, trainId);
            User user=userRepository.findByEmail(email);

            if(srcRoute==null) return new ResponseEntity<>("Source with name:"+source+" is not available",HttpStatus.BAD_REQUEST);
            if(destRoute==null) return new ResponseEntity<>("Destination with name:"+destination+" is not available",HttpStatus.BAD_REQUEST);

            int src=srcRoute.getStationNumber()-1;
            int dest=destRoute.getStationNumber()-1;

            int availableTicket = bookingService.bookTicket(trainId, src, dest);

            Booking booking=new Booking();
            booking.setUsername(user.getUsername());
            booking.setBookingTime(LocalDate.now());
            booking.setStatus("Confirm");
            booking.setSource(source);
            booking.setDestination(destination);
            Booking savedBooking=bookingRepository.save(booking);

            return ResponseEntity.ok("Ticket successfully booked!" +"\nBooking details: "+savedBooking+"\navailable tickets from source: "
                        +source +" to destination: "+destination +" are: "+availableTicket);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<?> getSeatAvailability(@RequestParam String source, @RequestParam String destination) {
        List<String> list=new ArrayList<>();
        for (Train train : trainService.getAllTrains()) {
            String trainName=train.getTrainName();
            Route srcRoute = routeRepository.findByStationNameAndTrain_Id(source, train.getId());
            Route destRoute = routeRepository.findByStationNameAndTrain_Id(destination, train.getId());

            if (srcRoute != null && destRoute != null && srcRoute.getStationNumber() < destRoute.getStationNumber()) {
                int availableSeats = bookingService.availableTicket(train, srcRoute.getStationNumber() - 1, destRoute.getStationNumber() - 1);
                if(availableSeats>0){
                    list.add(availableSeats+" are available in train: "+"'"+trainName+"'"+ " with train id= "+train.getId());
                }
            }
        }
        return ResponseEntity.ok(list);
    }
    

}
