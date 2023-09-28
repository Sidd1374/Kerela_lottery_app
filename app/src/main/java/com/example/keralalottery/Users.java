package com.example.keralalottery;
public class Users {
    String name;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String phNumber;

    public Users(String name, String email, String phNumber, String password) {
        this.name = name;
        this.email = email;
        this.phNumber = phNumber;
        this.password = password;
    }

    String password;
}
