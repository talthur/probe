package com.talthur.project.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ExceptionData {

    private final String title;
    private final String detail;
    private final Integer code;
    private final Integer status;

    public ExceptionData(String title, Integer code) {
        this.title = title;
        this.code = code;
        this.detail = null;
        this.status = null;
    }

}
