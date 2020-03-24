package com.karan.temparaturecricket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText temp;
    Button btn;
    TextView txt;
    DecimalFormat dd=new DecimalFormat("#0.00");
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
                if (temp.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this,"Please enter a value",Toast.LENGTH_SHORT).show();
                }
                else{
                    double crics = Double.parseDouble((temp.getText().toString()));
                    crics = 4 + crics / 3.0;
                    txt.setText("Temparature=" + dd.format(crics));
                    txt.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
