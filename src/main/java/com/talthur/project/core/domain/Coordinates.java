package com.talthur.project.core.domain;

import com.talthur.project.core.enums.Direction;
import com.talthur.project.core.exception.BusinessException;
import com.talthur.project.core.exception.errors.BusinessError;

public record Coordinates(int x, int y) {

    public Coordinates {
        if (x < 1 || y < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
    }

    public Coordinates nextPosition(Direction direction) {
        int nextX = x + direction.getPositionCalculator().getNextX();
        int nextY = y + direction.getPositionCalculator().getNextY();
        if (nextX < 1 || nextY + direction.getPositionCalculator().getNextY() < 1) {
            throw new BusinessException(BusinessError.COORDINATES_INVALID);
        }
        return new Coordinates(nextX, nextY);
    }

}
