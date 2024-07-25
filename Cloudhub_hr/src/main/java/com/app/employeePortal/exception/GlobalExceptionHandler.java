package com.app.employeePortal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
//        HttpStatus status = ex.getStatus();
//        String errorMessage = ex.getReason();
//        return new ResponseEntity<>(errorMessage, status);
        List<String> details = new ArrayList<>();
        details.add(getExceptionMessage(ex));
//        logError(ex, request);

        HttpStatus status = getResponseStatus(ex);
        ErrorResponse error = new ErrorResponse(status.toString(), details);
        return new ResponseEntity<>(error, status);

    }
    private String getExceptionMessage(Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = exception.toString();
        }
        return message;
    }
    private HttpStatus getResponseStatus(final ResponseStatusException exception) {
        return Optional.of(exception.getStatus()).orElse(HttpStatus.BAD_REQUEST);
    }

//    private void logError(final Exception exception, final HttpServletRequest request) {
//        log.error(exception.toString(), "[URL : " + getRequestPathAndQuery(request) + "]", exception);
//    }
}
