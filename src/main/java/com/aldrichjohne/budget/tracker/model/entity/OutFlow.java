package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "outflow")
@Data
@AllArgsConstructor
public class OutFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id")
    private Bills bill;

    private double amount;

    private String remarks;
}
