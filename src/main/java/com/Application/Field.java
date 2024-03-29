package com.Application;

import java.util.Arrays;

public class Field {
    /* indexArray saves the positions of the field and can be used to change positioning of pieces etc.
    since the playing field is not square the indices not on the field (for example [10][1]) are empty and return an
    error if tried to read/write into.
    The indices are shifted by one since the array index starts at 0 while the chess indices are starting at 1.
    That means b4 -> [1][3]. The first index describes the column (a, ..., k), the second describes the row (1, ..., 11)
    */
    private Piece[][] pieceArray;

    private boolean whiteTurn;
    public Field(){
        pieceArray = new Piece[11][11];
        //loop through all columns
        for (int i = 0; i < 11; i++) {
            // this loop makes sure only valid indices are set. From 6 rows in column a=0 one row is added each
            // column until there are 11 rows in column f = 5 and then one column is removed again until 6 columns
            // in row k=10
            for (int j = 0; j <= 10-Math.abs(5-i); j++) {

                pieceArray[i][j] = null;
            }
        }
        try {
            //Added pawns
            for(int i = 1; i <= 9;i++) {
                //System.out.println(i+" "+Math.min(i-1,11-i));
                System.out.println("Piece:" + i + " "+ Math.min(i-1,11-i));
                pieceArray[i][Math.min(i-1,9-i)] = new Pawn(true, true, new Position(i, Math.min(i-1,9-i)));
                pieceArray[i][6] = new Pawn(false, true, new Position(i, 6));
            }
            //Added Rooks
            pieceArray[2][0] = new Rook(true,new Position(2,0));
            pieceArray[8][0] = new Rook(true,new Position(8,0));
            pieceArray[2][7] = new Rook(false,new Position(2,7));
            pieceArray[8][7] = new Rook(false,new Position(8,7));
            //Added bsihops
            pieceArray[5][0] = new Bishop(true,new Position(5,0));
            pieceArray[5][1] = new Bishop(true,new Position(5,1));
            pieceArray[5][2] = new Bishop(true,new Position(5,2));
            pieceArray[5][8] = new Bishop(false,new Position(5,8));
            pieceArray[5][9] = new Bishop(false,new Position(5,9));
            pieceArray[5][10] = new Bishop(false,new Position(5,10));
            //Added jumpy obis
            pieceArray[3][0] = new Knight(true,new Position(3,0));
            pieceArray[7][0] = new Knight(true,new Position(7,0));
            pieceArray[3][8] = new Knight(false,new Position(3,8));
            pieceArray[7][8] = new Knight(false,new Position(7,8));
        }
        catch(Exception e){}

        whiteTurn = true;
    }

    // returns if the Position is a valid Position on the playing field. Rows go from a-k = 0-10 and columns 0-10
    public static boolean isValid(int x, int y){
        // check if Position lies in bounds
        if (x < 0 || x > 10 || y < 0 || y > 10){
            return false;
        }
        // check if given position lies on non-valid fields and return true if it does
        return y <= 10 - Math.abs(5 - x);
    }

    // returns if the Position is a valid Position on the playing field. Rows go from a-k = 0-10 and columns 0-10
    public static boolean isValid(Position pos){
        // check if Position lies in bounds
        int x = pos.getxPos();
        int y = pos.getyPos();
        if (x < 0 || x > 10 || y < 0 || y > 10){
            return false;
        }
        // check if given position lies on non-valid fields and return true if it does
        return y <= 10 - Math.abs(5 - x);
    }

    public void movePiece(Piece movingPiece, Position newPos) throws Exception{
        Position oldPiecePos = movingPiece.getPos();
        if(whiteTurn != movingPiece.whitePiece){
            throw new Exception("Wrong turn: It is " +(whiteTurn? "white":"black") +
                    " to move, but the piece is" + (movingPiece.whitePiece ? "white":"black"));
        }
        if (!Field.isValid(newPos)){
            throw new Exception("Piece cant move: No valid target position given: " + newPos.print() + " is not valid");
        }
        if (pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] != movingPiece){
            throw new Exception("Piece cant move: " + movingPiece.printType() + " not on Position " + oldPiecePos.print() + " found");
        }
        Position[] validPositions = movingPiece.getValidMovesWithPieces(this);
        boolean containedValid = false;
        for(Position pos:validPositions){
            System.out.println(pos.print());
            if(newPos.equalPoint(pos)){
                containedValid = true;
                break;
            }
        }
        if (!containedValid){
            throw new Exception("Piece cant move: Piece cant reach target position " + newPos.print());
        }

        // Now it's safe that there is the moving piece on this Field on the position movingPiece.getPos()
        // and that it can move to the new location

        // Letting the Piece know we moved it
        movingPiece.setPos(newPos);

        // Update the field array. A Piece standing on newPos before gets taken and is removed from the playing field.
        pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] = null;
        pieceArray[newPos.getxPos()][newPos.getyPos()] = movingPiece;

        //Update the turn
        whiteTurn = !whiteTurn;

    }

    // Since with En-Passant we don't take the piece on the field we move to, we need an extra method for this case:
    public void movePieceEnPassant(Pawn movingPiece, Position newPos, Position takenPawnPos) throws Exception{
        Position oldPiecePos = movingPiece.getPos();
        if (!Field.isValid(newPos)){
            throw new Exception("Piece cant move: No valid target position given: " + newPos.print() + " is not valid");
        }
        if (!Field.isValid(takenPawnPos)){
            throw new Exception("Piece cant move: No valid Pawn position given: " + takenPawnPos.print() + " is not valid");
        }
        if (pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] != movingPiece){
            throw new Exception("Piece cant move: " + movingPiece.printType() + " not on Position " + oldPiecePos.print() + " found");
        }
        Position[] validPositions = movingPiece.getValidMovesWithPieces(this);
        if (!Arrays.asList(validPositions).contains(newPos)){
            throw new Exception("Piece cant move: Piece cant reach target position " + newPos.print());
        }
        // check if the moving Piece is a Pawn
        if (!movingPiece.printType().equals("Pawn")){
            throw new Exception("En Passant not possible: no Pawn on " + oldPiecePos.print());
        }
        // check if there is a Pawn on takenPawnPos
        if (!pieceArray[takenPawnPos.getxPos()][takenPawnPos.getyPos()].printType().equals("Pawn")){
            throw new Exception("En Passant not possible: no Pawn on " + takenPawnPos.print());
        }
        //check if Piece is on newPos
        if (pieceArray[newPos.getxPos()][newPos.getyPos()] != null){
            throw new Exception("En Passant not possible: Found "+ pieceArray[newPos.getxPos()][newPos.getyPos()].printType() + " on " + newPos.print());
        }
        // Now it's safe that there is the Pawn the position movingPiece.getPos() and takenPawnPos
        // and that it can move to the new location

        // Letting the Piece know we moved it
        movingPiece.setPos(newPos);

        // Update the field array. The Pawn standing on takenPawnPos gets taken and is removed from the playing field.
        pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] = null;
        pieceArray[newPos.getxPos()][newPos.getyPos()] = movingPiece;
        pieceArray[takenPawnPos.getxPos()][takenPawnPos.getyPos()] = null;

        //Update the turn
        whiteTurn = !whiteTurn;

    }
    public Piece getPieceAtPos(Position pos){
        return pieceArray[pos.getxPos()][pos.getyPos()];
    }
    public boolean whiteToMove(){
        return whiteTurn;
    }
    public static void main(String[] args) {
        new Field();
    }
}
