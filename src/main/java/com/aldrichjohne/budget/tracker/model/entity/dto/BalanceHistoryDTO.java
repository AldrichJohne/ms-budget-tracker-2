package com.aldrichjohne.budget.tracker.model.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class BalanceHistoryDTO {
    private UUID id;
    @NotNull(message = "Flow ID cannot be null")
    private UUID flowId;
    @NotNull(message = "Change Amount cannot be null")
    private BigDecimal changeAmount;
    private BigDecimal balanceAfter;
    private LocalDateTime timestamp;
    private String remarks;
}
