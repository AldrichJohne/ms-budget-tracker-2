package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "inflow")
@Data
@AllArgsConstructor
public class InFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Date date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "id")
    private Actor person;

    private String remarks;
}
