package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.CurrentMonth;
import com.aldrichjohne.budget.tracker.model.entity.dto.CurrentMonthDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class CurrentMonthMapper {
    private CurrentMonthMapper() {}

    private Either<CurrentMonth, CurrentMonthDTO> convert(Either<CurrentMonth, CurrentMonthDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }
        if (toConvert.isLeft()) {
            return Either.right(new CurrentMonthDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getMonth(),
                    toConvert.getLeft().getBill(),
                    toConvert.getLeft().getBudget())
            );
        } else {
            return Either.left(new CurrentMonth(
                    toConvert.get().getId(),
                    toConvert.get().getMonth(),
                    toConvert.get().getBill(),
                    toConvert.get().getBudget())
            );
        }
    }
}
