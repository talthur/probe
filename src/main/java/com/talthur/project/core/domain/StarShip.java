package com.talthur.project.core.domain;

import com.talthur.project.core.enums.OrientationEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public abstract class StarShip {

    @Setter
    @Getter
    protected OrientationEnum orientation;
    @Getter
    protected final String shipName;
    @Getter
    @Setter
    protected List<Integer> actualPosition = new ArrayList<>(2);

    protected StarShip(OrientationEnum orientationEnum, String shipName, int column, int row) {
        actualPosition.add(column);
        actualPosition.add(row);
        this.shipName = shipName;
        this.orientation = orientationEnum;
    }

    public abstract List<Integer> move();

    public abstract void rotate(char command);

}
