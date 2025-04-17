package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inflow")
@Data
@AllArgsConstructor
public class InFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime date;
    private double amount;
    private String userId;
    private String remarks;
}
