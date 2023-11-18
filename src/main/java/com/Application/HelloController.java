package com.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    Polygon createHexagon(Position center,String color){
        Polygon hex = new Polygon(-1,0,-0.5,Math.sqrt(0.75),0.5,Math.sqrt(0.75),1,0,0.5,-Math.sqrt(0.75),-0.5,
                -Math.sqrt(0.75));
        hex.setScaleX(30);
        hex.setScaleY(30);
        hex.setLayoutX(center.x);
        hex.setLayoutY(center.y);
        hex.setFill(Paint.valueOf(color));
        return hex;
    }
    Position offset = new Position(200,700);

    @FXML
    public void initialize() throws Exception {
        Polygon[][] fields = new Polygon[12][12];
        for (int j = 1; j < 7; j++) {
            for (int i =1; i<12; i++) {
                Position pos = PlayingField.hexIndexToCoordinates(i,j,30);
                fields[i][j] = createHexagon(pos.add(offset),i == 0 & j == 0 ?"blueviolet" : "blue" );
                anchorPane.getChildren().add(fields[i][j]);
            }
        }
        for (int j = 7; j < 12; j++) {
            for (int i =j-5; i<18-j; i++) {
                Position pos = PlayingField.hexIndexToCoordinates(i,j,30);
                fields[i][j] = createHexagon(pos.add(offset),i == 0 & j == 0 ?"blueviolet" : "blue" );
                anchorPane.getChildren().add(fields[i][j]);
            }
        }
        Position test = PlayingField.hexIndexToCoordinates(11,1,30);
        anchorPane.getChildren().add(createHexagon(test.add(offset),"blueviolet"));

        for (int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {



            }
        }
        Label label1 = new Label();
        label1.setText("bla");
        //anchorPane.getChildren().add(label1);
    }


    @FXML
    protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to JavaFX Application!");
        }

}