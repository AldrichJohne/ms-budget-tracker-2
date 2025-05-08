package com.aldrichjohne.budget.tracker.exception.customexceptions;

public class MandatoryInputMissingException extends Exception {
    public MandatoryInputMissingException() {
        super();
    }

    public MandatoryInputMissingException(String missingField) {
        super("Mandatory input missing:" + missingField);
    }

    public MandatoryInputMissingException(String missingField, Throwable cause) {
        super("Mandatory input missing:" + missingField, cause);
    }

    public MandatoryInputMissingException(Throwable cause) {
        super(cause);
    }
}
