package com.aldrichjohne.budget.tracker.util.mapper;

import com.aldrichjohne.budget.tracker.model.entity.Bills;
import com.aldrichjohne.budget.tracker.model.entity.dto.BillsDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class BillsMapper {

    private BillsMapper() {}

    public static Either<Bills, BillsDTO> convert(Either<Bills, BillsDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }

        if(toConvert.isLeft()) {
            return Either.right(new BillsDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getName(),
                    toConvert.getLeft().getBudget()
            ));
        } else {
            return Either.left(new Bills(
                    toConvert.get().getId(),
                    toConvert.get().getName(),
                    toConvert.get().getBudget()
            ));
        }
    }
}
