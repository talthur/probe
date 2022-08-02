package com.talthur.project.core.domain;

import com.talthur.project.core.enums.OrientationEnum;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;
import com.talthur.project.dataprovider.PlanetInMemory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


public class Probe {

    @Getter
    private final String shipName;
    @Setter
    @Getter
    private OrientationEnum orientation;
    @Getter
    private Position actualPosition;
    private final String planetId;

    @Autowired
    private PlanetInMemory planetInMemory;


    public Probe(OrientationEnum orientationEnum, String probeName, Position position, String planetId) {

        this.orientation = orientationEnum;
        this.shipName = probeName;
        this.actualPosition = position;
        this.planetId = planetId;
    }

    public void rotate(char command) {
        if (command == 'L') {
            this.orientation = orientation.getPrevious();
        }

        if (command == 'R') {
            this.orientation = orientation.getNext();
        }
    }

    private void placeStarShip(int rows, int columns) {
        Planet planet = planetInMemory.getPlanet(planetId);
        try {
            if (planet.checkIfIsOccupied(rows, columns)) {
                throw new BusinessException(BusinessError.PLACEMENT_NOT_ALLOWED_OCUPPIED);
            } else {
                area[columns - 1][rows - 1].setStarShip(probe);
                starShips.put(probe.getShipName(), probe);
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }

    public void moveStarShip() {
        placeStarShip(orientation.getNextX(), orientation.getNextY());
        actualPosition = new Position(actualPosition.getX() + orientation.getNextX(), actualPosition.getY() + orientation.getNextY());
        area[actualPosition.get(1) - 1][actualPosition.get(0) - 1].removeProbe();
    }

}
