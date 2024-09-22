package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;

public interface BalanceService {
    BalanceDTO updateBalance(BalanceRequestDTO requestDTO) throws IllegalAccessException;
    Balance getBalance();
}
