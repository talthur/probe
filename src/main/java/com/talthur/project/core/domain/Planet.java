package com.talthur.project.core.domain;

import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ToString
public class Planet {

    @Getter
    private final String id = UUID.randomUUID().toString();
    @Getter
    private final Square[][] area;
    @Getter
    private Map<String, StarShip> starShips = new HashMap<>();


    public Planet(int columns, int rows) {
        validateCoordinates(columns, rows);
        area = new Square[columns][rows];
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[i][j] = new Square(null, false);
            }
        }
    }

    public boolean checkIfIsOccupied(int rows, int columns) {
        return area[columns - 1][rows - 1].isOccupied();
    }

    private void validateCoordinates(int columns, int rows) {
        if (rows < 1 || columns < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }

}
