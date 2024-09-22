package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.OutFlow;
import com.aldrichjohne.budget.tracker.model.entity.dto.OutFlowDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class OutFlowMapper {
    private OutFlowMapper() {}

    public static Either<OutFlow, OutFlowDTO> convert(Either<OutFlow, OutFlowDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new OutFlowDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getDate(),
                    toConvert.getLeft().getBill(),
                    toConvert.getLeft().getAmount(),
                    toConvert.getLeft().getRemarks()
            ));
        } else {
            return Either.left(new OutFlow(
                    toConvert.get().getId(),
                    toConvert.get().getDate(),
                    toConvert.get().getBill(),
                    toConvert.get().getAmount(),
                    toConvert.get().getRemarks()
            ));
        }
    }
}
