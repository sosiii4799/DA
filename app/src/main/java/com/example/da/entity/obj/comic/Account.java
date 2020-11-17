package com.example.da.entity.obj.comic;

import java.io.Serializable;

public class Account implements Serializable {
    private String idAccount;
    private String username;
    private String password;
    private String lastName;
    private  String name;
    private String phone;
    private String address;
    private int gender;
    private String email;
    private String DateOfBirth;
    private String avatar;

    public Account(String idAccount, String name, int gender, String dateOfBirth, String avatar) {
        this.idAccount = idAccount;
        this.name = name;
        this.gender = gender;
        DateOfBirth = dateOfBirth;
        this.avatar = avatar;
    }

    public Account() {
    }

    public Account(String idAccount, String name, String avatar) {
        this.idAccount = idAccount;
        this.name = name;
        this.avatar = avatar;
    }

    public Account(String idAccount, String username, String password, String lastName, String name, String phone, String address, int gender, String email, String dateOfBirth, String avatar) {
        this.idAccount = idAccount;
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.email = email;
        this.DateOfBirth = dateOfBirth;
        this.avatar = avatar;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
