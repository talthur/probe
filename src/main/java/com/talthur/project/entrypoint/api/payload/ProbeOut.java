package com.talthur.project.entrypoint.api.payload;

import com.talthur.project.core.domain.Coordinates;
import com.talthur.project.core.enums.Direction;

public record ProbeOut(String probeName, Coordinates coordinates, Direction orientation) {

}
