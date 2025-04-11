package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

import java.util.List;
import java.util.UUID;

public interface BillsService {
    ResponseWrapper addBill(BillsDTO dto);
    ResponseWrapper removeBill(UUID id);
    ResponseWrapper softRemoveBill(UUID uuid);
    ResponseWrapper updateBill(BillsDTO dto);
    ResponseWrapper getBill(UUID id);
    ResponseWrapper getBillWithoutDeleted();
    ResponseWrapper getAllBillsWithDeleted();
}
