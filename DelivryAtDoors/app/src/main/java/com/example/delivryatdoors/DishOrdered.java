package com.example.delivryatdoors;
public class DishOrdered {
    String userId;
    String dishName;
    String dishType;
    int dishPrice;
    String rstntName;

    public DishOrdered() {
    }

    public DishOrdered(String userId, String dishName, String dishType, int dishPrice, String rstntName) {
        this.userId = userId;
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishPrice = dishPrice;
        this.rstntName = rstntName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishType() {
        return dishType;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public String getRstntName() {
        return rstntName;
    }
}
