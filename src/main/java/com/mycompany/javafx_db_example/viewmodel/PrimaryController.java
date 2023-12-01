package com.mycompany.javafx_db_example.viewmodel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import com.mycompany.javafx_db_example.model.Person;
import com.mysql.cj.protocol.a.DebugBufferingPacketReader;
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
    TextField id, name, email, department, major, password;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, String> tv_fscid, tv_name, tv_email,tv_department,  tv_major;
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
    private static MenuItem themeSwitcher;
    App.ThemeHandler viewHandler;

    private ObservableList<Person> data =
            FXCollections.observableArrayList();

    //declare a controler CONSTRUCTOR that accpts a stage
    //declare a controller that accepts an observable lsit and a stage

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                Person p = new Person(id,name,email, department, major,password);
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
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        String sql = "INSERT INTO users (id, name, email, department, major, password) VALUES (?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = conn.prepareStatement(sql);
                        preparedStatement.setString(1, id.getText());
                        preparedStatement.setString(2, name.getText());
                        preparedStatement.setString(3, email.getText());
                        preparedStatement.setString(4, department.getText());
                        preparedStatement.setString(5, major.getText());
                        preparedStatement.setString(6, password.getText());
                        Person p = new Person(id.getText(), name.getText(), email.getText(), department.getText(), major.getText(), password.getText());
                        //data.add(p);
                        tv.getItems().add(p);
                        int row = preparedStatement.executeUpdate();

                        if (row > 0) {
                            System.out.println("A new user was inserted successfully.");
                        }

                        tv.refresh();
                        preparedStatement.close();
                        conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }




    @FXML
    protected void clearForm(){
        id.setText("");
        name.setText(""); //this works
        //last_name.clear(); //this works also
        email.setText("");
        department.setText("");
        major.setText("");
        password.setText("");
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
            department.setText(p.getDepartment());
            major.setText(p.getMajor());
            password.setText(p.getPassword());
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
            p2.setMajor(major.getText());
            p2.setPassword(password.getText());

            data.remove(c);
            data.add(c,p2);
            tv.getSelectionModel().select(c);
        }
        @FXML
        protected void deleteRecord(){
            Person p = tv.getSelectionModel().getSelectedItem();


            //data.remove(p);
            if(p!= null){
                try {
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    String sql = "DELETE FROM users WHERE id= \'" + p.getId() + "\'";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.executeUpdate(sql);
                    int row = preparedStatement.executeUpdate();
                    tv.getItems().remove(p);
                    tv.refresh();
                    preparedStatement.close();
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                id.setText("");
                name.setText("");
                email.setText("");
                department.setText("");
                major.setText("");
                password.setText("");
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