package com.talthur.project.entrypoint.api.payload;

import com.talthur.project.core.enums.OrientationEnum;

import java.util.List;

public record ProbeOut(String probeName, List<Integer> landingPosition, OrientationEnum orientation) {

}
