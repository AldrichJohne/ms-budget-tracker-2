package com.aldrichjohne.budget.tracker.model.dto;

import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCashResponseDto {
    InflowDTO inflow;
    BalanceDTO updatedBalance;
}
