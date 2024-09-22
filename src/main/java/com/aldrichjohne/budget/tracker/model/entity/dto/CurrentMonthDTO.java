package com.aldrichjohne.budget.tracker.model.entity.dto;

import com.aldrichjohne.budget.tracker.model.entity.Bills;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CurrentMonthDTO {
    private UUID id;
    private Date month;
    private Bills bill;
    private double budget;
}
