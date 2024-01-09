package com.Application;

import java.util.Arrays;

public abstract class Piece {
    //The position this piece is on
    Position pos = null;
    Boolean whitePiece;
    public Piece(Boolean isWhite){
        whitePiece = isWhite;
    }
    //returns an array with all possible Positions this piece could move to if there were no other pieces on the field
    public abstract Position[] getValidMovesNoPieces();

    //returns an array with all possible Positions this piece can move to including taking other pieces
    public abstract Position[] getValidMovesWithPieces(Field currentPositions);

    //moves this piece to a new position if valid, throws an Exception if not
    public void moveToIfValid(Position newPos, Field currentPositions) throws Exception{
        currentPositions.movePiece(this, newPos);
    }
    public void setPos(Position pos){
        this.pos = pos;
    }

    public String printType(){
        return "null";
    }
    public Position getPos(){
        return this.pos;
    }
}
