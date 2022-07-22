package com.talthur.project.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Square {

    @Getter
    private StarShip starShip;
    @Getter
    private boolean occupied;

    public void setStarShip(StarShip probe) {
        this.starShip = probe;
        occupied = true;
    }

    public void removeProbe() {
        occupied = false;
    }

}
