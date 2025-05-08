package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime date;
    private String billId;
    private BigDecimal amount;
    private String remarks;
}
