package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceHistoryDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.BalanceHistoryService;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.service.BalanceQueryService;
import com.aldrichjohne.budget.tracker.util.mapper.BalanceMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepo balanceRepo;
    private final BalanceHistoryService balanceHistoryService;

    public BalanceServiceImpl(
            final BalanceRepo balanceRepo,
            final BalanceHistoryService balanceHistoryService) {
        this.balanceRepo = balanceRepo;
        this.balanceHistoryService = balanceHistoryService;
    }

    @Override
    @Transactional
    public ResponseWrapper updateBalance(final BalanceRequestDTO balanceRequestDTO, String flowId)
            throws NoSuchElementException {

        if (Objects.isNull(balanceRequestDTO)) {
            throw new IllegalArgumentException("Update Balance: Input Body is null");
        }

        balanceHistoryService.addBalanceHistory(
                new BalanceHistoryDTO(
                        null,
                        UUID.fromString(flowId),
                        BigDecimal.valueOf(balanceRequestDTO.getAmount()),
                        null, LocalDateTime.now(), ""));

        UUID balanceId = balanceRepo.findAll().getFirst().getId();
        Balance currentBalance = balanceRepo.findById(balanceId).orElseThrow(
                () -> new EntityNotFoundException("Update Balance: Balance record with ID = " + balanceId + " not found"));

        final BalanceDTO newBalanceDto = new BalanceDTO();

        newBalanceDto.setId(balanceId);
        newBalanceDto.setRemainingBalance(this.operation(
                currentBalance.getRemainingBalance(),
                balanceRequestDTO.getAmount(),
                balanceRequestDTO.getOperation()
        ));
        final Balance newBalanceEntity = balanceRepo.save(Objects.requireNonNull(BalanceMapper.convert(Either.right(newBalanceDto))).getLeft());

        return new ResponseWrapper(
                Objects.requireNonNull(BalanceMapper.convert(Either.left(newBalanceEntity))).get(),
                ResponseWrapperStatus.OK.toString(),
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
}