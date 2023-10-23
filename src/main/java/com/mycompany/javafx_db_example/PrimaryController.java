package com.mycompany.javafx_db_example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*

 * SO:
         * a table view uses an observable list to display data.
         * an observable list can store any data type/class, we just have to declare it in a class file.
         * the initialize method below links the observable list to the table view.
         *      in doing the initialize() method, now any time, anything in the list changes
         *      the table view will automatically update.

 */

public class PrimaryController implements Initializable {

    @FXML
    TextField first_name, last_name, email, address, phone;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_email, tv_address, tv_phone;
    @FXML
    private ImageView img_view;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person(1, "Michael", "Catalanotti", "catamv3@farmingdale.edu", "Seaford","444-444-4444"),
                    new Person(2, "Albert", "Einstein", "albert@hotmail.com", "Europe","070-963-1920")

            );

    //declare a controler CONSTRUCTOR that accpts a stage
    //declare a controller that accepts an observable lsit and a stage

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /*
tv = new TableView<>();
        tv_id = new TableColumn<>();
        tv_fn = new TableColumn<>();
        tv_ln = new TableColumn<>();
        tv_dept = new TableColumn<>();
        tv_major = new TableColumn<>();
         */

        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tv_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tv_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        tv.setItems(data);


    }



    @FXML
    private MenuItem viewItem;

/*



    @FXML
    private void changeScene() {
        //Parent newRoot = FXMLLoader.load(getClass().getResource("primary.fxml"));

        Scene currentScene = stage.getScene();
        currentScene.getStylesheets().clear();
        scene.getStylesheets().add("moonTheme.css")
        Parent currentRoot = scene.getRoot();


        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            Scene newScene = new Scene(newRoot, 850, 560);
            primaryStage.setTitle("Algarvae Sunset");

            primaryStage.setScene(newScene);
        });

        fadeOut.play();
    }



    */

    @FXML
    private void changeScene() {
    Scene newScene = stage.getScene();
    newScene.getStylesheets().clear();
    newScene.getStylesheets().add("moonTheme.css");
    stage.setScene(newScene);
}

        @FXML
        protected void addNewRecord() {
            try {
                data.add(new Person(
                        data.size()+1,
                        first_name.getText(),
                        last_name.getText(),
                        email.getText(), address.getText(),phone.getText()
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    @FXML
    protected void clearForm(){
        first_name.setText(""); //this works
        last_name.clear(); //this works also
        email.setText("");
        address.setText("");
        phone.setText("");
    }

    /*

    on table view we have onMouseClick linked ot selectedItemTC()
     */
        @FXML
        protected void selectedItemTV(MouseEvent mouseEvent){
            Person p = tv.getSelectionModel().getSelectedItem();
            first_name.setText(p.getFirstName());
            last_name.setText(p.getLastName());
            email.setText(p.getEmail());
            address.setText(p.getAddress());
            phone.setText(p.getPhone());
        }



        @FXML
        protected void editRecord(){
            Person p = tv.getSelectionModel().getSelectedItem();
            //selection model returns list of colums as rows in a table view
            //selected item refers to an individual instance in that  tableview (a specific row)
            //in this case, selection model is person

            int c = data.indexOf(p);
            Person p2 = new Person();
            p2.setId(c+1);

            p2.setFirstName(first_name.getText());
            p2.setLastName(last_name.getText());
            p2.setEmail(email.getText());
            p2.setAddress(address.getText());
            p2.setPhone(phone.getText());

            data.remove(c);
            data.add(c,p2);
            tv.getSelectionModel().select(c);


        }
        @FXML
        protected void deleteRecord(){
            Person p = tv.getSelectionModel().getSelectedItem();
            data.remove(p);
        }

        /**
         * enable shortucts in menuItem tabs (ctrl +f)- edit, or whatever
         *
         * aws vs azure vs firebase
         *
         * authentication thru azure
         *
         * involving other authentications (with sql database) -- use firebase!s
         * firebase will not be discussed till the end
         * explore how to do authentication via firebase/azure
         */
        @FXML
        protected void closeScreen(ActionEvent event){

        }




        /**
         * how to load an image
         *
         */
        @FXML
        protected void showImage(){
            File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
            if(file!=null){
                img_view.setImage(new Image(file.toURI().toString()));
            }
        }
}