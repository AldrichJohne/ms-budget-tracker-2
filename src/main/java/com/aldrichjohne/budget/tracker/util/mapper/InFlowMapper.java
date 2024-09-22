package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.InFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.InflowDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class InFlowMapper {
    private InFlowMapper() {}

    public static Either<InFlow, InflowDTO> convert(Either<InFlow, InflowDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new InflowDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getDate(),
                    toConvert.getLeft().getAmount(),
                    toConvert.getLeft().getPerson(),
                    toConvert.getLeft().getRemarks()
            ));
        } else {
            return Either.left(new InFlow(
                    toConvert.get().getId(),
                    toConvert.get().getDate(),
                    toConvert.get().getAmount(),
                    toConvert.get().getPerson(),
                    toConvert.get().getRemarks()
            ));
        }
    }
}
