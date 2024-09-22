package com.aldrichjohne.budget.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "current_month")
@Data
@AllArgsConstructor
public class CurrentMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "month_date")
    private Date month;

    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id")
    private Bills bill;

    private double budget;
}
