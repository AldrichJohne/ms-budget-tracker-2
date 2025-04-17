package com.aldrichjohne.budget.tracker.config;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.Bills;
import com.aldrichjohne.budget.tracker.repository.ActorRepo;
import com.aldrichjohne.budget.tracker.repository.BalanceRepo;
import com.aldrichjohne.budget.tracker.repository.BillRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initializeDatabase(BillRepo bill, ActorRepo actorRepo, BalanceRepo balanceRepo) {
        return args -> {

            Bills bill1 = new Bills(UUID.randomUUID(), "Car Loan", 21413, false);
            Bills bill2 = new Bills(UUID.randomUUID(), "Electric Bill", 4000, false);

            bill.saveAll(List.of(bill1, bill2));

            Actor actor1 = new Actor(UUID.fromString("e20e728c-bc30-4990-b7f1-285f7ec1fcc4"), "AJ", "");
            actorRepo.saveAll(List.of(actor1));

            Balance balance = new Balance(UUID.randomUUID(), 0);
            balanceRepo.saveAll(List.of(balance));

        };
    }
}
