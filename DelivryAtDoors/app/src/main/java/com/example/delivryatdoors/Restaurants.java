package com.example.delivryatdoors;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurants implements Parcelable {
    private String restaurantName,speciality_first,address,description;
    private int cost_for_Two;
    private double rating;

    protected Restaurants(Parcel in) {
        restaurantName = in.readString();
        speciality_first = in.readString();
        address = in.readString();
        description = in.readString();
        cost_for_Two = in.readInt();
        rating = in.readDouble();
    }

    public static final Creator<Restaurants> CREATOR = new Creator<Restaurants>() {
        @Override
        public Restaurants createFromParcel(Parcel in) {
            return new Restaurants(in);
        }

        @Override
        public Restaurants[] newArray(int size) {
            return new Restaurants[size];
        }
    };

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getSpeciality_first() {
        return speciality_first;
    }

    public void setSpeciality_first(String speciality_first) {
        this.speciality_first = speciality_first;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost_for_Two() {
        return cost_for_Two;
    }

    public void setCost_for_Two(int cost_for_Two) {
        this.cost_for_Two = cost_for_Two;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Restaurants(String restaurantName, String speciality_first, String address, String description, int cost_for_Two, double rating) {
        this.restaurantName = restaurantName;
        this.speciality_first = speciality_first;
        this.address = address;
        this.description = description;
        this.cost_for_Two = cost_for_Two;
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurantName);
        dest.writeString(speciality_first);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeInt(cost_for_Two);
        dest.writeDouble(rating);
    }
}
