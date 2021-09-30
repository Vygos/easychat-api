package br.com.vygos.easychatapi.handler;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_MSG = "Ocorreu um erro durante a sua requisição";

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        Response message = Response.builder()
                .message(DEFAULT_MSG)
                .status(status.value())
                .message(ex.getMessage()).build();

        return handleExceptionInternal(ex, message, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleApplicationException(ex, headers, status, request);
    }

    @ExceptionHandler(value = NegocioException.class)
    protected ResponseEntity<Object> handleNegocioException(NegocioException ex) {
        Response body = Response.builder()
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(body, ex.getStatus());
    }

    protected ResponseEntity<Object> handleApplicationException(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error> errors = ex.getBindingResult().getAllErrors().stream().map(objectError -> {

            String fieldName = "";
            String constraintName = "";

            if (objectError instanceof FieldError) {
                fieldName = ((FieldError) objectError).getField();
                ConstraintViolationImpl<?> constraint = objectError.unwrap(ConstraintViolationImpl.class);
                constraintName = constraint.getMessageTemplate();
            }

            return Error.builder()
                    .field(fieldName)
                    .error(constraintName)
                    .build();
        }).collect(Collectors.toList());

        Response build = Response.builder()
                .message(DEFAULT_MSG)
                .status(status.value())
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, build, headers, status, request);
    }

    @Data
    @Builder
    public static class Response {
        private Integer status;
        private String message;
        private List<Error> errors;

    }

    @Data
    @Builder
    public static class Error {
        private String field;
        private String error;
    }
}
