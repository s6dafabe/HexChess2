package com.Application;

public class PlayingField {

    public static Position hexIndexToCoordinates(int i ,int j,int side){
        return new Position(i*1.5*side,-j*Math.sqrt(3)*side-Math.abs(i-6)*0.5*Math.sqrt(3)*side);
    }
}
