package com.aldrichjohne.budget.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BalanceRepo extends JpaRepository<com.aldrichjohne.budget.tracker.model.entity.Balance, UUID> {
}
