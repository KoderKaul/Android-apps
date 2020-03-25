package com.karan.contactsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView ISmile,ICall,Imail,IMap;
    TextView textView;
    Button btn;
    final int ACT=3;
    String contact,name,address,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ISmile=findViewById(R.id.IvSmile);
        ICall=findViewById(R.id.IvCall);
        Imail=findViewById(R.id.Iv3);
        IMap=findViewById(R.id.Iv4);
        textView=findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        ISmile.setVisibility(View.GONE);
        ICall.setVisibility(View.GONE);
        Imail.setVisibility(View.GONE);
        IMap.setVisibility(View.GONE);

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,
                        Activity2.class);
                startActivityForResult(intent,ACT);
            }
        });
        ICall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+contact));
                startActivity(intent1);
            }
        });
        Imail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+email));
                startActivity(intent1);
            }
        });
        IMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Intent.ACTION_VIEW,Uri.parse("geo:?q="+address));
                startActivity(intent1);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                ISmile.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                contact= data.getStringExtra("CONTACT");

                if(contact.isEmpty()==false)
                    ICall.setVisibility(View.VISIBLE);

                name=data.getStringExtra("NAME");
                email=data.getStringExtra("EMAIL");
                if(email.isEmpty()==false)
                    Imail.setVisibility(View.VISIBLE);
                address=data.getStringExtra("ADDRESS");
                if (address.isEmpty()==false)
                    IMap.setVisibility(View.VISIBLE);

                if(data.getStringExtra("MOOD").equals("sad"))
                    ISmile.setImageResource(R.drawable.sad);
                else if(data.getStringExtra("MOOD").equals("neutral"))
                    ISmile.setImageResource(R.drawable.neutral);
            }


    }

}
