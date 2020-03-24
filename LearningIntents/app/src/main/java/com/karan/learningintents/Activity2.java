package com.karan.learningintents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        txt=findViewById(R.id.txtWelcome);
        String name= getIntent().getStringExtra("NAME");// NAME is the key that helps to locate it
                            // If no input given sends a NULL value

        txt.setText(name+", Welcome to Activity 2");
    }
}
