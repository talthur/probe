package com.talthur.project.core.enums;


import lombok.Getter;

public enum Direction {

    NORTH(new PositionCalculator(0, 1)) {
        @Override
        public Direction turnRight() {
            return EAST;
        }

        @Override
        public Direction turnLeft() {
            return WEST;
        }
    },
    EAST(new PositionCalculator(1, 0)) {
        @Override
        public Direction turnRight() {
            return SOUTH;
        }

        @Override
        public Direction turnLeft() {
            return NORTH;
        }
    },
    SOUTH(new PositionCalculator(0, -1)) {
        @Override
        public Direction turnRight() {
            return WEST;
        }

        @Override
        public Direction turnLeft() {
            return EAST;
        }
    },
    WEST(new PositionCalculator(-1, 0)) {
        @Override
        public Direction turnRight() {
            return NORTH;
        }

        @Override
        public Direction turnLeft() {
            return SOUTH;
        }
    };

    private final PositionCalculator positionCalculator;

    Direction(PositionCalculator positionCalculator) {
        this.positionCalculator = positionCalculator;
    }

    public PositionCalculator getPositionCalculator() {
        return this.positionCalculator;
    }

    public abstract Direction turnRight();

    public abstract Direction turnLeft();

    public static class PositionCalculator {

        @Getter
        private int nextX;
        @Getter
        private int nextY;

        public PositionCalculator(int nextX, int nextY) {
            this.nextX = nextX;
            this.nextY = nextY;
        }

    }
}
