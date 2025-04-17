package com.aldrichjohne.budget.tracker.util.mapper.model;

import com.aldrichjohne.budget.tracker.model.entity.BalanceHistoryEntity;
import com.aldrichjohne.budget.tracker.model.entity.dto.BalanceHistoryDTO;
import io.vavr.control.Either;

import java.util.Objects;

public class BalanceHistoryMapper {
    private BalanceHistoryMapper() {}

    public static Either<BalanceHistoryEntity, BalanceHistoryDTO> convert(Either<BalanceHistoryEntity, BalanceHistoryDTO> toConvert) {
        if (Objects.isNull(toConvert)) {
            return null;
        }

        if (toConvert.isLeft()) {
            return Either.right(new BalanceHistoryDTO(
                    toConvert.getLeft().getId(),
                    toConvert.getLeft().getFlowId(),
                    toConvert.getLeft().getChangeAmount(),
                    toConvert.getLeft().getBalanceAfter(),
                    toConvert.getLeft().getTimestamp(),
                    toConvert.getLeft().getRemarks()
            ));
        } else {
            return Either.left(new BalanceHistoryEntity(
                    toConvert.get().getId(),
                    toConvert.get().getFlowId(),
                    toConvert.get().getChangeAmount(),
                    toConvert.get().getBalanceAfter(),
                    toConvert.get().getTimestamp(),
                    toConvert.get().getRemarks()
            ));
        }
    }
}
