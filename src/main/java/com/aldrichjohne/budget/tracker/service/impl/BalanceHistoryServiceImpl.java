package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.BalanceHistoryEntity;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceHistoryDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceHistoryRepo;
import com.aldrichjohne.budget.tracker.service.BalanceHistoryService;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.util.mapper.model.BalanceHistoryMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import com.aldrichjohne.budget.tracker.util.validator.ConstraintValidator;
import io.vavr.control.Either;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@Slf4j
public class BalanceHistoryServiceImpl implements BalanceHistoryService {
    private final BalanceHistoryRepo repo;
    public final BalanceService balanceService;
    private final Validator validator;

    public BalanceHistoryServiceImpl(BalanceHistoryRepo repo, BalanceService balanceService, Validator validator) {
        this.repo = repo;
        this.balanceService = balanceService;
        this.validator = validator;
    }


    @Override
    public ResponseWrapper addBalanceHistory(BalanceHistoryDTO balanceHistoryDTO) {

        if (Objects.isNull(balanceHistoryDTO)) {
            throw new IllegalArgumentException("Add Balance History: Null Input Body");
        }
        //TODO: Need fixing, the validator is not being triggered
        //TODO: ConstraintValidator.validate(balanceHistoryDTO, validator, "Add Balance History");

        BalanceDTO currentBalanceDto = (BalanceDTO) balanceService.getBalance().getResponse();
        double currentBalance = currentBalanceDto.getRemainingBalance();

        getBalanceAfter(BigDecimal.valueOf(currentBalance), balanceHistoryDTO);

        BalanceHistoryEntity newBalanceHistory = repo.save(
                Objects.requireNonNull(BalanceHistoryMapper.convert(Either.right(balanceHistoryDTO))).getLeft());


        return new ResponseWrapper(
                Objects.requireNonNull(BalanceHistoryMapper.convert(Either.left(newBalanceHistory))).get(),
                ResponseWrapperStatus.OK.name(),
                "Add Balance History: Success"
        );
    }

    @Override
    public ResponseWrapper retrieveBalanceHistory() {
        return new ResponseWrapper(
                repo.findAll(), ResponseWrapperStatus.OK.name(), "Retrieve Balance History: Success"
        );
    }

    private void getBalanceAfter(BigDecimal currentBalance, BalanceHistoryDTO dto) {
        dto.setBalanceAfter(currentBalance.add(dto.getChangeAmount()));
    }
}
