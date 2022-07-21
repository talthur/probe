package com.talthur.project.core.exception;

import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;

@Getter
public class BusinessException extends BasicException {

    public BusinessException(BusinessError error) {
        super(ExceptionData
            .builder()
            .code(error.getCode())
            .detail(error.getDetails())
            .status(error.getStatus())
            .title(error.getTitle())
            .build());
    }

}
