package com.talthur.project.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BasicException extends RuntimeException {

    private final String title;
    private final Integer code;
    private final Integer status;
    private final String detail;

    protected BasicException(ExceptionData exceptionData) {
        super(exceptionData.getTitle());
        this.title = exceptionData.getTitle();
        this.detail = exceptionData.getDetail();
        this.code = exceptionData.getCode();
        this.status = exceptionData.getStatus();
    }

}
