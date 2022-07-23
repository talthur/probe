package com.talthur.project.core.domain;

import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        validatePlacement(columns, rows);
        if (area[columns - 1][rows - 1].isOccupied()) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
        } else {
            area[columns - 1][rows - 1].setStarShip(probe);
            starShips.put(probe.getShipName(), probe);
        }
    }

    public void moveProbe(StarShip probe) {
        List<Integer> movePosition = probe.move();
        List<Integer> actualPosition = probe.getActualPosition();
        List<Integer> futurePosition = List.of(actualPosition.get(0) + movePosition.get(0), actualPosition.get(1) + movePosition.get(1));
        placeStarShip(futurePosition.get(0), futurePosition.get(1), probe);
        probe.setActualPosition(futurePosition);
        area[actualPosition.get(0)-1][actualPosition.get(1)-1].removeProbe();
    }

    public StarShip getStarShip(String name){
        return starShips.get(name);
    }

    private void validatePlacement(int columns, int rows) {
        if ((rows - 1 >= area.length || rows - 1 < 0) || (columns - 1 >= area[rows - 1].length || columns - 1 < 0)) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OFFLIMITS);
        }
    }

    private void validateCoordinates(int columns, int rows) {
        if (rows < 1 || columns < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }


}
