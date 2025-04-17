package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.dto.AddCashResponseDto;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.InFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import com.aldrichjohne.budget.tracker.repository.InFlowRepo;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.service.InFlowService;
import com.aldrichjohne.budget.tracker.service.helper.ActorHelper;
import com.aldrichjohne.budget.tracker.util.mapper.InFlowMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class InFlowServiceImpl implements InFlowService {
    private final InFlowRepo repo;
    private final BalanceService balanceService;
    private final ActorHelper actorServiceHelper;

    public InFlowServiceImpl(
            InFlowRepo repo,
            BalanceService balanceService,
            ActorHelper actorServiceHelper) {
        this.repo = repo;
        this.balanceService = balanceService;
        this.actorServiceHelper = actorServiceHelper;
    }

    @Override
    @Transactional
    public ResponseWrapper addCash(InflowDTO inflowDTO) throws IllegalAccessException {
        if(Objects.isNull(inflowDTO)) {
            throw new IllegalArgumentException("Add Cash: Input Body is Null");
        }
        checkIfActorExists(UUID.fromString(inflowDTO.getUserId()));

        inflowDTO.setDate(LocalDateTime.now());

        InFlow savedInflow = repo.save(Objects.requireNonNull(InFlowMapper.convert(Either.right(inflowDTO))).getLeft());
        ResponseWrapper updateBalanceResponse = balanceService.updateBalance(
                new BalanceRequestDTO(
                        null,
                        "add",
                        savedInflow.getAmount()),
                savedInflow.getId().toString());

        BalanceDTO updatedBalance = (BalanceDTO) updateBalanceResponse.getResponse();

        return new ResponseWrapper(
                new AddCashResponseDto(
                        Objects.requireNonNull(InFlowMapper.convert(Either.left(savedInflow))).get(),
                        Objects.requireNonNull(updatedBalance)),
                ResponseWrapperStatus.OK.name(), "Add Cash: Success");
    }

    private void checkIfActorExists(UUID id) {
        actorServiceHelper.findActorOrThrow(
                id,
                "Inflow: Actor ID is null",
                "Inflow: Actor not found"
        );
    }

}
