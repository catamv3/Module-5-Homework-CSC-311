package com.mycompany.javafx_db_example.db;

import java.sql.*;
import java.util.Scanner;
import com.mycompany.javafx_db_example.Person;

/**
 * This file stores information to establish a database connection.
 * @author MoaathAlrajab
 */
public class ConnDbOps {
    final String MYSQL_SERVER_URL = "jdbc:mysql://michaelcac311.mariadb.database.azure.com/";
    final String DB_URL = "jdbc:mysql://michaelcac311.mariadb.database.azure.com/csc311_class";
    final String USERNAME = "catamv3@michaelcac311";
    final String PASSWORD = "Michael01!";

    /**
     * This method establishes <b> A database connection!</b>
     *
     * @return <ol><li><i>false</i> if no registered users</li>
     * <li><i>True </i> otherwise</li></ol>
     */
    public boolean connectToDatabase() {
        boolean hasRegistredUsers = false;
        try {
            //First, connect to MYSQL server and create the database if not created
            Connection conn = DriverManager.getConnection(MYSQL_SERVER_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS csc311_class");
            statement.close();
            conn.close();

            //Second, connect to the database and create the table "users" if cot created
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            statement = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + "id VARCHAR(200) NOT NULL PRIMARY KEY," //not null has to enter something
                    + "name VARCHAR(200) NOT NULL,"
                    + "email VARCHAR(200) NOT NULL UNIQUE," //explain unique
                    + "phone VARCHAR(200),"
                    + "address VARCHAR(200),"
                    + "password VARCHAR(200) NOT NULL"
                    + ")";
            statement.executeUpdate(sql);

            //check if we have users in the table users
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }

            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegistredUsers;
    }


    /**
     * this method querys user info based on their <b>name</b>
     *
     * @param id- string arg for name.
     */
    public void queryUserByName(String id) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Address: " + address);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method lists all the users in the database.
     * <ol> <li> first it opens a connection</li>
     * <li>then executes a query statement</li>
     * <li> then returns the information requested</li>
     * <li>and finally closes the connection</li></ol>
     */

    public void listAllUsers() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                Person p = new Person(id,name,email,address,phone);
                System.out.println("ID: " + id + "\tName: " + name + "\tEmail: " + email + "\tPhone: " + phone + "\tAddress: " + address);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editUser(String id) {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter Name:");
            String name = scan.nextLine();
            System.out.print("Enter Email: ");
            String email = scan.next();
            System.out.print("Enter Phone: ");
            String phone = scan.next();
            scan.nextLine();
            System.out.print("Enter Address: ");
            String address = scan.nextLine();
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET name=?, email=?, phone=?, address=? WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, id);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("Your information was updated.");
            } else {
                System.out.println("No user found with the given id: " + id);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String id) {
//con to dta base an check if un/ pw is correct
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM users WHERE id= \'" + id + "\'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was removed successfully.");
            }

            preparedStatement.close();
            conn.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTable(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DROP TABLE users";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * <p>
     * this method inserts a user into the database
     * the <b><i>person</i></b> object requires :
     *     <ol>
     *         <li>a name</li>
     *         <li>an email</li>
     *         <li>an address</li>
     *         <li>a phone number</li>
     *         <li>a password</li>
     *
     *     </ol>
     * </p>
     *
     * @param name
     * @param email
     * @param phone
     * @param address
     * @param password
     */
    public void insertUser(String id, String name, String email, String phone, String address, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (id, name, email, phone, address, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, password);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                System.out.println("A new user was inserted successfully.");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
