package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.Bills;
import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import com.aldrichjohne.budget.tracker.repository.BillRepo;
import com.aldrichjohne.budget.tracker.service.BillsService;
import com.aldrichjohne.budget.tracker.util.mapper.BillsMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BillServiceImpl implements BillsService {
    private final BillRepo billRepo;

    public BillServiceImpl(BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    @Override
    public ResponseWrapper addBill(BillsDTO bill) {
        if (Objects.isNull(bill)) {
            throw new IllegalArgumentException("Add Bill: Input is null");
        }

        Optional<Bills> existingDuplicate = billRepo.findByName(bill.getName());

        if (existingDuplicate.isPresent()) {
            log.info("Add Bill: Unsuccessful: Bill already exists");
            return new ResponseWrapper(
                    existingDuplicate.get(),
                    ResponseWrapperStatus.OK.toString(),
                    "Add Bill: Unsuccessful: Bill already exists");
        }

        Bills bills = billRepo.save(Objects.requireNonNull(BillsMapper.convert(Either.right(bill))).getLeft());
        log.info("Add Bill: Success: {}", bills);
        return new ResponseWrapper(
                Objects.requireNonNull(BillsMapper.convert(Either.left(bills))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Add Bill: Success"
        );
    }

    @Override
    public ResponseWrapper removeBill(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Remove Bill: Input ID is null");
        }

        Bills bills = billRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Remove Bill: Bill with ID = " + id + "not found"));
        billRepo.delete(bills);
        log.info("Delete Bill: Success: {}", bills);

        return new ResponseWrapper(
                Objects.requireNonNull(BillsMapper.convert(Either.left(bills))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Delete Bill: Success");
    }

    @Override
    public ResponseWrapper softRemoveBill(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Remove Bill: Input ID is null");
        }

        Bills bills = billRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Soft Remove Bill: Bill with ID = " + id + "not found"));
        bills.setDeleted(true);
        billRepo.save(bills);
        log.info("Soft Delete Bill: Success: {}", bills);

        return new ResponseWrapper(
                Objects.requireNonNull(BillsMapper.convert(Either.left(bills))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Soft Delete Bill: Success");
    }

    @Override
    public ResponseWrapper updateBill(BillsDTO dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Update Bill: Input Body is null");
        }
        UUID inputId = dto.getId();
        billRepo.findById(inputId).orElseThrow(() -> new EntityNotFoundException("Update Actor: Actor with ID = " + inputId + " not found"));
        Bills billsUpdatedValue = billRepo.save(Objects.requireNonNull(BillsMapper.convert(Either.right(dto))).getLeft());
        log.info("Update Bill: Success: {}", dto);
        return new ResponseWrapper(
                Objects.requireNonNull(BillsMapper.convert(Either.left(billsUpdatedValue))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Update Bill: Success"
        );
    }

    @Override
    public ResponseWrapper getBill(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Retrieve Bill: Input ID is null");
        }

        Bills bills = billRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Retrieve Bill: Bill with ID = " + id + " not found"));
        log.info("Retrieve Bill: Success: {}", bills);

        return new ResponseWrapper(
                Objects.requireNonNull(BillsMapper.convert(Either.left(bills))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Retrieve Bill: Success"
        );
    }

    @Override
    public ResponseWrapper getBillWithoutDeleted() {
        List<BillsDTO> billsList = billRepo.findAll().stream()
                .filter(b -> !b.isDeleted())
                .map(b -> Objects.requireNonNull(BillsMapper.convert(Either.left(b))).get())
                .toList();

        return new ResponseWrapper(billsList, ResponseWrapperStatus.OK.toString(), "Retrieve Bills without deleted: Success");
    }

    @Override
    public ResponseWrapper getAllBillsWithDeleted() {
        List<BillsDTO> billsList = billRepo.findAll().stream()
                .map(b -> Objects.requireNonNull(BillsMapper.convert(Either.left(b))).get())
                .toList();

        return new ResponseWrapper(billsList, ResponseWrapperStatus.OK.toString(), "Retrieve All Bills: Success");
    }

}
