package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.util.mapper.BalanceMapper;
import io.vavr.control.Either;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepo balanceRepo;

    public BalanceServiceImpl(final BalanceRepo balanceRepo) {
        this.balanceRepo = balanceRepo;
    }

    @Override
    public BalanceDTO updateBalance(final BalanceRequestDTO balanceRequestDTO)
            throws NoSuchElementException {

        if (Objects.isNull(balanceRequestDTO)) {
            throw new IllegalArgumentException("Invalid input " + null);
        }

        final Optional<Balance> currentBalance = balanceRepo.findById(balanceRequestDTO.getId());
        if (currentBalance.isEmpty()) {
            throw new NoSuchElementException("Couldn't find current balance record from the database");
        }

        final BalanceDTO newBalanceDto = new BalanceDTO();
        newBalanceDto.setId(balanceRequestDTO.getId());
        newBalanceDto.setRemainingBalance(this.operation(
                currentBalance.get().getRemainingBalance(),
                balanceRequestDTO.getAmount(),
                balanceRequestDTO.getOperation()
        ));
        final Balance newBalanceEntity = balanceRepo.save(Objects.requireNonNull(BalanceMapper.convert(Either.right(newBalanceDto))).getLeft());

        return Objects.requireNonNull(BalanceMapper.convert(Either.left(newBalanceEntity))).get();
    }

    private double operation(double remainingBal, double amount, String operation) throws IllegalArgumentException {
        return switch (operation) {
            case "add" -> remainingBal + amount;
            case "subtract" -> {
                if (remainingBal < amount) {
                    throw new IllegalArgumentException("Insufficient balance");
                }
                yield remainingBal - amount;
            }
            default -> throw new IllegalArgumentException("Invalid operation type");
        };
    }

    @Override
    public Balance getBalance() {
        return balanceRepo.findAll().getFirst();
    }
}