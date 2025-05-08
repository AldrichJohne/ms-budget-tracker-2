package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.exception.customexceptions.InputIsNullException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InsufficientBalanceException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.MandatoryInputMissingException;
import com.aldrichjohne.budget.tracker.model.entity.dto.OutFlowDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

public interface OutFlowService {
    ResponseWrapper payBill(OutFlowDTO outFlowDTO) throws MandatoryInputMissingException, InputIsNullException, InsufficientBalanceException, IllegalAccessException;
}
