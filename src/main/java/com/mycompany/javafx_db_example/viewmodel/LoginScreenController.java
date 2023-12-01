package com.mycompany.javafx_db_example.viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    @FXML
    private Button loginButton, registerButton;

    @FXML
    private TextField idField, pwField;

    @FXML
    private ImageView imageView;





    public void loginClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 515);
        scene.getStylesheets().add("sunset.css");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void createAccountClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 515);
        scene.getStylesheets().add("sunset.css");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(event -> {
            try {
                if(registeredUser("here","here"))
                    loginClicked(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private boolean registeredUser(String id, String password){
        boolean isRegistered = true;



        return isRegistered;
    }
}
