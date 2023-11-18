package com.Application;

public class Position {
    public double x;
    public double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Position add(Position pos2){
        return new Position(x+pos2.x,y+pos2.y);
    }
}
