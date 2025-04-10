package com.aldrichjohne.budget.tracker.exception;

import com.aldrichjohne.budget.tracker.enums.ResponseWrapperStatus;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal Argument Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Entity Not Found Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseWrapper> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data Integrity Violation Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), ex.getMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ResponseWrapper> handleDataAccessException(DataAccessException ex) {
        log.error("Database Access Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "Database error occurred"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method Argument Type Mismatch: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "Invalid parameter type"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseWrapper> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        log.error("Missing Request Parameter: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "Missing request parameter: " + ex.getParameterName()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError() != null ?
                ex.getBindingResult().getFieldError().getDefaultMessage() : "Validation failed";
        log.error("Validation Exception: {}", errorMessage, ex);
        return ResponseEntity.badRequest()
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), errorMessage));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseWrapper> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("HTTP Method Not Supported: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "HTTP method not allowed"));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseWrapper> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        log.error("HTTP Media Type Not Supported: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "Unsupported media type"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "An unexpected error occurred: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleGenericException(Exception ex) {
        log.error("Generic Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseWrapper(null, ResponseWrapperStatus.ERROR.toString(), "An unexpected error occurred"));
    }
}
