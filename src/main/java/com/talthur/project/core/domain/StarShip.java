package com.talthur.project.core.domain;

import java.util.List;

public interface StarShip {

    List<Integer> getFuturePosition();

    void rotate(char command);

}
