package com.karan.learningintents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int ACTIVITY3 = 3;
    EditText name;
    Button btn1;
    Button btn2;
    TextView txtR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtR = findViewById(R.id.txtResult);
        name = findViewById(R.id.etName);
        btn1 = findViewById(R.id.btnAct2);
        btn2 = findViewById(R.id.btnAct3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please provide an Input", Toast.LENGTH_SHORT).show();
                else {
                    String s = name.getText().toString().trim();
                    Intent intent = new Intent(MainActivity.this,
                            com.karan.learningintents.Activity2.class);
                    intent.putExtra("NAME", s);
                    startActivity(intent);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,
                        com.karan.learningintents.Activity3.class);
                startActivityForResult(intent2, ACTIVITY3);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ACTIVITY3) {
            if(resultCode==RESULT_OK) {
                String Surname = data.getStringExtra("SURNAME");
                txtR.setText(Surname);
            }
            if(resultCode==RESULT_CANCELED)
                txtR.setText("No data Received");
        }
    }

}

