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
    Polygon createHexagon(int i, int j,String color){
        Polygon hex = new Polygon(-1,0,-0.5,Math.sqrt(0.75),0.5,Math.sqrt(0.75),1,0,0.5,-Math.sqrt(0.75),-0.5,
                -Math.sqrt(0.75));
        hex.setScaleX(30);
        hex.setScaleY(30);
        hex.setLayoutX(i);
        hex.setLayoutY(j);
        hex.setFill(Paint.valueOf(color));
        return hex;
    }
    @FXML
    public void initialize() throws Exception {
        Polygon[][] fields = new Polygon[11][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 2-(int)Math.floor((double)i/2); j <= 2+(int)Math.ceil((double)i/2); j++) {
                fields[i][j] = createHexagon(i*50+50,j*50+50,(i+j) % 2 == 0 ?"blueviolet" : "blue" );
                anchorPane.getChildren().add(fields[i][j]);
            }
        }

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