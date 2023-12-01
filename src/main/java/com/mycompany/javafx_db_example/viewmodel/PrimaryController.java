package com.mycompany.javafx_db_example.viewmodel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.mycompany.javafx_db_example.model.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class PrimaryController implements Initializable {

    @FXML
    TextField fscidField, nameField, emailField, departmentField, majorField, passwordField;

    @FXML
    private Label outputLabel;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, String> tv_fscid, tv_name, tv_email, tv_department, tv_major;
    @FXML
    private ImageView img_view;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    final String MYSQL_SERVER_URL = "jdbc:mysql://michaelcac311.mariadb.database.azure.com/";
    final String DB_URL = "jdbc:mysql://michaelcac311.mariadb.database.azure.com/csc311_class";
    final String USERNAME = "catamv3@michaelcac311";
    final String PASSWORD = "Michael01!";


    private boolean sunsetModeOn;

    @FXML
    private Button addButton, editButton, deleteButton, clearButton;

    @FXML
    private static MenuItem themeSwitcher;
    App.ThemeHandler viewHandler;

    private boolean flag;

    private BooleanProperty isInformationValid = new SimpleBooleanProperty(false);


    private ObservableList<Person> data =
            FXCollections.observableArrayList();

    //declare a controler CONSTRUCTOR that accpts a stage
    //declare a controller that accepts an observable lsit and a stage

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setDisable(true);
        clearButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);

        fscidField.setOnKeyPressed(e ->{
            if (e.getCode() != KeyCode.TAB && flag) {
                fscidField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");

                flag = false;
            }
        } );

        fscidField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("FSCID text is focused");
            } else {
                if (fscidField.getText().matches("[a-z][a-z0-9]{2,6}")) {
                    nameField.setEditable(true);
                    fscidField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's name is invalid
                //update flag
                {
                    fscidField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    fscidField.setVisible(true);
                    fscidField.requestFocus();
                    outputLabel.setText(fscidField.getText() + " is not valid FSC ID");
                    nameField.setEditable(false);
                    emailField.setEditable(false);
                    departmentField.setEditable(false);
                    majorField.setEditable(false);
                    passwordField.setEditable(false);
                    flag = true;
                }

            }
        });





        /**
         * this lambda expression does not allow the user to advance to the next textfield if there is an error in their input
         * the flag relates to the flag (if any), produced when the input is compared to the regex.
         * @parm - this method does not accept any parameters.
         * @return - this method does not return anything
         * @throws - this method at large does not throw any errors, however sub parts of this method may.
         */
        nameField.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                nameField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");

                flag = false;
            }

        });



        /**
         * this method adds a listener to the namefield element. if the data entered does not match the pattern,
         * the user can not advance to the next textfield.
         *
         * Valid names entail a first and last name, seperated by one space. the first name must be between
         * 2 and no more than 12 letters, the last name must be between
         *          * 2 and no more than 13 letters
         */
        nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Name text is focused");
            } else {
                if (nameField.getText().matches("[A-Za-z]{2,12}[\\s][A-Za-z]{2,13}")) {
                    emailField.setEditable(true);
                    nameField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's name is invalid
                //update flag
                {
                    nameField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    nameField.setVisible(true);
                    nameField.requestFocus();
                    outputLabel.setText(nameField.getText() + " is not valid name");
                    emailField.setEditable(false);
                    departmentField.setEditable(false);
                    majorField.setEditable(false);
                    passwordField.setEditable(false);
                    flag = true;
                }

            }
        });

        /**
         * this lambda expression does not allow the user to advance to the next textfield if there is an error in their input
         * the flag relates to the flag (if any), produced when the input is compared to the regex.
         */
        emailField.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                emailField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");

                flag = false;
            }

        });

        /**
         * this method adds a listener to the emailField element to validate the user input.
         *
         * Valid email:
         * 1. starts with a letter
         * 2. contains any mix of lowercase letters and digits 0-9
         * 3. must end with @farmingdale.edu
         */
        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Email text is focused");
            } else {
                if (emailField.getText().matches("[a-z][a-z0-9]{2,6}@farmingdale.edu")) {
                    departmentField.setEditable(true);
                    emailField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's email is invalid
                //update flag
                {
                    emailField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    emailField.setVisible(true);
                    emailField.requestFocus();
                    outputLabel.setText(emailField.getText() + " is not valid farmingdale email");
                    departmentField.setEditable(false);
                    majorField.setEditable(false);
                    passwordField.setEditable(false);
                    flag = true;
                }

            }
        });

        departmentField.setOnKeyPressed(event -> {

            if (event.getCode() != KeyCode.TAB && flag) {
                departmentField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");

                flag = false;
            }

        });
        departmentField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("Email text is focused");
            } else {
                if (departmentField.getText().toUpperCase().matches("[A-Z]{3}")) {
                    majorField.setEditable(true);
                    departmentField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's email is invalid
                //update flag
                {
                    departmentField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    departmentField.setVisible(true);
                    departmentField.requestFocus();
                    outputLabel.setText(departmentField.getText() + " is not valid farmingdale department");
                    majorField.setEditable(false);
                    passwordField.setEditable(false);
                    flag = true;
                }

            }
        });

        /**
         * this lambda expression does not allow the user to advance to the next birthday if there is an error in their input
         * the flag relates to the flag (if any), produced when the input is compared to the regex.
         */
        majorField.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.TAB && flag) {
                majorField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");

                flag = false;
            }
        });

        /**
         * this method adds a listener to the bdayField element to validate the user input.
         *
         * Valid birthday:
         * 1. user enters date in format MM/DD/YYYY
         * 2. user must be born between 1900-2004
         * 3. single digit months/days preceeded by 0;
         */
        majorField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("major field is focused");
            } else {
                //bday format
                if (majorField.getText().toUpperCase().matches("[A-Z]{3}")) {
                    passwordField.setEditable(true);
                    majorField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's bday is invalid
                //update flag
                {
                    majorField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    majorField.setVisible(true);
                    majorField.requestFocus();
                    outputLabel.setText(majorField.getText() + " is not valid major");
                    passwordField.setEditable(false);
                    flag = true;
                }
            }
        });


        /**
         * this lambda expression does not allow the user to advance to the next zipField if there is an error in their input
         * the flag relates to the flag (if any), produced when the input is compared to the regex.
         */
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.TAB && flag) {
                passwordField.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
                outputLabel.setText("");


                flag = false;
            }
        });

        /**
         * MUST READ:
         * this method adds a listener to the zipField element to validate the user input.
         * in order for the user to proceed to the next screen by tapping the button, all fields must be validated.
         * once the zip is validated, this method should add functionality to the button to switch between screens.
         *
         * Valid Zipcode:
         * 1. begins with "117"
         * 5 digits long (last twp digits are any digit b/w 0-9).
         */
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("password field is focused");
            } else {
                //zip format "117-XX"
                if (passwordField.getText().matches(("^(?=.*[A-Z])(?=.*\\d.*\\d)[A-Za-z\\d]{5,10}$"))) {
                    //passwordField.setEditable(true);
                   // passwordField.setBorder(null);
                    addButton.setDisable(false);
                    //passwordField.setBorder(null);
                } else
                //if there is an error with the users data, change the appearance of the box
                //show on the screen that the user's zip is invalid
                //update flag
                {
                    passwordField.setStyle("-fx-border-color: red ; -fx-border-width: 4px ;");
                    passwordField.setVisible(true);
                    passwordField.requestFocus();
                    outputLabel.setText(passwordField.getText() + " is not valid passsword");
                    passwordField.setEditable(true);
                    flag = true;
                }
            }
        });


        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String department = resultSet.getString("department");
                String major = resultSet.getString("major");
                String password = resultSet.getString("password");
                Person p = new Person(id, name, email, department, major, password);
                data.add(p);
            }

            preparedStatement.close();
            //conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tv_fscid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));
        tv_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        tv.setItems(data);
        //viewHandler = new App.ThemeHandler();
        sunsetModeOn = true;

        fscidField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());
        nameField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());
        departmentField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());
        majorField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> checkInformationValidity());


    }

    private void checkInformationValidity() {
        boolean isValid = validInformation(); // Your existing validation logic
        isInformationValid.set(isValid);
    }



    public boolean validInformation(){
        boolean isValid = true;
        if (!fscidField.getText().matches("[a-z][a-z0-9]{2,6}"))
            return false;
        if (!nameField.getText().matches("[A-Za-z]{2,12}[\\s][A-Za-z]{2,13}"))
            return false;
        if (!emailField.getText().matches("[a-z][a-z0-9]{2,6}@farmingdale.edu"))
            return false;
        if (!departmentField.getText().toUpperCase().matches("[A-Z]{3}"))
            return false;
        if (!majorField.getText().toUpperCase().matches("[A-Z]{3}"))
            return false;
        if (passwordField.getText().matches(("^(?=.*[A-Z])(?=.*\\d.*\\d)[A-Za-z\\d]{5,10}$")))
            return false;


            return isValid;
    }





    /*
            Trying to change the theme
            do i need to declare a gui/controller for each theme i want to show?
     */
        @FXML
        private void changeScene (ActionEvent event){
            MenuItem menuItem = (MenuItem) event.getSource(); // Get the MenuItem
            Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
            if (sunsetModeOn) {
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
        private void closeScreen (ActionEvent event){
            System.exit(1);
        }


        @FXML
        protected void addNewRecord () {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "INSERT INTO users (id, name, email, department, major, password) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, fscidField.getText());
                preparedStatement.setString(2, nameField.getText());
                preparedStatement.setString(3, emailField.getText());
                preparedStatement.setString(4, departmentField.getText());
                preparedStatement.setString(5, majorField.getText());
                preparedStatement.setString(6, passwordField.getText());
                Person p = new Person(fscidField.getText(), nameField.getText(), emailField.getText(), departmentField.getText(), majorField.getText(), passwordField.getText());
                //data.add(p);
                tv.getItems().add(p);
                int row = preparedStatement.executeUpdate();

                if (row > 0) {
                    System.out.println("A new user was inserted successfully.");
                }

                tv.refresh();
                preparedStatement.close();

                sql = "INSERT INTO passwords (id, password) VALUES (?, ?)";
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, fscidField.getText());
                preparedStatement.setString(2, passwordField.getText());
                preparedStatement.close();

                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        @FXML
        protected void clearForm () {
            fscidField.setText("");
            nameField.setText(""); //this works
            //last_name.clear(); //this works also
            emailField.setText("");
            departmentField.setText("");
            majorField.setText("");
            passwordField.setText("");
        }

    /*

    on table view we have onMouseClick linked ot selectedItemTC()
     */
        @FXML
        protected void selectedItemTV (MouseEvent mouseEvent){
            Person p = tv.getSelectionModel().getSelectedItem();
            if (p != null) {
                fscidField.setText(p.getId());
                nameField.setText(p.getName());
                emailField.setText(p.getEmail());
                departmentField.setText(p.getDepartment());
                majorField.setText(p.getMajor());
                passwordField.setText(p.getPassword());

                addButton.setDisable(false);
                clearButton.setDisable(false);
                deleteButton.setDisable(false);
                editButton.setDisable(false);

            }

        }


        @FXML
        protected void editRecord () {
            Person p = tv.getSelectionModel().getSelectedItem();
            //selection model returns list of colums as rows in a table view
            //selected item refers to an individual instance in that  tableview (a specific row)
            //in this case, selection model is person

            int c = data.indexOf(p);
            Person p2 = new Person();
            p2.setId(fscidField.getText());
            p2.setName(nameField.getText());
            p2.setEmail(emailField.getText());
            p2.setMajor(majorField.getText());
            p2.setPassword(passwordField.getText());

            data.remove(c);
            data.add(c, p2);
            tv.getSelectionModel().select(c);
        }
        @FXML
        protected void deleteRecord () {
            Person p = tv.getSelectionModel().getSelectedItem();


            //data.remove(p);
            if (p != null) {
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String sql = "DELETE FROM users WHERE id= \'" + p.getId() + "\'";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.executeUpdate(sql);
                    int row = preparedStatement.executeUpdate();
                    tv.getItems().remove(p);
                    tv.refresh();
                    preparedStatement.close();

                    sql = "DELETE FROM passwords WHERE id= \'" + p.getId() + "\'";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.executeUpdate(sql);
                    preparedStatement.close();

                    conn.close();
                    fscidField.setText("");
                    nameField.setText("");
                    emailField.setText("");
                    departmentField.setText("");
                    majorField.setText("");
                    passwordField.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // tv.getItems().remove(p);
            }
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
        public void jaja (ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createAccountScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 850, 560);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        @FXML
        public void hehe (ActionEvent event) throws IOException {
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
        protected void showImage () {
            File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
            if (file != null) {
                img_view.setImage(new Image(file.toURI().toString()));
            }
        }
    }