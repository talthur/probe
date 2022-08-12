package com.talthur.project.core.domain;

import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import lombok.Getter;
import lombok.Setter;


public class Probe {

    @Getter
    private final String probeName;
    @Setter
    @Getter
    private Direction direction;
    @Getter
    @Setter
    private Coordinates coordinates;


    public Probe(Direction orientationEnum, String probeName, Coordinates coordinates) {
        this.direction = orientationEnum;
        this.probeName = probeName;
        this.coordinates = coordinates;
    }

    public void rotateProbeRight() {
        direction = direction.turnRight();
    }

    public void rotateProbeLeft() {
        direction = direction.turnLeft();
    }

    public void moveProbe(Planet planet) {
        Coordinates nextCoordinates = coordinates.nextPosition(direction);
        Square[][] area = planet.getArea();
        area[area.length - coordinates.x()][coordinates.y() - 1].removeProbe();
        try {
            area[area.length - nextCoordinates.x()][nextCoordinates.y() - 1].setProbe(this);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OFFLIMITS);
        }
        this.setCoordinates(nextCoordinates);
    }

}
