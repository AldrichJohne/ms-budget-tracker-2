package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.BalanceQueryService;
import com.aldrichjohne.budget.tracker.util.mapper.BalanceMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class BalanceQueryServiceImpl implements BalanceQueryService {
    private final BalanceRepo balanceRepo;

    public BalanceQueryServiceImpl(BalanceRepo balanceRepo) {
        this.balanceRepo = balanceRepo;
    }

    @Override
    public ResponseWrapper getBalance() {
        Balance currentBalance = balanceRepo.findAll().getFirst();
        return new ResponseWrapper(
                Objects.requireNonNull(BalanceMapper.convert(Either.left(currentBalance))).get(),
                ResponseWrapperStatus.OK.toString(),
                "Retrieve Balance: Success"
        );
    }
}
