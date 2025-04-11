package com.aldrichjohne.budget.tracker.controller;

import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import com.aldrichjohne.budget.tracker.service.BillsService;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bills/v1")
public class BillsController {
    private final BillsService billsService;

    public BillsController(BillsService billsService) {
        this.billsService = billsService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createBill(@RequestBody BillsDTO billsDTO){
        return ResponseEntity.ok(billsService.addBill(billsDTO));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseWrapper> removeBill(@PathVariable UUID id){
        return ResponseEntity.ok(billsService.softRemoveBill(id));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> update(@RequestBody BillsDTO billsDTO){
        return ResponseEntity.ok(billsService.updateBill(billsDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getBill(@PathVariable UUID id){
        return ResponseEntity.ok(billsService.getBill(id));
    }

    @GetMapping("/all/active")
    public ResponseEntity<ResponseWrapper> getBillWithoutDeleted(){
        return ResponseEntity.ok(billsService.getBillWithoutDeleted());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllBill(){
        return ResponseEntity.ok(billsService.getAllBillsWithDeleted());
    }
}
