package com.aldrichjohne.budget.tracker.model.entity.dto;

import com.aldrichjohne.budget.tracker.model.entity.Bills;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OutFlowDTO {
    private UUID id;
    private Date date;
    private Bills bill;
    private double amount;
    private String remarks;
}
