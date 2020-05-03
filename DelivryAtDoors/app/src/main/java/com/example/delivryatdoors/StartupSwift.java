package com.example.delivryatdoors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class StartupSwift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_swift);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser() == null){
                    startActivity(new Intent(StartupSwift.this,Login.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else {
                    Log.e("Fb",FirebaseAuth.getInstance().getCurrentUser().getMetadata().toString());
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().length()!=0) {
                        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().trim().substring(FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@")).equals("@foodie.com")) {
                            startActivity(new Intent(StartupSwift.this, Rider_Activity.class));
                        }else
                            startActivity(new Intent(StartupSwift.this, MainActivity.class));
                    }else
                        startActivity(new Intent(StartupSwift.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }, 2000);
    }
}
