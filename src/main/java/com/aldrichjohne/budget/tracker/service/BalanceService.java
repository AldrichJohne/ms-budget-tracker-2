package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

public interface BalanceService {
    ResponseWrapper updateBalance(BalanceRequestDTO requestDTO, String flowId) throws IllegalAccessException;
}
