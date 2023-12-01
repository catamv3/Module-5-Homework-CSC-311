package com.mycompany.javafx_db_example.model;

public class Person {
    private String id;
    private String name;
    private String email;
    private String major;
    private String department;
    private String password;

    public Person() {
    }


    public Person(String id, String name, String email, String dept,String major, String password) {
        this.id = id;
        this.name= name;
        this.email = email;
        this.department= dept;
        this.major = major;
        this.password = password;
    }

    public Person(String id, String pass){
        id=id;
        name = "";
        email = "";
        department = "";
        major = "";
        password = pass;
    }

    // fix the getters and setters to match the fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }


    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
