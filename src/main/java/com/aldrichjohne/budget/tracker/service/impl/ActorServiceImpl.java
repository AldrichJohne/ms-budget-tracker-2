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
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            if (Objects.isNull(actorDTO)) {
                return new ResponseWrapper("", "Error", "Add actor input is null");
            }
            Actor actor = actorRepo.save(ActorMapper.convert(Either.right(actorDTO)).getLeft());
            log.info("Successfully added an actor; {}", actor);
            return new ResponseWrapper(
                    ActorMapper.convert(Either.left(actor)).get(),
                    "Success",
                    "Successfully added an actor"
            );
        } catch (DataIntegrityViolationException exception) {
            log.error("Error occurred on saving new actor due to Data Integrity Violation; {}", exception.getMessage());
            return new ResponseWrapper(
                    exception.getMessage(), "Error", "Error occurred on saving new actor due to Data Integrity Violation"
            );
        } catch (Exception exception) {
            log.error("Error occurred on saving new actor; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred on saving new actor");
        }
    }

    @Override
    public ResponseWrapper removeActor(final UUID id) {
        if (Objects.isNull(id)) {
            return new ResponseWrapper("", "Error", "Delete actor input is null");
        }
        try {
            Actor actor = actorRepo.findById(id).get();
            actorRepo.delete(actor);
            log.info("Successfully deleted an actor; {}", actor);
            return new ResponseWrapper(actor, "Success", "Successfully deleted an actor");
        } catch (final EntityNotFoundException exception) {
            log.error("Error occurred while removing actor, non existing record for actor with id {}", id);
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while removing actor");
        } catch (final Exception exception) {
            log.error("Error occurred while deleting an actor; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while removing an actor");
        }
    }

    @Override
    public ResponseWrapper updateActor(UUID id, ActorDTO actorDTO) {
        if (Objects.isNull(actorDTO) || Objects.isNull(id)) {
            return new ResponseWrapper("", "Error", "Update actor input is null");
        }
        try {
            actorRepo.findById(id).get();
            Actor actorUpdatedValue = actorRepo.save(ActorMapper.convert(Either.right(actorDTO)).getLeft());
            return new ResponseWrapper(
                    actorUpdatedValue,
                    "Success",
                    "Successfully updated an actor"
            );
        } catch (final EntityNotFoundException exception) {
            log.error("Error occurred while updating an actor, non existing record for actor with id {}", id);
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while updating actor");
        } catch (final Exception exception) {
            log.error("Error occurred while updating an actor; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while updating an actor");
        }
    }

    @Override
    public ResponseWrapper getActor(UUID id) {
        if (Objects.isNull(id)) {
            return new ResponseWrapper("", "Error", "Fetch actor input is null");
        }
        try {
            Actor actor = actorRepo.findById(id).get();
            return new ResponseWrapper(
                    actor,
                    "Success",
                    "Successfully fetched an actor"
            );
        } catch (final EntityNotFoundException exception) {
            log.error("Error occurred while fetching an actor, non existing record for actor with id {}", id);
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while fetching actor");
        } catch (final Exception exception) {
            log.error("Error occurred while fetching an actor; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while fetching an actor");
        }
    }

    @Override
    public ResponseWrapper getActors() {
        try {
            List<Actor> actorList = actorRepo.findAll();
            return new ResponseWrapper(
                    actorList,
                    "Success",
                    "Successfully fetched all actors"
            );
        } catch (final Exception exception) {
            log.error("Error occurred while fetching all actors; {}", exception.getMessage());
            return new ResponseWrapper(exception.getMessage(), "Error", "Error occurred while fetching all actors");
        }
    }
}
