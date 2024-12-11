package com.railwayBooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railwayBooking.model.Booking;
import com.railwayBooking.repository.BookingRepository;

@RestController
@RequestMapping("/api/trains")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/showAllBookings")
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    @GetMapping("/showUserBooking")
    public List<Booking> getBookingsOfUser(@RequestParam String username){
        return bookingRepository.findByUsername(username);
    }
}
