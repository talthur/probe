package com.talthur.project.entrypoint.api.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

    private static final long serialVersionUID = -8687325531493549920L;
    private String title;
    private String detail;
    private Integer code;
    private Integer status;

}