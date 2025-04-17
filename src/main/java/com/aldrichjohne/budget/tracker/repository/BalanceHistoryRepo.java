package com.aldrichjohne.budget.tracker.repository;

import com.aldrichjohne.budget.tracker.model.entity.BalanceHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceHistoryRepo extends JpaRepository<BalanceHistoryEntity, UUID> {
}
