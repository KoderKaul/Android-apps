package com.example.delivryatdoors;

import android.os.Parcel;
import android.os.Parcelable;

public class PendingOrders implements Parcelable {
    String orderId;
    String userName;
    String number;
    String userAddress;
    String restAddress;
    String restName;
    String money;

    public PendingOrders() {
    }

    public PendingOrders(String orderId, String userName, String number, String userAddress, String restName,String restAddress,  String money) {
        this.orderId = orderId;
        this.userName = userName;
        this.number = number;
        this.userAddress = userAddress;
        this.restAddress = restAddress;
        this.restName = restName;
        this.money = money;
    }

    protected PendingOrders(Parcel in) {
        orderId = in.readString();
        userName = in.readString();
        number = in.readString();
        userAddress = in.readString();
        restAddress = in.readString();
        restName = in.readString();
        money = in.readString();
    }

    public static final Creator<PendingOrders> CREATOR = new Creator<PendingOrders>() {
        @Override
        public PendingOrders createFromParcel(Parcel in) {
            return new PendingOrders(in);
        }

        @Override
        public PendingOrders[] newArray(int size) {
            return new PendingOrders[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
    }

    public String getNumber() {
        return number;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public String getrestName() {
        return restName;
    }

    public String getMoney() {
        return money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(userName);
        dest.writeString(number);
        dest.writeString(userAddress);
        dest.writeString(restAddress);
        dest.writeString(restName);
        dest.writeString(money);
    }
}
