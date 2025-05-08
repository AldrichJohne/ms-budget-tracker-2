package com.aldrichjohne.budget.tracker.model.entity.dto;

import com.aldrichjohne.budget.tracker.model.entity.Bills;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OutFlowDTO {
    private UUID id;
    private LocalDateTime date;
    private String billId;
    private BigDecimal amount;
    private String remarks;
}
