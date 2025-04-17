package com.aldrichjohne.budget.tracker.service.impl;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.dto.ActorDTO;
import com.aldrichjohne.budget.tracker.repository.ActorRepo;
import com.aldrichjohne.budget.tracker.service.ActorService;
import com.aldrichjohne.budget.tracker.service.helper.ActorHelper;
import com.aldrichjohne.budget.tracker.util.mapper.ActorMapper;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ActorServiceImpl implements ActorService {
    private final ActorRepo actorRepo;
    private final ActorHelper actorHelper;

    public ActorServiceImpl(ActorRepo actorRepo, ActorHelper actorHelper) {
        this.actorRepo = actorRepo;
        this.actorHelper = actorHelper;
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
                ResponseWrapperStatus.OK.name(),
                "Successfully added an actor"
        );
    }

    @Override
    public ResponseWrapper removeActor(final UUID id) {
        Actor actor = actorHelper.findActorOrThrow(
                id,
                "Delete Actor: Input ID is null",
                "Delete Actor: Actor with ID = " + id + " not found");

        actorRepo.delete(actor);
        log.info("Delete Actor: Success: {}", actor);

        return new ResponseWrapper(actor, ResponseWrapperStatus.OK.name(), "Delete Actor: Success");
    }

    @Override
    public ResponseWrapper updateActor(ActorDTO actorDTO) {
        if (Objects.isNull(actorDTO)) {
            throw new IllegalArgumentException("Update Actor: Input Body is null");
        }

        UUID id = Objects.requireNonNull(actorDTO.getId(), "Update Actor: Input ID is null");

        actorHelper.findActorOrThrow(
                id,
                "Update Actor: Input ID is null",
                "Update Actor: Actor with ID = " + id + " not found");

        Actor actorUpdatedValue = actorRepo.save(Objects.requireNonNull(ActorMapper.convert(Either.right(actorDTO))).getLeft());
        return new ResponseWrapper(
                Objects.requireNonNull(ActorMapper.convert(Either.left(actorUpdatedValue))).get(),
                "Success",
                "Successfully updated an actor"
        );
    }

    @Override
    public ResponseWrapper getActor(UUID id) {
        Actor actor = actorHelper.findActorOrThrow(
                id,
                "Retrieve Actor: Input ID is null",
                "Retrieve Actor: Actor with ID = " + id + " not found");

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
