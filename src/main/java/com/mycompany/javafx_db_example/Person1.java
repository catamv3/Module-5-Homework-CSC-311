package com.mycompany.javafx_db_example;

public class Person1 {
        private Integer id;
        private String firstName;
        private String lastName;
        private String email;

        private String address;
        private String phone;

        public Person1() {
        }


        public Person1(Integer id, String firstName, String lastName, String email, String address, String phone) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.address = address;
            this.phone = phone;
        }

        // fix the getters and setters to match the fields

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


        public String getFirstName() {
            return firstName;
        }

    /**
     * This method is a setter method for the name field. Accepts a string argument,
     * sets this Person1 name to firstName
     * @param firstName
     */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
/*
to gen javadoc > tools > generate java doc >
 */

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }


