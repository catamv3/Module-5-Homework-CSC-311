package com.mycompany.javafx_db_example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class PrimaryController implements Initializable {

    @FXML
    TextField id, name, email, address, phone;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, String> tv_id, tv_name, tv_email, tv_address, tv_phone;
    @FXML
    private ImageView img_view;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    private boolean sunsetModeOn;

    @FXML
    private static MenuItem themeSwitcher;
    App.ThemeHandler viewHandler;

    private final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person("1stu", "Michael Catalanotti", "catamv3@farmingdale.edu", "Seaford","444-444-4444"),
                    new Person("2stu", "Albert Einstein", "albert@hotmail.com", "Europe","070-963-1920"),
                    new Person("31", "Albert Einstein", "albert@hotmail.com", "Europe","070-963-1920")
                    , new Person("42", "Albert Einstein", "albert@hotmail.com", "Europe","070-963-1920")

            );

    //declare a controler CONSTRUCTOR that accpts a stage
    //declare a controller that accepts an observable lsit and a stage

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tv_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tv_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tv.setItems(data);
        //viewHandler = new App.ThemeHandler();
        sunsetModeOn = true;
    }
    





    /*
            Trying to change the theme
            do i need to declare a gui/controller for each theme i want to show?
     */
    @FXML
    private void changeScene(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource(); // Get the MenuItem
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        if(sunsetModeOn){
            String cssPath = "moonTheme.css";
            scene.getStylesheets().clear();
            scene.getStylesheets().add(cssPath);
            sunsetModeOn = !sunsetModeOn;
        } else {
            String cssPath = "sunset.css";
            scene.getStylesheets().clear();
            scene.getStylesheets().add(cssPath);
            sunsetModeOn = !sunsetModeOn;
        }

        // Load and apply the new CSS stylesheet

    }

    @FXML
    private void closeScreen(ActionEvent event){
        System.exit(1);
    }


        @FXML
        protected void addNewRecord() {
            try {
                data.add(new Person(
                        id.getText(),
                        name.getText(),
                        email.getText(), address.getText(),phone.getText()
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//        public void checkUserName(String userName){
//            boolean flag = true;
//            do{
//                String namePattern = "[a-z]*{2,25}";
//                Pattern nPattern = Pattern.compile(namePattern);
//
//
//            } while (!flag){
//                System.out.println("valid data");
//            }
//
//        }

    @FXML
    protected void clearForm(){
        id.setText("");
        name.setText(""); //this works
        //last_name.clear(); //this works also
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
            id.setText(p.getId());
            name.setText(p.getName());
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
            p2.setId(id.getText());
            p2.setName(name.getText());
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
            tv.getItems().remove(p);

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
        public void jaja(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 850, 560);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    @FXML
    public void hehe(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 560);
        //scene.getStylesheets().add(getClass().getResource("sunset.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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