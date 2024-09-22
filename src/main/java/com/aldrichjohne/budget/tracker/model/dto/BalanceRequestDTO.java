package com.aldrichjohne.budget.tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class BalanceRequestDTO {
    private UUID id;
    private String operation;
    private double amount;
}