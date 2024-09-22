package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.model.entity.dto.ActorDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class ActorMapper {
    private ActorMapper() {}

    public static Either<Actor, ActorDTO> convert(Either<Actor, ActorDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new ActorDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getName(),
                    toConvert.getLeft().getRemarks()
            ));
        } else {
            return Either.left(new Actor(
                    toConvert.get().getId(),
                    toConvert.get().getName(),
                    toConvert.get().getRemarks()
            ));
        }
    }

}
