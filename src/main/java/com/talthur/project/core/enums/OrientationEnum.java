package com.talthur.project.core.enums;


public enum OrientationEnum {

    NORTH{
        @Override
        public int getNextY() {
            return 0;
        }

        @Override
        public int getNextX() {
            return -1;
        }
    },
    EAST {
        @Override
        public int getNextY() {
            return 1;
        }

        @Override
        public int getNextX() {
            return 0;
        }
    },
    SOUTH {
        @Override
        public int getNextY() {
            return 0;
        }

        @Override
        public int getNextX() {
            return 1;
        }
    },
    WEST {
        @Override
        public int getNextY() {
            return -1;
        }

        @Override
        public int getNextX() {
            return 0;
        }
    };

    public OrientationEnum getNext() {
        return values()[(ordinal() + 1) % values().length];
    }

    public OrientationEnum getPrevious() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    public abstract int getNextY();

    public abstract int getNextX();
}
