package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

public interface InFlowService {
    ResponseWrapper addCash(InflowDTO inflowDTO) throws IllegalAccessException;
}
