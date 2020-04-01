package com.karan.recyclerchallenge;

public class Details {
    private String name;
    private String number;
    private String type;
    private String model;

    public Details(String name, String number, String type, String model) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }
}
