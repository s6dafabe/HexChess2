package com.Application;

public class Pawn extends Piece {
    Position pos;
    public Pawn(){

    }

    //ToDo
    @Override
    public Position[] getValidMovesNoPieces() {
        System.out.println("getValidMovesNoPieces in Pawn still ToDo");
        return new Position[0];
    }

    @Override
    public Position[] getValidMovesWithPieces(Field currentPositions) {
        System.out.println("getValidMovesWithPieces in Pawn still ToDo");
        return new Position[0];
    }
    @Override
    public String printType(){
        return "Pawn";
    }
}
