package com.aldrichjohne.budget.tracker.service.helper;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.repository.ActorRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class ActorHelper {

    private final ActorRepo actorRepo;

    public ActorHelper(ActorRepo actorRepo) {
        this.actorRepo = actorRepo;
    }

    public Actor findActorOrThrow(UUID id, String nullMsg, String notFoundMsg) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException(nullMsg);
        }

        return actorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMsg));
    }
}
