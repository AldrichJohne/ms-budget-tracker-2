package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.util.mapper.BalanceMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepo balanceRepo;

    public BalanceServiceImpl(final BalanceRepo balanceRepo) {
        this.balanceRepo = balanceRepo;
    }

    @Override
    public ResponseWrapper updateBalance(final BalanceRequestDTO balanceRequestDTO)
            throws NoSuchElementException {

        if (Objects.isNull(balanceRequestDTO)) {
            throw new IllegalArgumentException("Update Balance: Input Body is null");
        }

        UUID inputId = balanceRequestDTO.getId();

        Balance currentBalance = balanceRepo.findById(inputId).orElseThrow(
                () -> new EntityNotFoundException("Update Balance: Balance record with ID = " + inputId + " not found"));

        final BalanceDTO newBalanceDto = new BalanceDTO();

        newBalanceDto.setId(inputId);
        newBalanceDto.setRemainingBalance(this.operation(
                currentBalance.getRemainingBalance(),
                balanceRequestDTO.getAmount(),
                balanceRequestDTO.getOperation()
        ));
        final Balance newBalanceEntity = balanceRepo.save(Objects.requireNonNull(BalanceMapper.convert(Either.right(newBalanceDto))).getLeft());

        return new ResponseWrapper(
                Objects.requireNonNull(BalanceMapper.convert(Either.left(newBalanceEntity))).get(),
                ResponseWrapperStatus.SUCCESS.toString(),
                "Update Balance: Success"
        );
    }

    private double operation(double remainingBal, double amount, String operation) throws IllegalArgumentException {
        return switch (operation) {
            case "add" -> remainingBal + amount;
            case "subtract" -> {
                if (remainingBal < amount) {
                    throw new IllegalArgumentException("Update Balance: Insufficient balance");
                }
                yield remainingBal - amount;
            }
            default -> throw new IllegalArgumentException("Update Balance: Invalid operation type");
        };
    }

    @Override
    public ResponseWrapper getBalance() {
        return new ResponseWrapper(
                balanceRepo.findAll().getFirst(), ResponseWrapperStatus.SUCCESS.toString(), "Retrieve Balance: Success"
        );
    }
}