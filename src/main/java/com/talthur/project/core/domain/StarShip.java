package com.talthur.project.core.domain;

import com.talthur.project.core.enums.OrientationEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class StarShip {

    @Setter
    @Getter
    protected OrientationEnum orientation;
    @Getter
    protected final String shipName;
    @Getter
    @Setter
    protected List<Integer> actualPosition = new ArrayList<>(2);

    protected StarShip(OrientationEnum orientationEnum, String shipName, int row, int column) {
        actualPosition.add(row);
        actualPosition.add(column);
        this.shipName = shipName;
        this.orientation = orientationEnum;
    }

    public abstract List<Integer> move();

    public abstract void rotate(char command);

}
