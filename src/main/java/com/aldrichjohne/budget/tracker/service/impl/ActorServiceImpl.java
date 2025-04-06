package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.dto.ActorDTO;
import com.aldrichjohne.budget.tracker.repository.ActorRepo;
import com.aldrichjohne.budget.tracker.service.ActorService;
import com.aldrichjohne.budget.tracker.util.mapper.ActorMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ActorServiceImpl implements ActorService {
    private final ActorRepo actorRepo;

    public ActorServiceImpl(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    @Override
    public ResponseWrapper addActor(final ActorDTO actorDTO) {
        if (Objects.isNull(actorDTO)) {
            throw new IllegalArgumentException("Add Actor: Input Body is null");
        }
        Actor actor = actorRepo.save(Objects.requireNonNull(ActorMapper.convert(Either.right(actorDTO))).getLeft());
        log.info("Successfully added an actor; {}", actor);
        return new ResponseWrapper(
                Objects.requireNonNull(ActorMapper.convert(Either.left(actor))).get(),
                "Success",
                "Successfully added an actor"
        );
    }

    @Override
    public ResponseWrapper removeActor(final UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Delete Actor: Input ID is null");
        }

        Actor actor = actorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Delete Actor: Actor with ID = " + id + " not found"));

        actorRepo.delete(actor);
        log.info("Delete Actor: Success: {}", actor);

        return new ResponseWrapper(actor, "Success", "Delete Actor: Success");
    }

    @Override
    public ResponseWrapper updateActor(ActorDTO actorDTO) {
        if (Objects.isNull(actorDTO)) {
            throw new IllegalArgumentException("Update Actor: Input Body is null");
        }

        actorRepo.findById(actorDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Update Actor: Actor with ID = " + actorDTO.getId() + " not found"));
        Actor actorUpdatedValue = actorRepo.save(Objects.requireNonNull(ActorMapper.convert(Either.right(actorDTO))).getLeft());
        return new ResponseWrapper(
                Objects.requireNonNull(ActorMapper.convert(Either.left(actorUpdatedValue))).get(),
                "Success",
                "Successfully updated an actor"
        );
    }

    @Override
    public ResponseWrapper getActor(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Retrieve Actor: Input ID is null");
        }

        Actor actor = actorRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Get Actor: Actor with ID = " + id + " not found"));

        return new ResponseWrapper(
                actor,
                "Success",
                "Successfully retrieved an actor"
        );
    }

    @Override
    public ResponseWrapper getActors() {
        List<Actor> actorList = actorRepo.findAll();
        return new ResponseWrapper(
                actorList,
                "Success",
                "Successfully fetched all actors"
        );
    }
}
