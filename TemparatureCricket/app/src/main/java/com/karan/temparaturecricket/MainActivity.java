package com.karan.temparaturecricket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText temp;
    Button btn;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp=findViewById(R.id.tempID);
        btn=findViewById(R.id.btnCalc);
        txt=findViewById(R.id.txtResult);
        txt.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int crics=Integer.parseInt((temp.getText().toString()));
                crics= 4+crics/3;
                txt.setText("Temparature=" + crics);
                txt.setVisibility(View.VISIBLE);
            }
        });
    }
}
