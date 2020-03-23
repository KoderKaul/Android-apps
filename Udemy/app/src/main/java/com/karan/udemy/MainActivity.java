package com.karan.udemy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etIDJ;
    Button btnSubmitJ;
    TextView txtResultJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIDJ= findViewById(R.id.etIDX);
        btnSubmitJ=findViewById(R.id.btnSubmitX);
        txtResultJ=findViewById(R.id.txtResultX);

        txtResultJ.setVisibility(500);

        btnSubmitJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idNumber= etIDJ.getText().toString().trim();
                if(idNumber.length()==13) {
                    String dob = idNumber.substring(0, 6);
                    int gender = Integer.parseInt(Character.toString(idNumber.charAt(6)));
                    String sGender;
                    if (gender < 5)
                        sGender = "Female";
                    else
                        sGender = "Male";
                    int nationality = Integer.parseInt(Character.toString(idNumber.charAt(10)));
                    String sNationality;
                    if (nationality == 0)
                        sNationality = "SA Citizen";
                    else
                        sNationality = "Permanant Resident";
                    String setText = ("Date of Birth: " + dob.substring(4) + "/" + dob.substring(2, 4) + "/" + dob.substring(0, 2) + "\n" +
                            "Gender: " + sGender + "\n" +
                            "Nationality: " + sNationality);
                    txtResultJ.setText(setText);
                    txtResultJ.setVisibility(View.VISIBLE);

                }
                else{
                    txtResultJ.setText("Error Try Again!");
                    txtResultJ.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
