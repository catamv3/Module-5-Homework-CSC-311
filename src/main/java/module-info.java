module com.mycompany.javafx_db_example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens com.mycompany.javafx_db_example to javafx.fxml;
    exports com.mycompany.javafx_db_example;
    exports com.mycompany.javafx_db_example.db;
    opens com.mycompany.javafx_db_example.db to javafx.fxml;
}
