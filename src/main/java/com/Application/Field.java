package com.Application;

import java.util.Arrays;

public class Field {
    /* indexArray saves the positions of the field and can be used to change positioning of pieces etc.
    since the playing field is not square the indices not on the field (for example [10][1]) are empty and return an
    error if tried to read/write into.
    The indices are shifted by one since the array index starts at o while the chess indices are starting at 1.
    That means b4 -> [1][3]. The first index describes the column (a, ..., k), the second describes the row (1, ..., 11)
    */
    Piece[][] pieceArray;
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
    }

    // returns if the Position is a valid Position on the playing field. Rows go from a-k = 1-11 and columns 1-11 = 1-11
    public static boolean isValid(int x, int y){
        // check if Position lies in bounds
        if (x < 1 || x > 11 || y < 1 || y > 11){
            return false;
        }
        // check if given position lies on non-valid fields and return true if it does
        return y <= 11 - Math.abs(6 - x);
    }

    // returns if the Position is a valid Position on the playing field. Rows go from a-k = 1-11 and columns 1-11 = 1-11
    public static boolean isValid(Position pos){
        // check if Position lies in bounds
        int x = pos.getxPos();
        int y = pos.getyPos();
        if (x < 1 || x > 11 || y < 1 || y > 11){
            return false;
        }
        // check if given position lies on non-valid fields and return true if it does
        return y <= 11 - Math.abs(6 - x);
    }

    public void movePiece(Piece movingPiece, Position newPos) throws Exception{
        Position oldPiecePos = movingPiece.getPos();
        if (!Field.isValid(newPos)){
            throw new Exception("Piece cant move: No valid target position given: " + newPos.print() + " is not valid");
        }
        if (pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] != movingPiece){
            throw new Exception("Piece cant move: " + movingPiece.printType() + " not on Position " + oldPiecePos.print() + " found");
        }
        Position[] validPositions = movingPiece.getValidMovesWithPieces(this);
        if (!Arrays.asList(validPositions).contains(newPos)){
            throw new Exception("Piece cant move: Piece cant reach target position " + newPos.print());
        }

        // Now it's safe that there is the moving piece on this Field on the position movingPiece.getPos()
        // and that it can move to the new location

        // Letting the Piece know we moved it
        movingPiece.setPos(newPos);

        // Update the field array. A Piece standing on newPos before gets taken and is removed from the playing field.
        pieceArray[oldPiecePos.getxPos()][oldPiecePos.getyPos()] = null;
        pieceArray[newPos.getxPos()][newPos.getyPos()] = movingPiece;

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

    }

    public static void main(String[] args) {
        new Field();
    }
}
