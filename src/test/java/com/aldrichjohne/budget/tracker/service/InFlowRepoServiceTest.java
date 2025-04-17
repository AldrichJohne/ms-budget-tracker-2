package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.dto.AddCashResponseDto;
import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.InFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import com.aldrichjohne.budget.tracker.repository.InFlowRepo;
import com.aldrichjohne.budget.tracker.service.helper.ActorHelper;
import com.aldrichjohne.budget.tracker.service.impl.InFlowServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.InFlowMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class InFlowRepoServiceTest {
    @InjectMocks
    InFlowServiceImpl service;

    @Mock
    ActorHelper actorHelper;

    @Mock
    InFlowRepo repo;

    @Mock
    BalanceService balanceService;

    @Test
    void addCash() throws IllegalAccessException {
        Actor actor = new Actor(UUID.fromString("e20e728c-bc30-4990-b7f1-285f7ec1fcc4"), "AJ", "");
        InFlow inflow = new InFlow(UUID.randomUUID(), LocalDateTime.now(), 35000.00, actor.getId().toString(), "15th Salary");
        Mockito.when(repo.save(Mockito.any())).thenReturn(inflow);
        Mockito.when(balanceService.updateBalance(Mockito.any(), Mockito.any())).thenReturn(new ResponseWrapper(
                new BalanceDTO(null, 35000), ResponseWrapperStatus.OK.name(), "Update Balance: Success"));

        ResponseWrapper result = service.addCash(Objects.requireNonNull(InFlowMapper.convert(Either.left(inflow))).get());
        AddCashResponseDto resultDto = (AddCashResponseDto) result.getResponse();

        Assertions.assertEquals(35000.00, resultDto.getUpdatedBalance().getRemainingBalance());

    }

    @Test
    void addCash_Null_Input() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addCash(null);
        });
    }
}
