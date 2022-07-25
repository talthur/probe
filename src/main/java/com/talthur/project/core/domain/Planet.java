package com.talthur.project.core.domain;

import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ToString
public class Planet {

    @Getter
    private final String id = UUID.randomUUID().toString();
    @Getter
    private Map<String, StarShip> starShips = new HashMap<>();
    @Getter
    private final Square[][] area;


    public Planet(int columns, int rows) {
        validateCoordinates(columns, rows);
        area = new Square[columns][rows];
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[i][j] = new Square(null, false);
            }
        }
    }

    public void placeStarShip(int rows, int columns, StarShip probe) {
        try {
            if (checkIfIsOccupied(rows, columns)) {
                throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
            } else {
                area[columns - 1][rows - 1].setStarShip(probe);
                starShips.put(probe.getShipName(), probe);
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }


    public void moveProbe(StarShip probe) {
        List<Integer> movePosition = probe.move();
        List<Integer> actualPosition = probe.getActualPosition();
        List<Integer> futurePosition = List.of(actualPosition.get(0) + movePosition.get(0), actualPosition.get(1) + movePosition.get(1));
        placeStarShip(futurePosition.get(0), futurePosition.get(1), probe);
        probe.setActualPosition(futurePosition);
        area[actualPosition.get(1) - 1][actualPosition.get(0) - 1].removeProbe();
    }

    public StarShip getStarShip(String name) {
        return starShips.get(name);
    }

    private boolean checkIfIsOccupied(int rows, int columns) {
        return area[columns - 1][rows - 1].isOccupied();
    }

    private void validateCoordinates(int columns, int rows) {
        if (rows < 1 || columns < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }

}
