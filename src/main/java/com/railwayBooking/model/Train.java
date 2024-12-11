package com.railwayBooking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trainName;

    private int totalTickets;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;

    public Train(int totalTickets){
        this.totalTickets=totalTickets;
    }

}
