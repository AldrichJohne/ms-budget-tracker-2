package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.BalanceHistoryEntity;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceHistoryDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceHistoryRepo;
import com.aldrichjohne.budget.tracker.service.impl.BalanceHistoryServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.model.BalanceHistoryMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import com.aldrichjohne.budget.tracker.util.validator.ConstraintValidator;
import io.vavr.control.Either;
import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BalanceHistoryServiceImplTest {
    @InjectMocks
    BalanceHistoryServiceImpl service;

    @Mock
    BalanceHistoryRepo repo;

    @Mock
    BalanceService balanceService;

    @Mock
    Validator validator;

    @BeforeEach
    void setup() {
        service = new BalanceHistoryServiceImpl(repo, balanceService, validator);
    }

    @Test
    void addBalanceHistory_ok() {
        UUID uuid = UUID.randomUUID();
        UUID flowId = UUID.randomUUID();
        ResponseWrapper remainingBalance = new ResponseWrapper(
                new BalanceDTO(UUID.randomUUID(), 5000), ResponseWrapperStatus.OK.name(), "Retrieve Balance: Success");

        BalanceHistoryEntity balanceHistoryResponse = new BalanceHistoryEntity(
                uuid, flowId, new BigDecimal("10000.00"), new BigDecimal("15000.00"), LocalDateTime.now(), "");
        BalanceHistoryEntity balanceHistoryRequest = new BalanceHistoryEntity(
                uuid, flowId, new BigDecimal("10000.00"), new BigDecimal("0"), LocalDateTime.now(), "");

        Mockito.when(balanceService.getBalance()).thenReturn(remainingBalance);
        Mockito.when(repo.save(Mockito.any())).thenReturn(balanceHistoryResponse);

        ResponseWrapper result = service.addBalanceHistory(BalanceHistoryMapper.convert(Either.left(balanceHistoryRequest)).get());
        BalanceHistoryDTO balanceHistoryDTO = (BalanceHistoryDTO) result.getResponse();

        Assertions.assertEquals(new BigDecimal("15000.00"), balanceHistoryDTO.getBalanceAfter());
        Assertions.assertEquals("Add Balance History: Success", result.getMessage());

    }

    @Test
    void addBalanceHistory_error_illegal_arg_exception() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addBalanceHistory(null);
        });

        Assertions.assertEquals("Add Balance History: Null Input Body", exception.getMessage());
    }

    @Test
    void retrieveBalanceHistory_success() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid11 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid22 = UUID.randomUUID();
        List<BalanceHistoryEntity> balanceHistoryEntities = List.of(
                new BalanceHistoryEntity(uuid1, uuid11, new BigDecimal("5000.00"), new BigDecimal("10000.00"), LocalDateTime.now(), ""),
                new BalanceHistoryEntity(uuid2, uuid22, new BigDecimal("5000.00"), new BigDecimal("50000.00"), LocalDateTime.now(), "")
        );

        Mockito.when(repo.findAll()).thenReturn(balanceHistoryEntities);

        ResponseWrapper result = service.retrieveBalanceHistory();
        List<BalanceHistoryEntity> resultBody = (List<BalanceHistoryEntity>) result.getResponse();

        Assertions.assertEquals(2, resultBody.size());

    }

}
