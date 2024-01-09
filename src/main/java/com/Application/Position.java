package com.Application;

import javafx.geometry.Pos;

public class Position extends Point{
    public Position(int xPos, int yPos) throws Exception {
        super(xPos, yPos);
        if (!Field.isValid(xPos, yPos)) {
            throw new Exception("Exception while initializing Position: no valid Coordinates given: " + super.print());
        }
    }
    public Position(Point point){
        super((int) point.xPos, (int) point.yPos);
    }

    public int getxPos() {
        return (int) super.xPos;
    }
    public int getyPos() {
        return (int) super.yPos;
    }

}
