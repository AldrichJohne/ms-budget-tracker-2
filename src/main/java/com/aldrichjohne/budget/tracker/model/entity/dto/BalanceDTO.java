package com.aldrichjohne.budget.tracker.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {
    private UUID id;
    private double remainingBalance;
}
