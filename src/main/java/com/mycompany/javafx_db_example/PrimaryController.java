package com.mycompany.javafx_db_example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*

 <stylesheets>
        <URL value="@../../../../../../styling/style.css" />
        <URL value="@../../../../../styling/style.css" />
    </stylesheets>

 */

public class PrimaryController implements Initializable {

        @FXML
        TextField first_name, last_name, department, major;
        @FXML
        private TableView<Person> tv;
        @FXML
        private TableColumn<Person, Integer> tv_id;
        @FXML
        private TableColumn<Person, String> tv_fn, tv_ln, tv_dept, tv_major;

/*



 */
        @FXML
        private MenuItem closeItem;

        @FXML
        private ImageView img_view;


        public final ObservableList<Person> data =
            FXCollections.observableArrayList(
                    new Person(1, "Michael", "Catalanotti", "CPIS", "CS"),
                    new Person(2, "Albert", "Einstein", "LAS", "Physics")

            );
 /*       */
        /**
         * SO:
         * a table view uses an observable list to display data.
         * an observable list can store any data type/class, we just have to declare it in a class file.
         * the initialize method below links the observable list to the table view.
         *      in doing the initialize() method, now any time, anything in the list changes
         *      the table view will automatically update.
         * @param url
         * @param resourceBundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            tv = new TableView<Person>();
            tv_id = new TableColumn<Person, Integer>();
            tv_fn = new TableColumn<Person, String>();
            tv_ln = new TableColumn<Person, String>();
            tv_dept = new TableColumn<Person, String>();
            tv_major = new TableColumn<Person, String>();


            tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tv_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
            tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));


            tv.setItems(data);
        }


        @FXML
        protected void addNewRecord() {

            data.add(new Person(
                    data.size()+1,
                    first_name.getText(),
                    last_name.getText(),
                    department.getText(),
                    major.getText()
            ));
        }

        @FXML
        protected void clearForm(){
            first_name.setText(""); //this works
            last_name.clear(); //this works also
            department.setText("");
            major.setText("");
        }

        /**
         * to select a row, you are accessing something on the observable list
         */

    /*

    on table view we have onMouseClick linked ot selectedItemTC()
     */
        @FXML
        protected void selectedItemTV(MouseEvent mouseEvent){
            Person p = tv.getSelectionModel().getSelectedItem();
            first_name.setText(p.getFirstName());
            last_name.setText(p.getLastName());
            department.setText(p.getDept());
            major.setText(p.getMajor());
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
            p2.setDept(department.getText());
            p2.setMajor(major.getText());

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