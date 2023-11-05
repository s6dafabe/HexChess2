package com.Application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox vbox;

    @FXML
    public void initialize() throws Exception {
        Polygon[][] fields = new Polygon[11][11];
        for (int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {

            }
        }
        Label label1 = new Label();
        label1.setText("bla");
        vbox.getChildren().add(label1);
    }


    @FXML
    protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to JavaFX Application!");
        }

}