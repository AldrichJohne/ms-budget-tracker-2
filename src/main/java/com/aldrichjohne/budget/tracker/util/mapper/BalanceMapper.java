package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.Balance;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class BalanceMapper {
    private BalanceMapper() {
    }

    public static Either<Balance, BalanceDTO> convert(Either<Balance, BalanceDTO> toConvert) {
        if(Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new BalanceDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getRemainingBalance()
            ));
        } else {
            return Either.left(new Balance(
                    toConvert.get().getId(),
                    toConvert.get().getRemainingBalance()
            ));
        }
    }
}
