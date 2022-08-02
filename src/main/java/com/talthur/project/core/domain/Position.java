package com.talthur.project.core.domain;

public class Position {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
    private int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

}
