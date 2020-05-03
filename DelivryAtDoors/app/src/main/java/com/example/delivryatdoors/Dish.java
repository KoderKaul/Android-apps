package com.example.delivryatdoors;

import android.os.Parcel;
import android.os.Parcelable;

public class Dish implements Parcelable {
    private String dish_name;
    private String dish_type;
    private int price;

    public Dish(String dish_name, String dish_type, int price) {
        this.dish_name = dish_name;
        this.dish_type = dish_type;
        this.price = price;
    }

    protected Dish(Parcel in) {
        dish_name = in.readString();
        dish_type = in.readString();
        price = in.readInt();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_type() {
        return dish_type;
    }

    public void setDish_type(String dish_type) {
        this.dish_type = dish_type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dish_name);
        dest.writeString(dish_type);
        dest.writeInt(price);
    }
}
