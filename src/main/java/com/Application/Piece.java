package com.Application;

public abstract class Piece {
    //The position this piece is on
    Position pos = null;
    boolean whitePiece;
    public Piece(boolean isWhite){
        whitePiece = isWhite;
    }
    public Piece(boolean isWhite,Position pos){
        whitePiece = isWhite;
        this.pos = pos;
    }
    //returns an array with all possible Positions this piece could move to if there were no other pieces on the field
    public abstract Position[] getValidMovesNoPieces();

    //returns an array with all possible Positions this piece can move to including taking other pieces
    public abstract Position[] getValidMovesWithPieces(Field currentPositions);

    protected void pieceUpdate(){

    }
    //moves this piece to a new position if valid, throws an Exception if not
    public void moveToIfValid(Position newPos, Field currentPositions) throws Exception{
        currentPositions.movePiece(this, newPos);
        pieceUpdate();
    }
    public void setPos(Position pos){
        this.pos = pos;
    }

    //Differentiate between setting position (i.e. for initialization) and moving piece, as this may be useful
    //in certain scenarios

    public String printType(){
        return "null";
    }
    public Position getPos(){
        return this.pos;
    }
}
