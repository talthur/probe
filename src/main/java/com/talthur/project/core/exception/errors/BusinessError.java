package com.talthur.project.core.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {

    PLACEMENT_NOT_ALLOWED_OFFLIMITS(20001, 422, "Placement not allowed",
        "The requested probe placement is not allowed. Offlimits"),
    PLACEMENT_NOT_ALLOWED_OCUPPIED(20002, 422, "Placement not allowed",
        "The requested probe placement is not allowed. Theres is already a probe in the place");
    private final Integer code;
    private final Integer status;
    private final String title;
    private final String details;

}
