package com.karan.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    ImageView hpy,sad,neut;
    EditText name,contact,email,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        hpy=findViewById(R.id.IvHpy);
        sad=findViewById(R.id.IvSad);
        neut=findViewById(R.id.IvNeu);
        name=findViewById(R.id.etName);
        contact=findViewById(R.id.etPhone);
        email=findViewById(R.id.etMail);
        address=findViewById(R.id.etAddress);

        hpy.setOnClickListener(this);
        sad.setOnClickListener(this);
        neut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            Intent intent= new Intent();
            intent.putExtra("NAME", name.getText().toString().trim());
            intent.putExtra("EMAIL", email.getText().toString().trim());
            intent.putExtra("ADDRESS", address.getText().toString().trim());
            intent.putExtra("CONTACT", contact.getText().toString().trim());
            if(v.getId()==R.id.IvHpy){
                intent.putExtra("MOOD", "happy");
            }
            else if(v.getId()==R.id.IvSad){
                intent.putExtra("MOOD","sad");
            }
            else
                intent.putExtra("MOOD","neutral");
            setResult(RESULT_OK,intent);
            Toast.makeText(Activity2.this,name.getText().toString()+" entered",Toast.LENGTH_LONG).show();
            Activity2.this.finish();
        }

    }

