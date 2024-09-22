package com.aldrichjohne.budget.tracker.util.mapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper {
    private Object response;
    private String status;
    private String message;
}
