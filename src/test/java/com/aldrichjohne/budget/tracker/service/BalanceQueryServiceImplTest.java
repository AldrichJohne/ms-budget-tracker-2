package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.impl.BalanceQueryServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BalanceQueryServiceImplTest {
    @InjectMocks
    BalanceQueryServiceImpl service;

    @Mock
    BalanceRepo repo;

    @Test
    void getBalance() {
        Balance balance = new Balance(UUID.randomUUID(), 30000.00);
        Mockito.when(repo.findAll()).thenReturn(List.of(balance));

        ResponseWrapper result = service.getBalance();
        BalanceDTO resultBody = (BalanceDTO) result.getResponse();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(30000.00, resultBody.getRemainingBalance());
    }


}
