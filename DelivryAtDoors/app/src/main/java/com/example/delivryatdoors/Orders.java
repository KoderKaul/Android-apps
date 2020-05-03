package com.example.delivryatdoors;

import android.os.Parcel;
import android.os.Parcelable;

public class Orders implements Parcelable {
    private String order_name;
    private String order_type;
    private int order_price;
    private int order_quantity;

    public Orders(String order_name, String order_type, int order_price, int order_quantity) {
        this.order_name = order_name;
        this.order_type = order_type;
        this.order_price = order_price;
        this.order_quantity = order_quantity;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    protected Orders(Parcel in) {
        order_name = in.readString();
        order_type = in.readString();
        order_price = in.readInt();
        order_quantity = in.readInt();
    }

    public static final Creator<Orders> CREATOR = new Creator<Orders>() {
        @Override
        public Orders createFromParcel(Parcel in) {
            return new Orders(in);
        }

        @Override
        public Orders[] newArray(int size) {
            return new Orders[size];
        }
    };

    public String getOrder_name() {
        return order_name;
    }

    public String getOrder_type() {
        return order_type;
    }

    public int getOrder_price() {
        return order_price;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(order_name);
        dest.writeString(order_type);
        dest.writeInt(order_price);
        dest.writeInt(order_quantity);
    }
}
