package com.Application;

public class PlayingField {

    public static Point hexIndexToCoordinates(int i , int j, int side){
        return new Point(i*1.5*side,-j*Math.sqrt(3)*side-Math.abs(i-5)*0.5*Math.sqrt(3)*side);
    }
}
