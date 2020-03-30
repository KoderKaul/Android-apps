package com.karan.recyclerfrags;

public class Person {
    private String name;
    private String tele;

    public Person(String name, String tele) {
        this.name = name;
        this.tele = tele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String gettele() {
        return tele;
    }

    public void settele(String tele) {
        this.tele = tele;
    }
}
