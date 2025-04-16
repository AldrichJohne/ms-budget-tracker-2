package com.aldrichjohne.budget.tracker.util.validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

public class ConstraintValidator {

    private ConstraintValidator() {}

    public static <T> void validate(T object, Validator validator, String methodType) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<T> violation : violations) {
                System.out.println("Violation in method " + methodType + ": " + violation.getMessage());
            }

            throw new ConstraintViolationException(methodType + " Constraint Validation failed", violations);
        }
    }
}
