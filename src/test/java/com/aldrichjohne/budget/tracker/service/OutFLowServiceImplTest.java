package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InputIsNullException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.InsufficientBalanceException;
import com.aldrichjohne.budget.tracker.exception.customexceptions.MandatoryInputMissingException;
import com.aldrichjohne.budget.tracker.model.entity.OutFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.OutFlowDTO;
import com.aldrichjohne.budget.tracker.repository.OutFlowRepo;
import com.aldrichjohne.budget.tracker.service.impl.OutFlowServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.OutFlowMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OutFLowServiceImplTest {
    @InjectMocks
    OutFlowServiceImpl service;

    @Mock
    OutFlowRepo outFlowRepo;

    @Mock
    BalanceService balanceService;

    @Mock
    BalanceQueryService balanceQueryService;

    @Mock
    BillsService billsService;


    @Test
    void payBill_OK() throws IllegalAccessException, MandatoryInputMissingException, InputIsNullException, InsufficientBalanceException {
        UUID id = UUID.randomUUID();
        UUID billId = UUID.randomUUID();
        OutFlowDTO outFlowDTO = new OutFlowDTO(id, LocalDateTime.now(), billId.toString(), new BigDecimal("2600.54"), "");
        OutFlow outFlow = Objects.requireNonNull(OutFlowMapper.convert(Either.right(outFlowDTO))).getLeft();

        Mockito.when(balanceQueryService.getBalance()).thenReturn(
                new ResponseWrapper(new BalanceDTO(UUID.randomUUID(), 50000.00), ResponseWrapperStatus.OK.name(), "Retrieve Balance: Success"));
        Mockito.when(outFlowRepo.save(Mockito.any())).thenReturn(outFlow);
        Mockito.when(balanceService.updateBalance(Mockito.any(), Mockito.any())).thenReturn(new ResponseWrapper(
                new BalanceDTO(null, 35000), ResponseWrapperStatus.OK.name(), "Update Balance: Success"));

        ResponseWrapper result = service.payBill(outFlowDTO);
        OutFlowDTO resultBody = (OutFlowDTO) result.getResponse();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(billId.toString(), resultBody.getBillId());

    }

    @Test
    void payBill_null_input() {
        Assertions.assertThrows(InputIsNullException.class, () -> {
            service.payBill(null);
        });
    }

    @Test
    void payBill_mandatory_input_missing() {
        Assertions.assertThrows(MandatoryInputMissingException.class, () -> {
            service.payBill(new OutFlowDTO(UUID.randomUUID(), null, null, null, ""));
        });
    }

    @Test
    void payBill_mandatory_input_missing_amount() {
        Assertions.assertThrows(MandatoryInputMissingException.class, () -> {
            service.payBill(new OutFlowDTO(UUID.randomUUID(), null, UUID.randomUUID().toString(), null, ""));
        });
    }

    @Test
    void payBill_bill_dont_exists() {
        UUID id = UUID.randomUUID();
        UUID billId = UUID.randomUUID();

        Mockito.when(billsService.getBill(Mockito.any())).thenThrow(new EntityNotFoundException("Retrieve Bill: Bill with ID = " + billId + " not found"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.payBill(new OutFlowDTO(id, null, billId.toString(), new BigDecimal("2750.25"), ""));
        });
    }

    @Test
    void payBill_not_enough_balance() {
        UUID billId = UUID.randomUUID();
        Mockito.when(billsService.getBill(Mockito.any())).thenReturn(
                new ResponseWrapper(
                        new BillsDTO(billId, "Electric Bill", new BigDecimal("4000"), false),
                        ResponseWrapperStatus.OK.name(),
                        ""
                ));
        Mockito.when(balanceQueryService.getBalance()).thenReturn(
                new ResponseWrapper(
                        new BalanceDTO(UUID.randomUUID(), 1000.00),
                        ResponseWrapperStatus.OK.name(),
                        ""
                ));

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            service.payBill(new OutFlowDTO(UUID.randomUUID(), null, billId.toString(), new BigDecimal("2500.00"), ""));
        });

    }
}
