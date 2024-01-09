package com.Application;

import javafx.geometry.Pos;

import java.util.ArrayList;

public class Pawn extends Piece {
    Position pos;
    Boolean firstMove;
    public Pawn(Boolean isWhite){
        super(isWhite);
        firstMove = true;
    }

    //ToDo
    @Override
    public Position[] getValidMovesNoPieces() {
        System.out.println("getValidMovesNoPieces in Pawn still ToDo");
        ArrayList<Position> valid = new ArrayList<Position>();
        for(int xMove =-1;xMove <= 1;xMove++){
            try {
                valid.add(new Position(pos.getxPos() + xMove, pos.getyPos() + 1));
            }
            catch(Exception e){}
        }
        if(firstMove){
            for(int xMove =-1;xMove <= 1;xMove++){
                try {
                    valid.add(new Position(pos.getxPos() + xMove, pos.getyPos() + 2));
                }
                catch(Exception e){}
            }
        }
        return valid.toArray(new Position[0]);
    }

    @Override
    public Position[] getValidMovesWithPieces(Field currentPositions) {
        Position[] candidates = getValidMovesNoPieces();
        ArrayList<Position> valid = new ArrayList<Position>();
        for(Position pos:candidates){
            // TODO: filter legal moves
        }
        return new Position[0];
    }
    @Override
    public String printType(){
        return "Pawn";
    }
}
