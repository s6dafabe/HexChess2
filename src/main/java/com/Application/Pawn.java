package com.Application;

import java.util.ArrayList;

public class Pawn extends Piece {

    boolean firstMove;
    public Pawn(boolean isWhite, boolean firstMove){
        super(isWhite);
        this.firstMove = firstMove;
    }
    public Pawn(boolean isWhite, boolean firstMove,Position pos){
        super(isWhite,pos);
        this.firstMove = firstMove;
    }
    //ToDo
    @Override
    public Position[] getValidMovesNoPieces() {
        System.out.println("getValidMovesNoPieces in Pawn still ToDo");
        ArrayList<Position> valid = new ArrayList<>();

        // Case: Pawn moves forward (If white: upwards, if black: downwards). 4 possible moves:
        // The pawn either stays in the column and moves one step in vertical direction
        // The pawn captures a piece diagonally away from the middle column by changing the column and staying in (white) or changing (black) the row
        // The pawn captures a piece diagonally towards the middle column by changing the column and staying in (black) or changing (white) the row
        // The pawn either stays in the column and moves two steps in vertical direction if still at staring location

        // For white this means the moves are:
        // (i)          (x, y+1)                     for all x,
        // (ii)         (x+1, y+1), (x-1, y+1)       for x = 5 (f)
        // (iii)        (x+1, y+1), (x-1, y)         for x <= 4 (e),
        // (iv)         (x+1, y), (x-1, y+1)         for x >= 6 (g)
        // (v)          (x, y+2)                     for x=y+1 && x<=5 (left side starting positions) and x+y=9 (right side s.p.)

        // For black this means the moves are:
        // (i)          (x, y-1)                     for all x,
        // (ii)         (x+1, y-1), (x-1, y-1)       for x = 5 (f)
        // (iii)        (x+1, y-1), (x-1, y)         for x >= 6 (g)
        // (iv)         (x+1, y), (x-1, y-1)         for x <= 4 (e),
        // (v)          (x, y-2)                     for y=6 (starting positions for black)

        //toDo: Promotion!
        // I dont know if we want to do this here or elsewhere, it is kind of part of the pawn move, but I think we dont need it here
        // Copy complete comment over to where you think this should go
        // Moves are the same but if piece ends up in
        // (x, 0) for all x (black player promotes)
        // (x, x+5) for x<=5 (white player promotes on left side)
        // (x, 15-x) for x >= 5 (white player promotes on right side)
        // It needs an additional information about the piece it promotes into (bishop, rock, knight, queen).
        // Info: If one promotion is valid, all promotions are valid, so no need to check


        // White pieces can only ever go up
        int yChange = 1;
        // Black pieces can only ever go down
        if (!this.whitePiece){
            yChange = -1;
        }
        int case234var;
        if (this.pos.getxPos() == 5){
            case234var = 2;
        }else if (this.pos.getxPos() <= 4) {
            // if black: case 4, if white case 3
            case234var = (int) (3.5 - 0.5 * yChange);
        }else {
            // if black: case 3, if white case 4
            case234var = (int) (3.5 + 0.5 * yChange);
        }

        for(int xMove =-1;xMove <= 1;xMove++){
            try {
                // Add case i
                valid.add(new Position(pos.getxPos(), pos.getyPos() + yChange));
                // Add case ii/iii/iv depending on case234var
                if (case234var == 2){
                    valid.add(new Position(pos.getxPos() + 1, pos.getyPos() + yChange));
                    valid.add(new Position(pos.getxPos() - 1, pos.getyPos() + yChange));
                }
                if (case234var == 3){
                    valid.add(new Position(pos.getxPos() + 1, pos.getyPos() + yChange));
                    valid.add(new Position(pos.getxPos() - 1, pos.getyPos()));
                }
                if (case234var == 4){
                    valid.add(new Position(pos.getxPos() + 1, pos.getyPos()));
                    valid.add(new Position(pos.getxPos() - 1, pos.getyPos() + yChange));
                }
                // Add case v
                if (firstMove) valid.add(new Position(pos.getxPos(), pos.getyPos() + 2 * yChange));

            }
            catch(Exception e){
                //still toDo
                System.out.println("ToDo " + e);
            }
        }
        return valid.toArray(new Position[0]);
    }

    @Override
    public Position[] getValidMovesWithPieces(Field currentPositions) {
        Position[] candidates = getValidMovesNoPieces();
        ArrayList<Position> valid = new ArrayList<>();
        for(Position can:candidates){
            // TODO: filter legal moves
            // Move is not legal if:
            // In case i: Field moved onto is blocked
            // In case ii/iii/iv: Field moved onto is NOT blocked --> Exception En passant
            // In case v: case i would be not legal or field moved onto is blocked

            // TODO: Player is in check after move

            //if legal, add move to valid

            //i think we should skip en passant for now
            Piece blocking = currentPositions.getPieceAtPos(can);
            //Case distinction if we move or capture
            if(can.getxPos() == pos.getxPos()){
                //Case i+v: Target Field is blocked
                if(blocking != null){
                    continue;
                }
                //Case v: Piece in front of pawn
                 try {
                     int yChange = whitePiece? 1:-1;
                     Piece inFront = currentPositions.getPieceAtPos(new Position(pos.getxPos(), pos.getyPos()+ yChange));
                     if (inFront != null) {
                        continue;
                     }
                 }
                 catch(Exception e){}
            }
            else{

            }
            valid.add(can);

        }
        return valid.toArray(new Position[0]);
    }

    @Override
    public void pieceUpdate(){
        firstMove = false;
    }
    @Override
    public String printType(){
        return "Pawn";
    }


}
