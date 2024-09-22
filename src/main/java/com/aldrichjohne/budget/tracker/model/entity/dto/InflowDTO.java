package com.aldrichjohne.budget.tracker.model.entity.dto;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InflowDTO {
    private UUID id;
    private Date date;
    private double amount;
    private Actor person;
    private String remarks;
}
