package com.aldrichjohne.budget.tracker.controller;

import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import com.aldrichjohne.budget.tracker.service.InFlowService;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/txn/v1")
public class TransactionsController {
    private final InFlowService inFlowService;

    public TransactionsController(InFlowService inFlowService) {
        this.inFlowService = inFlowService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> addCash(@RequestBody InflowDTO inflowDTO) throws IllegalAccessException {
        return ResponseEntity.ok(inFlowService.addCash(inflowDTO));
    }
}
