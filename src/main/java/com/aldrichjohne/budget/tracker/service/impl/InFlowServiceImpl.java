package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.dto.AddCashResponseDto;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.InFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import com.aldrichjohne.budget.tracker.repository.InFlowRepo;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.service.InFlowService;
import com.aldrichjohne.budget.tracker.util.mapper.BalanceMapper;
import com.aldrichjohne.budget.tracker.util.mapper.InFlowMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
public class InFlowServiceImpl implements InFlowService {
    private final InFlowRepo repo;
    private final BalanceService balanceService;

    public InFlowServiceImpl(InFlowRepo repo, BalanceService balanceService) {
        this.repo = repo;
        this.balanceService = balanceService;
    }

    @Override
    @Transactional
    public ResponseWrapper addCash(InflowDTO inflowDTO) throws IllegalAccessException {
        if(Objects.isNull(inflowDTO)) {
            throw new IllegalArgumentException("Add Cash: Input Body is Null");
        }

        InFlow savedInflow = repo.save(Objects.requireNonNull(InFlowMapper.convert(Either.right(inflowDTO))).getLeft());
        ResponseWrapper updateBalanceResponse = balanceService.updateBalance(new BalanceRequestDTO(null, "add", inflowDTO.getAmount()));
        BalanceDTO updatedBalance = (BalanceDTO) updateBalanceResponse.getResponse();

        return new ResponseWrapper(
                new AddCashResponseDto(
                        Objects.requireNonNull(InFlowMapper.convert(Either.left(savedInflow))).get(),
                        Objects.requireNonNull(updatedBalance)),
                ResponseWrapperStatus.OK.name(), "Add Cash: Success");
    }
}
