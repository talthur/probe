package com.talthur.project.core.domain;

import com.talthur.project.core.enums.OrientationEnum;

import java.util.List;

public class Probe extends StarShip {

    public Probe(OrientationEnum orientationEnum, String probeName, int row, int column) {
        super(orientationEnum, probeName, row, column);
    }

    @Override
    public List<Integer> move() {
        return switch (orientation) {
            case NORTH -> List.of(-1, 0);
            case EAST -> List.of(0, 1);
            case SOUTH -> List.of(1, 0);
            case WEST -> List.of(0, -1);
        };
    }

    @Override
    public void rotate(char command) {
        if (command == 'L') {
            this.orientation = orientation.getPrevious();
        }

        if (command == 'R') {
            this.orientation = orientation.getNext();
        }
    }

}
