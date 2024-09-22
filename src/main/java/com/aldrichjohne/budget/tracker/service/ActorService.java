package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.dto.ActorDTO;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;

import java.util.UUID;

public interface ActorService {
    ResponseWrapper addActor(ActorDTO actorDTO);
    ResponseWrapper removeActor(UUID id);
    ResponseWrapper updateActor(UUID id, ActorDTO actorDTO);
    ResponseWrapper getActor(UUID id);
    ResponseWrapper getActors();
}
