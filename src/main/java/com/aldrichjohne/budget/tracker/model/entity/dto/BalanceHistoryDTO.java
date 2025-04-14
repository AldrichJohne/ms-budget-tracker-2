package com.aldrichjohne.budget.tracker.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BalanceHistoryDTO {
    private UUID id;
    private UUID flowId;
    private BigDecimal changeAmount;
    private BigDecimal balanceAfter;
    private LocalDateTime timestamp;
    private String remarks;
}
