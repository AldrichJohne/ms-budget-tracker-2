package com.aldrichjohne.budget.tracker.model.entity.dto;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InflowDTO {
    private UUID id;
    private LocalDateTime date;
    private double amount;
    private String userId;
    private String remarks;
}
