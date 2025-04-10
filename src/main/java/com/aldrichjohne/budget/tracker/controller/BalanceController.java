package com.aldrichjohne.budget.tracker.controller;

import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/balance/v1")
public class BalanceController {
    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> updateBalance(@RequestBody BalanceRequestDTO requestDTO) throws IllegalAccessException {
        return ResponseEntity.ok(balanceService.updateBalance(requestDTO));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getBalance() {
        return ResponseEntity.ok(balanceService.getBalance());
    }
}
