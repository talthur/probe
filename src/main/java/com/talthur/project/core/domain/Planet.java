package com.talthur.project.core.domain;

import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planet {

    private final Square[][] area;
    @Getter
    private Map<String, Probe> probes = new HashMap<>();

    public Planet(int rows, int columns) {
        area = new Square[rows][columns];
        for (int i = 0; i < area.length; i++) {
            for (int j = 0; j < area[i].length; j++) {
                area[i][j] = new Square(null, false);
            }
        }
    }

    public void placeProbe(int rows, int columns, Probe probe) {
        checkIfPlacingIsValid(rows, columns);
        if (area[rows][columns].isOccupied()) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
        } else {
            area[rows - 1][columns - 1].setProbe(probe);
            probes.put(probe.getProbeName(), probe);
        }
    }

    public void moveProbe(Probe probe) {
        List<Integer> movePosition = probe.move();
        List<Integer> actualPosition = probe.getActualPosition();
        List<Integer> futurePosition = List.of(actualPosition.get(0) + movePosition.get(0), actualPosition.get(1) + movePosition.get(1));
        placeProbe(futurePosition.get(0), futurePosition.get(1), probe);
        probe.setActualPosition(futurePosition);
        area[actualPosition.get(0)][actualPosition.get(1)].removeProbe();
    }

    private void checkIfPlacingIsValid(int rows, int columns) {
        if ((rows - 1 >= area.length || rows - 1 < 0) || (columns - 1 >= area[rows - 1].length || columns - 1 < 0)) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OFFLIMITS);
        }
    }

}
