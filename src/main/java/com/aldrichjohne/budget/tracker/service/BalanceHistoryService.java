package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceHistoryDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

public interface BalanceHistoryService {
    ResponseWrapper addBalanceHistory(BalanceHistoryDTO balanceHistoryDTO);
    ResponseWrapper retrieveBalanceHistory();
}
