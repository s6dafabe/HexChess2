package com.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HelloController {
    Polygon[][] fields;
    ImageView[][] pieceSprites;
    private Field backendField;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Polygon selectedField;
    private Piece selectedPiece;
    @FXML
    Polygon createHexagon(Point center, String color){
        Polygon hex = new Polygon(-1,0,-0.5,Math.sqrt(0.75),0.5,Math.sqrt(0.75),1,0,0.5,-Math.sqrt(0.75),-0.5,
                -Math.sqrt(0.75));
        hex.setScaleX(30);
        hex.setScaleY(30);
        hex.setLayoutX(center.xPos);
        hex.setLayoutY(center.yPos);
        hex.setFill(Color.web(color));
        return hex;
    }
    ImageView createPiece(Point center, Piece piece){
        String baseDir = "Images/";
        String source = baseDir + (piece.whitePiece ? "PiecesWhite/":"PiecesBlack/")+piece.printType()+".png";
        Image img = null;
        try {
            img = new Image(new FileInputStream(source));
        }
        catch (FileNotFoundException e){
            System.out.println("Image not found:" + source);
        }
        ImageView imview = new ImageView(img);
        imview.setLayoutX(center.xPos-32);
        imview.setLayoutY(center.yPos-32);
        imview.setScaleX(0.7);
        imview.setScaleY(0.7);
        return imview;

    }
    EventHandler<MouseEvent> createHexEvent(int x, int y){
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(selectedPiece == null){
                    try{
                        Piece clickedPiece = backendField.getPieceAtPos(new Position(x,y));
                        if(clickedPiece != null && clickedPiece.whitePiece == backendField.whiteToMove()){
                            selectedPiece = clickedPiece;
                            selectedField = fields[x][y];
                            Color selectedFieldColor = (Color)(selectedField.getFill());
                            selectedField.setFill(selectedFieldColor.darker());

                        }
                    }
                    catch (Exception e){}
                }
                else{
                    try{
                        Position oldPos = selectedPiece.getPos();
                        selectedPiece.moveToIfValid(new Position(x,y),backendField);
                        ImageView movingImg = pieceSprites[oldPos.getxPos()][oldPos.getyPos()];
                        pieceSprites[oldPos.getxPos()][oldPos.getyPos()] = null;
                        //ToDo: Again, we need to change something here if we add en passant
                        if(pieceSprites[x][y] != null){
                            anchorPane.getChildren().remove(pieceSprites[x][y]);
                        }
                        pieceSprites[x][y] = movingImg;
                        Point pos = PlayingField.hexIndexToCoordinates(x,y,30).add(offset);
                        movingImg.setLayoutX(pos.xPos-32);
                        movingImg.setLayoutY(pos.yPos-32);

                    }
                    catch (Exception e){
                        System.out.println("invalid move");
                    }

                    selectedPiece = null;
                    Color selectedFieldColor = (Color)(selectedField.getFill());
                    selectedField.setFill(selectedFieldColor.brighter());
                    selectedField = null;
                }

            }
        };
        return handler;
    }
    EventHandler<MouseEvent> createPieceEvent(Piece piece){
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int x = piece.getPos().getxPos();
                int y = piece.getPos().getyPos();
                if(selectedPiece == null){
                    try{
                        if(piece.whitePiece == backendField.whiteToMove()){
                            selectedPiece = piece;
                            selectedField = fields[x][y];
                            Color selectedFieldColor = (Color)(selectedField.getFill());
                            selectedField.setFill(selectedFieldColor.darker());

                        }
                    }
                    catch (Exception e){}
                }
                else{
                    try{
                        Position oldPos = selectedPiece.getPos();
                        selectedPiece.moveToIfValid(new Position(x,y),backendField);
                        ImageView movingImg = pieceSprites[oldPos.getxPos()][oldPos.getyPos()];
                        pieceSprites[oldPos.getxPos()][oldPos.getyPos()] = null;
                        //ToDo: Again, we need to change something here if we add en passant
                        if(pieceSprites[x][y] != null){
                            anchorPane.getChildren().remove(pieceSprites[x][y]);
                        }
                        pieceSprites[x][y] = movingImg;
                        Point pos = PlayingField.hexIndexToCoordinates(x,y,30).add(offset);
                        movingImg.setLayoutX(pos.xPos-32);
                        movingImg.setLayoutY(pos.yPos-32);

                    }
                    catch (Exception e){
                        System.out.println("invalid move");
                    }

                    selectedPiece = null;
                    Color selectedFieldColor = (Color)(selectedField.getFill());
                    selectedField.setFill(selectedFieldColor.brighter());
                    selectedField = null;
                }

            }
        };
        return handler;
    }
    Point offset = new Point(200,700);
    public void setUpPosition(){
        for(int i = 0; i < 11;i++){
            for(int j = 0; j< 11;j++){
                pieceSprites[i][j] = null;
                try {
                    Piece pieceAtPos = backendField.getPieceAtPos(new Position(i, j));
                    if(pieceAtPos != null) {
                        Point pos = PlayingField.hexIndexToCoordinates(i,j,30);
                        ImageView pieceImage = createPiece(pos.add(offset), pieceAtPos);
                        anchorPane.getChildren().add(pieceImage);
                        pieceImage.addEventFilter(MouseEvent.MOUSE_CLICKED,createPieceEvent(pieceAtPos));
                        pieceSprites[i][j] = pieceImage;
                    }
                }
                catch(Exception e){System.out.println("Outside field:"+i+" "+j);}
            }
        }
    }
    @FXML
    public void initialize() throws Exception {
        backendField = new Field();
        selectedField = null;
        selectedPiece = null;
        fields = new Polygon[11][11];
        pieceSprites = new ImageView[11][11];

        String[] fieldColors = {"0xB5651D","0xDAAE7C","0xFFF8DC"};

        for (int j = 0; j < 6; j++) {
            for (int i =0; i<11; i++) {
                Point pos = PlayingField.hexIndexToCoordinates(i,j,30);
                int colorIndex = (i < 6) ? ((i+j) % 3) : ((10-i+j)%3);
                fields[i][j] = createHexagon(pos.add(offset),fieldColors[colorIndex]);
                fields[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED,createHexEvent(i,j));
                anchorPane.getChildren().add(fields[i][j]);
            }
        }

        for (int j = 6; j < 11; j++) {
            for (int i =j-5; i<16-j; i++) {
                int colorIndex = (i < 6) ? ((i+j) % 3) : ((10-i+j)%3);
                Point pos = PlayingField.hexIndexToCoordinates(i,j,30);
                fields[i][j] = createHexagon(pos.add(offset),fieldColors[colorIndex]);
                fields[i][j].addEventFilter(MouseEvent.MOUSE_CLICKED,createHexEvent(i,j));
                anchorPane.getChildren().add(fields[i][j]);
            }
        }
        //Point test = PlayingField.hexIndexToCoordinates(11,1,30);
        //anchorPane.getChildren().add(createHexagon(test.add(offset),"blueviolet"));
        setUpPosition();
        Label label1 = new Label();
        label1.setText("bla");
        //anchorPane.getChildren().add(label1);
    }

}

/*
daae7c
fff8dc
b5651d
 */