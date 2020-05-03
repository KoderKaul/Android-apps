package com.example.delivryatdoors;

public class User {
    String name;
    String number;
    String email;
    String userId;
    String address;
    String birthday;
    public User() {
    }

    public User(String name, String email, String number, String birthday, String address, String userId) {
        this.address = address;
        this.name = name;
        this.number = number;
        this.email = email;
        this.birthday=birthday;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }
}
