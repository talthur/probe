package com.talthur.project.core.exception.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessError {

    PLACEMENT_NOT_ALLOWED_OFFLIMITS(20001, 422, "Placement not allowed",
        "The requested probe placement is not allowed. Offlimits"),
    PLACEMENT_NOT_ALLOWED_OCUPPIED(20002, 422, "Placement not allowed",
        "The requested probe placement is not allowed. Theres is already a probe in the place"),
    PLANET_NOT_EXIST(20003, 404, "Planet not found",
        "The requested planet does not exist"),
    COORDINATES_INVALID(20004, 400, "Invalid coordinates",
        "The coordinates must be greater than 0 and to the max size of the planet"),
    PROBE_NOT_FOUND(20005, 404, "Probe not found",
        "The requested probe was not found"),
    BAD_REQUEST(20039, 400, "One or more validation errors ocurred", "Please check the inserted payload"),
    INVALID_COMMAND(20040, 400, "Invalid Command", "The command inserted is invalid");

    private final Integer code;
    private final Integer status;
    private final String title;
    private final String details;

}
