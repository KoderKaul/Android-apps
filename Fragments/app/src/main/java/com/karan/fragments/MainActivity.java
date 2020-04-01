package com.karan.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListFrag.ItemSelected {//Alt enter to implement ItemSelected Methods
    TextView TvDescription;
    //ArrayList<String> description;
    String [] description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TvDescription=findViewById(R.id.TvDescription);// Fragments ke components can be initialized in main

        /*description= new ArrayList<>();
        description.add("Description of item 1");
        description.add("Description of item 2");
        description.add("Description of item 3"); */
        description= getResources().getStringArray(R.array.descriptions);
        // means phone in portrait mode
        if(findViewById(R.id.layout_portrait)!=null){
            FragmentManager manager =this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.DetailFrag)) // potrait me at a time sirf ek hi show hoga
                    .show(manager.findFragmentById(R.id.ListFrag))
                    .commit();
        }
        //means landscape mode
        if(findViewById(R.id.layout_land)!=null){
            FragmentManager manager =this.getSupportFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.DetailFrag)) // potrait me at a time sirf ek hi show hoga
                    .show(manager.findFragmentById(R.id.ListFrag))
                    .commit();
        }
    }

    @Override
    public void onItemSelected(int index) {
        // for array list   TvDescription.setText(description.get(index));
        TvDescription.setText(description[index]);
        if(findViewById(R.id.layout_portrait)!=null){
            FragmentManager manager =this.getSupportFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.DetailFrag)) // portrait me at a time sirf ek hi show hoga
                    .hide(manager.findFragmentById(R.id.ListFrag))
                    .addToBackStack(null)// back dabane pe exit nai hoga app se
                    .commit();
        }
    }
}
