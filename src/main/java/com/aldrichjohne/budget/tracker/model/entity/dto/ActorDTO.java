package com.aldrichjohne.budget.tracker.model.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ActorDTO {
    private UUID id;
    private String name;
    private String remarks;
}