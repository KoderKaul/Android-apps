package com.karan.learningintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {
    EditText Surname;
    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        btn1=findViewById(R.id.btnSubmit);
        Surname=findViewById(R.id.etSurname);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Surname.getText().toString().isEmpty())
                    Toast.makeText(Activity3.this, "Please enter your Surname", Toast.LENGTH_SHORT).show();
                else {
                    String sur= Surname.getText().toString().trim();
                    Intent mainInt= new Intent();// Dont need to create any specific intent  // just for the sake of features
                    mainInt.putExtra("SURNAME",sur);
                    setResult(RESULT_OK,mainInt);
                    Activity3.this.finish();
                }
            }
        });
    }
}
