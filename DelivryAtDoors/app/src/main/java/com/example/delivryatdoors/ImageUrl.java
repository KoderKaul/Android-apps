package com.example.delivryatdoors;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageUrl implements Parcelable {
    String imageUrl;

    public ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected ImageUrl(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<ImageUrl> CREATOR = new Creator<ImageUrl>() {
        @Override
        public ImageUrl createFromParcel(Parcel in) {
            return new ImageUrl(in);
        }

        @Override
        public ImageUrl[] newArray(int size) {
            return new ImageUrl[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
