package com.mycompany.javafx_db_example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {
    @FXML
    private Button loginBtn, createAccBtn;

    @FXML
    private TextField uNameTxtfld, pwTxtfld;


    @FXML
    public void loginClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 560);
        scene.getStylesheets().add("sunset.css");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createAccountClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 560);
        scene.getStylesheets().add("sunset.css");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}
