package com.karan.recyclerfrags;

import android.app.Application;

import java.util.ArrayList;

//first thing that starts when ur app opens and gets destroyed at the end also
public class ApplicationClass extends Application {
// to make it the base class GOTO manifest file and add the name of ths class
    public static ArrayList<Person> peep;
    @Override
    public void onCreate() {
        super.onCreate();
        peep= new ArrayList<>();//writing Person isnot necessary
        peep.add(new Person("Karan Kaul", "9018585703"));
        peep.add(new Person("M K Koul","9906398735"));
        peep.add(new Person("Anita Koul","9419285703"));
        peep.add(new Person("Home", "01912531899"));
    }
}
