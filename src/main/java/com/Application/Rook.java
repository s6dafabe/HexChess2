package com.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(boolean isWhite){
        super(isWhite);
    }
    public Rook(boolean isWhite, Position pos){
        super(isWhite,pos);
    }
    //ToDo
    @Override
    public Position[] getValidMovesNoPieces() {
    return null;
    }
    public Position goStep(Position currentPos,int direction) {
        /*
        0:up,1:upright,2:downright,3:down,4:downleft,5:upleft
         */
        int x = currentPos.getxPos();
        int y = currentPos.getyPos();
        int xNew;
        int yNew;
        //0 left 1 middle 2 right
        int middle = (x==5)? 1 : ((x < 5)?0:2);
        try {
            switch(direction){
                case 0:
                    xNew = x;
                    yNew = y+1;
                    break;
                case 1:
                    xNew = x+1;
                    yNew = y + ((middle == 0)? 1:0);
                    break;
                case 2:
                    xNew = x+1;
                    yNew = y + ((middle == 0)? 0:-1);
                    break;
                case 3:
                    xNew = x;
                    yNew = y-1;
                    break;
                case 4:
                    xNew = x-1;
                    yNew = y + ((middle == 2)? 0:-1);
                    break;
                case 5:
                    xNew = x-1;
                    yNew = y + ((middle == 2)? 1:0);
                    break;
                default:
                    throw new Exception("Bullshit direction >:(");

            }
            if(Field.isValid(xNew, yNew) ){
                return new Position(xNew, yNew);
            }
            else{
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Position[] getValidMovesWithPieces(Field currentPositions) {
        ArrayList<Position> valid = new ArrayList<>();
        /*
        Up/Down: y+- x nothing
        Up to middle: y+ x+-
        up away from middle y nothing x+-
        down to middle y nothing x+-
        down away from middle y- x+-
         */
        int x = pos.getxPos();
        int y = pos.getyPos();
        for(int direction = 0;direction<6;direction++){
            Position p = pos;
            while(true) {
                Position pNew = goStep(p,direction);
                if(pNew == null)break;

                Piece blocking = currentPositions.getPieceAtPos(pNew);
                if (blocking != null) {
                    if (blocking.whitePiece != this.whitePiece) {
                        valid.add(pNew);
                    }
                    break;
                } else {
                    valid.add(pNew);
                    p = pNew;
                }
            }
        }

        return valid.toArray(new Position[0]);
    }


    @Override
    public String printType(){
        return "Rook";
    }


}
