package com.talthur.project.core.enums;

import lombok.Getter;

@Getter
public enum OrientationEnum {
    NORTH,
    EAST,
    SOUTH,
    WEST;

   public OrientationEnum getNext(){
       return values()[(ordinal() + 1) % values().length];
   }

    public OrientationEnum getPrevious(){
        return values()[(ordinal() - 1 + values().length) % values().length];
    }
}
