package com.talthur.project.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Square {

    @Getter
    private Probe probe;
    @Getter
    private boolean occupied;

    public void setProbe(Probe probe) {
        this.probe = probe;
        occupied = true;
    }

    public void removeProbe() {
        occupied = false;
    }

}
