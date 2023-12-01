package com.mycompany.javafx_db_example.viewmodel;

import com.mycompany.javafx_db_example.db.ConnDbOps;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;

import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static ConnDbOps cdbop;
    private static Stage primaryStage;




    public void start(Stage primaryStage) throws IOException {
        try {

            this.primaryStage = primaryStage;
            Parent root = FXMLLoader.load(getClass().getResource("splash_screen.fxml"));
            Scene splashScene = new Scene(root, 900, 515);

            primaryStage.setResizable(false);


            // Load the primary FXML file here
            Parent loginRoot = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));

            primaryStage.setScene(splashScene);
            primaryStage.show();
            changeScene(loginRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ThemeHandler{

        static boolean isSun = true;
        static Stage getStage(){return App.primaryStage;}
        static void setStage(Stage s){App.primaryStage=s;}
        static Scene getScene(){return App.scene;}
        static void setScene(Scene s){App.scene=s;}

        ThemeHandler(){
            primaryStage = App.primaryStage;
            scene = App.primaryStage.getScene();
        }

        static void switchTheme(ActionEvent e) {
            if (isSun) {
                isSun = !isSun;

                //Parent root = new Parent();
                Scene darkView = new Scene(scene.getRoot(), 900, 515);
                darkView.getStylesheets().clear();
                darkView.getStylesheets().add(ThemeHandler.class.getResource("moonTheme.css").toExternalForm());
                primaryStage.setScene(darkView);
                primaryStage.show();
            }
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    protected static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }




    private void changeScene(Parent newRoot) {
        Scene currentScene = primaryStage.getScene();
        Parent currentRoot = currentScene.getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> {
            Scene newScene = new Scene(newRoot, 900, 515);
            newScene.getStylesheets().add("sunset.css");

            primaryStage.setScene(newScene);
        });

        fadeOut.play();
    }



    public static void main(String[] args) {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);

        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ==============");
            System.out.println("| To start GUI,           press 'g' |");
            System.out.println("| To connect to DB,       press 'c' |");
            System.out.println("| To display all users,   press 'a' |");
            System.out.println("| To insert to the DB,    press 'i' |");
            System.out.println("| To delete a user,       press 'd' |");
            System.out.println("| To query by name,       press 'q' |");
            System.out.println("| To delete table,        press 'v' |");
            System.out.println("| To CLEAR table,         press 'w' |");
            System.out.println("| To edit a user,         press 'u' |");
            System.out.println("| To exit,                press 'e' |");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'g':
                    launch(args); // Start the GUI
                    break;

                case 'c':
                    cdbop.connectToDatabase();
                    break;
                case 'a':
                    cdbop.listAllUsers();
                    //System.out.println("fix me!");
                    break;

                case 'i':
                    scan.nextLine();
                    System.out.print("Enter Id: ");
                    String id = scan.nextLine();
                    System.out.print("Enter Name:");
                    String name = scan.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scan.next();
                    System.out.print("Enter Dept: ");
                    String dept = scan.next();
                    scan.nextLine();
                    System.out.print("Enter Major: ");
                    String major = scan.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scan.next();
                    cdbop.insertUser(id, name, email, dept, major, password);
                    break;
                case 'd':
                    scan.nextLine();
                    System.out.println("Enter the name of the user you want to delete");
                    String uname = scan.next();
                    cdbop.removeUser(uname);
                    break;

                case 'q':
                    scan.nextLine();
                    System.out.print("Enter the name to query: ");
                    String queryName = scan.nextLine();
                    cdbop.queryUserByName(queryName);
                    break;
                case 'e':
                    System.out.println("Exiting...");
                    System.exit(1);
                    break;
                case 'v':
                    System.out.println("DELETING THE ENTIRE table!");
                    cdbop.deleteTable();
                    break;
                case 'w':
                    System.out.println("Emptying contents of table");
                    cdbop.clearTable();
                    break;
                case 'u':
                    System.out.print("Enter your FSC ID: ");
                    scan.nextLine();
                    String fscID = scan.nextLine();
                    cdbop.editUser(fscID);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'e');

        scan.close();
    }
}