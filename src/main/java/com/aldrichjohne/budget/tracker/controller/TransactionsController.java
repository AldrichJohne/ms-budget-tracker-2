package com.aldrichjohne.budget.tracker.controller;

import com.aldrichjohne.budget.tracker.exception.customexceptions.InputIsNullException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InsufficientBalanceException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.MandatoryInputMissingException;
import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.OutFlowDTO;
import com.aldrichjohne.budget.tracker.service.InFlowService;
import com.aldrichjohne.budget.tracker.service.OutFlowService;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/txn/v1")
public class TransactionsController {
    private final InFlowService inFlowService;
    private final OutFlowService outFlowService;

    public TransactionsController(
            InFlowService inFlowService,
            OutFlowService outFlowService) {
        this.inFlowService = inFlowService;
        this.outFlowService = outFlowService;
    }

    @PostMapping("/in")
    public ResponseEntity<ResponseWrapper> addCash(@RequestBody InflowDTO inflowDTO) throws IllegalAccessException {
        return ResponseEntity.ok(inFlowService.addCash(inflowDTO));
    }

    @PostMapping("/bill")
    public ResponseEntity<ResponseWrapper> payBill(@RequestBody OutFlowDTO outFlowDTO)
            throws MandatoryInputMissingException,
            InputIsNullException,
            InsufficientBalanceException,
            IllegalAccessException {
        return ResponseEntity.ok(outFlowService.payBill(outFlowDTO));
    }
}
