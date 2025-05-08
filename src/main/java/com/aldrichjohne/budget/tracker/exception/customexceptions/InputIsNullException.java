package com.aldrichjohne.budget.tracker.exception.customexceptions;

public class InputIsNullException extends Exception {
    public InputIsNullException() {
        super();
    }

    public InputIsNullException(String message) {
        super(message);
    }

    public InputIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputIsNullException(Throwable cause) {
        super(cause);
    }
}
