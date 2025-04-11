package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.Bills;
import com.aldrichjohne.budget.tracker.repository.BillRepo;
import com.aldrichjohne.budget.tracker.service.impl.BillServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.BillsMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith({SpringExtension.class})
public class BillServiceImplTest {

    @Mock
    BillRepo repo;

    @InjectMocks
    BillServiceImpl service;

    @Test
    void success_adding_bill() {
        Bills bill = new Bills(UUID.randomUUID(), "Electric Bill", 4000, false);
        Mockito.when(repo.save(bill)).thenReturn(bill);

        var result = service.addBill(BillsMapper.convert(Either.left(bill)).get());

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Add Bill: Success", result.getMessage());
    }

    @Test
    void unsuccessful_adding_bill_duplicate_record() {
        Bills bill = new Bills(UUID.randomUUID(), "Electric Bill", 4000, false);
        Mockito.when(repo.findByName(bill.getName())).thenReturn(Optional.of(bill));

        var result = service.addBill(BillsMapper.convert(Either.left(bill)).get());

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Add Bill: Unsuccessful: Bill already exists", result.getMessage());
    }


    @Test
    void failed_adding_bill_null_input() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.addBill(null);
        });

        Assertions.assertEquals("Add Bill: Input is null", exception.getMessage());
    }

    @Test
    void success_deleting_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-bill", 4000, false);
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.of(bill));
        Mockito.when(repo.save(bill)).thenReturn(new Bills(uuid, "E-bill", 4000, true));

        var result = service.removeBill(uuid);
        Assertions.assertEquals("OK", result.getStatus());

    }

    @Test
    void success_soft_deleting_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-bill", 4000, false);
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.of(bill));
        Mockito.when(repo.save(bill)).thenReturn(new Bills(uuid, "E-bill", 4000, true));

        var result = service.softRemoveBill(uuid);
        Assertions.assertEquals("OK", result.getStatus());

    }

    @Test
    void failed_deleting_bill_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.removeBill(uuid);
        });
    }

    @Test
    void failed_soft_deleting_bill_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.softRemoveBill(uuid);
        });
    }

    @Test
    void failed_deleting_bill_null_input() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.removeBill(null);
        });

        Assertions.assertEquals("Remove Bill: Input ID is null", exception.getMessage());
    }

    @Test
    void failed_soft_deleting_bill_null_input() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.softRemoveBill(null);
        });

        Assertions.assertEquals("Remove Bill: Input ID is null", exception.getMessage());
    }

    @Test
    void success_updating_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-bill", 4000, false);
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.of(bill));
        Mockito.when(repo.save(bill)).thenReturn(bill);

        var result = service.updateBill(BillsMapper.convert(Either.left(bill)).get());
        ResponseWrapper expected = new ResponseWrapper(BillsMapper.convert(Either.left(bill)).get(), ResponseWrapperStatus.OK.toString(), "Update Bill: Success");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void failed_updating_bill_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-Bill", 4000, false);
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.empty());

        Mockito.doThrow(new EntityNotFoundException("")).when(repo).findById(uuid);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.updateBill(BillsMapper.convert(Either.left(bill)).get());
        });
    }

    @Test
    void failed_update_bill_null_input() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.updateBill(null);
        });

        Assertions.assertEquals("Update Bill: Input Body is null", exception.getMessage());
    }

    @Test
    void success_fetching_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-Bill", 4000, false);
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.of(bill));
        ResponseWrapper expected = new ResponseWrapper(BillsMapper.convert(Either.left(bill)).get(), ResponseWrapperStatus.OK.toString(), "Retrieve Bill: Success");

        var result = service.getBill(uuid);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void failed_fetching_bill_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(repo.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.getBill(uuid);
        });
    }

    @Test
    void failed_fetching_bill_null_input() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getBill(null);
        });

        Assertions.assertEquals("Retrieve Bill: Input ID is null", exception.getMessage());
    }

    @Test
    public void success_fetching_all_undeleted_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-Bill", 4000, false);
        Bills bill2 = new Bills(uuid, "Car", 22000, false);
        Bills bill3 = new Bills(uuid, "Car Insurance", 4100, true);
        Mockito.when(repo.findAll()).thenReturn(List.of(bill, bill2, bill3));

        var result = service.getBillWithoutDeleted();

        Assertions.assertNotNull(result);
    }

    @Test
    public void success_fetching_all_bill() {
        UUID uuid = UUID.randomUUID();
        Bills bill = new Bills(uuid, "E-Bill", 4000, false);
        Bills bill2 = new Bills(uuid, "Car", 22000, false);
        Bills bill3 = new Bills(uuid, "Car Insurance", 4100, true);
        Mockito.when(repo.findAll()).thenReturn(List.of(bill, bill2, bill3));

        var result = service.getAllBillsWithDeleted();

        Assertions.assertNotNull(result);
    }

    @Test
    public void failed_fetching_all_actors() {
        Mockito.doThrow(new EntityNotFoundException("")).when(repo).findAll();
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.getBillWithoutDeleted();
        });

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.getAllBillsWithDeleted();
        });
    }
}
