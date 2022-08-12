package com.talthur.project.core.domain;

import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class Planet {

    @Getter
    private final String name;
    @Getter
    @Setter
    private Square[][] area;
    @Getter
    private Map<String, Probe> probes = new HashMap<>();


    public Planet(String name, Coordinates coordinates) {
        validateCoordinates(coordinates);
        this.name = name;
        area = new Square[coordinates.y()][coordinates.x()];
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[i][j] = new Square(null, false);
            }
        }
    }

    public void checkIfIsOccupied(Coordinates coordinates) {
        if (area[area.length - coordinates.y()][coordinates.x() - 1].isOccupied()) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
        }
    }

    private void validateCoordinates(Coordinates coordinates) {
        if (coordinates.x() < 1 || coordinates.y() < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }

}
