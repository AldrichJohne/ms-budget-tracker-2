package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InputIsNullException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InsufficientBalanceException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.MandatoryInputMissingException;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.OutFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.OutFlowDTO;
import com.aldrichjohne.budget.tracker.repository.OutFlowRepo;
import com.aldrichjohne.budget.tracker.service.BalanceQueryService;
import com.aldrichjohne.budget.tracker.service.BalanceService;
import com.aldrichjohne.budget.tracker.service.BillsService;
import com.aldrichjohne.budget.tracker.service.OutFlowService;
import com.aldrichjohne.budget.tracker.util.mapper.OutFlowMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutFlowServiceImpl implements OutFlowService {
    private final OutFlowRepo outFlowRepo;
    private final BalanceService balanceService;
    private final BalanceQueryService balanceQueryService;
    private final BillsService billsService;

    @Override
    @Transactional
    public ResponseWrapper payBill(OutFlowDTO outFlowDTO)
            throws MandatoryInputMissingException, InputIsNullException, InsufficientBalanceException, IllegalAccessException {

        this.validate(outFlowDTO);
        outFlowDTO.setDate(LocalDateTime.now());

        OutFlow savedOutFlow = outFlowRepo.save(Objects.requireNonNull(OutFlowMapper.convert(Either.right(outFlowDTO))).getLeft());

        balanceService.updateBalance(
                new BalanceRequestDTO(
                        null,
                        "subtract",
                        savedOutFlow.getAmount().doubleValue()),
                savedOutFlow.getId().toString());

        return new ResponseWrapper(
                Objects.requireNonNull(OutFlowMapper.convert(Either.left(savedOutFlow))).get(),
                ResponseWrapperStatus.OK.name(),
                "Pay bill: Success"
        );
    }

    private void validate(OutFlowDTO outFlowDTO)
            throws InputIsNullException, MandatoryInputMissingException, InsufficientBalanceException {
        if (Objects.isNull(outFlowDTO)) {
            throw new InputIsNullException("Pay bill: input is null");
        }

        if (Objects.isNull(outFlowDTO.getBillId()) || outFlowDTO.getBillId().isEmpty()) {
            throw new MandatoryInputMissingException("billId");
        } else if (Objects.isNull(outFlowDTO.getAmount())) {
            throw new MandatoryInputMissingException("amount");
        }

        billsService.getBill(UUID.fromString(outFlowDTO.getBillId()));

        BalanceDTO remainingBalanceResponse = (BalanceDTO) balanceQueryService.getBalance().getResponse();

        if (outFlowDTO.getAmount().compareTo(BigDecimal.valueOf(remainingBalanceResponse.getRemainingBalance())) > 0) {
            throw new InsufficientBalanceException("Pay bill: Insufficient Balance");}
    }
}
