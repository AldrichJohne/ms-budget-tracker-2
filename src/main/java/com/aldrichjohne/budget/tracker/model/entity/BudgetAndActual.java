package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "budget_and_actual")
@Data
@AllArgsConstructor
public class BudgetAndActual {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "month_date")
    private Date month;

    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id")
    private Bills bill;

    private double budget;

    @Column(name = "actual_out")
    private double actualOutFlow;

    private double difference;
}
