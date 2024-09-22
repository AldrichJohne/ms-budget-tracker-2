package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.BudgetAndActual;
import com.aldrichjohne.budget.tracker.model.entity.dto.BudgetAndActualDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class BudgetAndActualMapper {
    private BudgetAndActualMapper() {

    }

    public static Either<BudgetAndActual, BudgetAndActualDTO> convert(
            Either<BudgetAndActual, BudgetAndActualDTO> toConvert) {
        if(Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new BudgetAndActualDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getMonth(),
                    toConvert.getLeft().getBill(),
                    toConvert.getLeft().getBudget(),
                    toConvert.getLeft().getActualOutFlow(),
                    toConvert.getLeft().getDifference()
            ));
        } else {
            return Either.left(new BudgetAndActual(
                    toConvert.get().getId(),
                    toConvert.get().getMonth(),
                    toConvert.get().getBill(),
                    toConvert.get().getBudget(),
                    toConvert.get().getActualOutFlow(),
                    toConvert.get().getDifference()
            ));
        }
    }
}
