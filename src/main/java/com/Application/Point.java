package com.Application;

public class Point {
    public double xPos;
    public double yPos;

    public Point(double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public Point add(Point pos2){
        return new Point(xPos+pos2.xPos,yPos+pos2.yPos);
    }

    public String print(){
        return "(" + this.xPos + ", " + this.yPos + ")";
    }
}
