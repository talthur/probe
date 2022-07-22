package com.talthur.project.entrypoint.api.handler;

import com.google.gson.Gson;
import com.talthur.project.core.exception.BasicException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static ApiError buildException() {
        return new ApiError(BusinessError.BAD_REQUEST.getTitle(), BusinessError.BAD_REQUEST.getDetails(),
            BusinessError.BAD_REQUEST.getCode(),
            BusinessError.BAD_REQUEST.getStatus());
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<ApiError> integrationHandler(BasicException ex) {
        ApiError exceptionData = new ApiError(ex.getTitle(), ex.getDetail(), ex.getCode(), ex.getStatus());
        log.error("BasicException -> apiError={}", new Gson().toJson(exceptionData), ex);
        return ResponseEntity.status(ex.getStatus()).body(exceptionData);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
        WebRequest request) {
        ApiError exceptionData = buildException();
        log.error("ServletRequestBindingException -> exceptionData={}", new Gson().toJson(exceptionData), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionData);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError exceptionData = buildException();
        log.error("MethodArgumentNotValidException -> exceptionData={}", new Gson().toJson(exceptionData), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionData);
    }

}
