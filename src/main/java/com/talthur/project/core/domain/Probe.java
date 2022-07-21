package com.talthur.project.core.domain;

import com.talthur.project.core.enums.OrientationEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Probe {

    @Setter
    private OrientationEnum orientation;
    @Getter
    private String probeName;
    @Getter
    @Setter
    private List<Integer> actualPosition = new ArrayList<>(2);

    public Probe(OrientationEnum orientationEnum, String probeName, int row, int column) {
        actualPosition.add(row);
        actualPosition.add(column);
        this.probeName = probeName;
        this.orientation = orientationEnum;
    }

    public List<Integer> move() {
        return List.of(-1, 0);
        //        switch (orientation) {
        //            case NORTH -> planet
        //        }
    }

    public void rotate(char command) {
        if (command == 'L') {
            this.orientation = orientation.getPrevious();

        }

        if (command == 'R') {
            this.orientation = orientation.getNext();

        }
    }

}
