package com.aldrichjohne.budget.tracker.repository;

import com.aldrichjohne.budget.tracker.model.entity.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BillRepo extends JpaRepository<com.aldrichjohne.budget.tracker.model.entity.Bills, UUID> {
    Optional<Bills> findByName(String name);
}
