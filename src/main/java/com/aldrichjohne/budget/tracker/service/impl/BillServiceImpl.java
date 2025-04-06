package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.Bills;
import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import com.aldrichjohne.budget.tracker.repository.BillRepo;
import com.aldrichjohne.budget.tracker.service.BillsService;
import com.aldrichjohne.budget.tracker.util.mapper.ActorMapper;
import com.aldrichjohne.budget.tracker.util.mapper.BillsMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
public class BillServiceImpl implements BillsService {
    private final BillRepo billRepo;

    public BillServiceImpl(BillRepo billRepo) {
        this.billRepo = billRepo;
    }

    @Override
    public ResponseWrapper addBill(BillsDTO bill) {
        try {
            if (Objects.isNull(bill)) {
                return new ResponseWrapper("", "Error", "Input while adding a bill is null");
            }
            Bills bills = billRepo.save(Objects.requireNonNull(BillsMapper.convert(Either.right(bill)), "Input while adding bill cannot be null").getLeft());
            log.info("Successfully added an bill; {}", bills);
            return new ResponseWrapper(
                    Objects.requireNonNull(BillsMapper.convert(Either.left(bills)), "Input while adding bill cannot be null").get(),
                    "Success",
                    "Successfully added a bill"
            );
        } catch (DataIntegrityViolationException exception) {
            log.error("Error occurred on saving new bill due to Data Integrity Violation; {}", exception.getMessage());
            return new ResponseWrapper(
                    exception.getMessage(), "Error", "Error occurred on saving new bill due to Data Integrity Violation"
            );
        } catch (Exception exception) {
            log.error("Error occurred on saving new bill; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred on saving new bill");
        }
    }

    @Override
    public ResponseWrapper removeBill(UUID id) {
        return null;
    }

    @Override
    public ResponseWrapper updateBill(UUID id, BillsDTO dto) {
        return null;
    }

    @Override
    public ResponseWrapper getBill(UUID id) {
        return null;
    }

    @Override
    public ResponseWrapper getBillWithoutDeleted() {
        return null;
    }

    @Override
    public ResponseWrapper getAllBillsWithDeleted() {
        return null;
    }
}
