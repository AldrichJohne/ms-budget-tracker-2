package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.dto.BalanceRequestDTO;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.service.impl.BalanceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith({SpringExtension.class})
class BalanceServiceImplTest {

    @Mock
    BalanceRepo balanceRepo;

    @InjectMocks
    BalanceServiceImpl service;

    @Test
    void success_adding_balance() {
        UUID id = UUID.randomUUID();
        Balance balance = new Balance(id, 10000);
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "add", 25000);

        Mockito.when(balanceRepo.findById(Mockito.any())).thenReturn(Optional.of(balance));
        Mockito.when(balanceRepo.save(Mockito.any())).thenReturn(new Balance(id,
                balance.getRemainingBalance() + balanceRequest.getAmount()));

        var result = service.updateBalance(balanceRequest);

        Assertions.assertEquals(balance.getRemainingBalance() + balanceRequest.getAmount(), result.getRemainingBalance());
    }

    @Test
    void success_subtracting_balance() {
        UUID id = UUID.randomUUID();
        Balance balance = new Balance(id, 25000);
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "subtract", 10000);

        Mockito.when(balanceRepo.findById(Mockito.any())).thenReturn(Optional.of(balance));
        Mockito.when(balanceRepo.save(Mockito.any())).thenReturn(new Balance(id,
                balance.getRemainingBalance() - balanceRequest.getAmount()));

        var result = service.updateBalance(balanceRequest);

        Assertions.assertEquals(balance.getRemainingBalance() - balanceRequest.getAmount(), result.getRemainingBalance());
    }

    @Test
    void got_an_empty_balance_from_database() {
        UUID id = UUID.randomUUID();
        Optional<Balance> emptyBalanceEntity = Optional.empty();
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "subtract", 10000);

        Mockito.when(balanceRepo.findById(Mockito.any())).thenReturn(emptyBalanceEntity);

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.updateBalance(balanceRequest);
        });

        Assertions.assertEquals("Couldn't find current balance record from the database", exception.getMessage());

    }

    @Test
    void failed_remaining_is_less_than_difference_while_subtracting() {
        UUID id = UUID.randomUUID();
        Balance balance = new Balance(id, 5000);
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "subtract", 10000);

        Mockito.when(balanceRepo.findById(Mockito.any())).thenReturn(Optional.of(balance));



        Exception exception = Assertions.assertThrows(IllegalArgumentException.class , () -> {
            service.updateBalance(balanceRequest);
        });

        Assertions.assertEquals("Insufficient balance", exception.getMessage());

    }

    @Test
    void failed_updating_balance_EntityNotFoundException() {
        UUID id = UUID.randomUUID();
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "add", 10000);
        Mockito.doThrow(new EntityNotFoundException()).when(balanceRepo).findById(Mockito.any());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateBalance(balanceRequest);
        });
    }

    @Test
    void invalid_operation_type() {
        UUID id = UUID.randomUUID();
        Balance balance = new Balance(id, 5000);
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "multiply", 10000);

        Mockito.when(balanceRepo.findById(Mockito.any())).thenReturn(Optional.of(balance));

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateBalance(balanceRequest);
        });

        Assertions.assertEquals("Invalid operation type", exception.getMessage());
    }

    @Test
    void failed_updating_balance_GenericException() {
        UUID id = UUID.randomUUID();
        BalanceRequestDTO balanceRequest = new BalanceRequestDTO(id, "add", 10000);
        Mockito.doThrow(new NoSuchElementException("")).when(balanceRepo).findById(Mockito.any());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.updateBalance(balanceRequest);
        });

    }

    @Test
    void failed_update_balance_invalid_input() throws IllegalArgumentException {


        Exception exception = Assertions.assertThrows(IllegalArgumentException.class , () -> {
            service.updateBalance(null);
        });

        Assertions.assertEquals("Invalid input null", exception.getMessage());
    }

    @Test
    void success_fetching_all_balance() {
        UUID id = UUID.randomUUID();
        Balance balance = new Balance(id, 25000);

        Mockito.when(balanceRepo.findAll()).thenReturn(List.of(balance));

        var result = service.getBalance();

        Assertions.assertEquals(balance.getRemainingBalance(), result.getRemainingBalance());
    }

    @Test
    void failed_fetching_all_actors() {
        Mockito.doThrow(new EntityNotFoundException("")).when(balanceRepo).findAll();

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            throw new EntityNotFoundException();
        });
    }
}