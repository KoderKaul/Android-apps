package com.karan.recyclerchallenge;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<Details> buyer;
//Remember to add this in MAnifest file
    @Override
    public void onCreate() {
        super.onCreate();
        buyer=new ArrayList<>();
        buyer.add(new Details("Karan","9018585703","Mercedes","E200"));
        buyer.add(new Details("Pranay","9852685545","Volkswagen","Polo"));
        buyer.add(new Details("Prasoon","90185568503","Nissan","Almera"));
        buyer.add(new Details("Saransh","6007589003","Mercedes","E180"));
        buyer.add(new Details("Ishank","7285946578","Volkswagen","Vento"));

    }
}
