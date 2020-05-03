package com.example.delivryatdoors;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up_activity extends AppCompatActivity {
    EditText etPass,etConfirm,etMail,etName,etMobile,etAddress;
    ImageView check;
    Button button,btnMap;
    DatabaseReference databaseUser;
    String mail,pass;
    RadioButton radio;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);
        button=findViewById(R.id.button);
        check=findViewById(R.id.ivCheck);
        etConfirm=findViewById(R.id.etConfirm);
        etPass=findViewById(R.id.etPass);
        databaseUser= FirebaseDatabase.getInstance().getReference();// no path gives address of the root node of the JSON tree
        etAddress= findViewById(R.id.etAddress);
        etName=findViewById(R.id.etName);
        etMobile=findViewById(R.id.etMobile);
        etMail=findViewById(R.id.etMail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = etMail.getText().toString().trim();
                pass = etPass.getText().toString().trim();
                if (!mail.isEmpty() && !pass.isEmpty() && !etName.getText().toString().trim().isEmpty() && !etAddress.getText().toString().trim().isEmpty() &&
                        !etMobile.getText().toString().trim().isEmpty()) {
                    Log.e("MainActivity.TAG", "createUserWithEmail:success1");
                    mAuth = FirebaseAuth.getInstance();
                    if (etPass.getText().toString().equals(etConfirm.getText().toString())) {
                        mAuth.createUserWithEmailAndPassword(mail, pass)
                                .addOnCompleteListener(sign_up_activity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.e("MainActivity.TAG", "createUserWithEmail:success2");
                                        if (task.isSuccessful()) {
                                            Toast.makeText(sign_up_activity.this, "User Created Successfully!",
                                                    Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            User users = new User(etName.getText().toString().trim(), mail, etMobile.getText().toString().trim(),"",etAddress.getText().toString().trim(), user.getUid());
                                            databaseUser.child("users").child(user.getUid()).setValue(users);
                                            startActivity(new Intent(sign_up_activity.this, MainActivity.class));
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.e("MainActivity.TAG",  task.getException().toString());
                                            if(task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."))
                                                Toast.makeText(sign_up_activity.this, "This email already exists",
                                                        Toast.LENGTH_SHORT).show();
                                            else if(task.getException().toString().equals("com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]"))
                                                Toast.makeText(sign_up_activity.this, "Password should be at least 6 characters",
                                                        Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(sign_up_activity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else
                        Toast.makeText(sign_up_activity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(sign_up_activity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
            }
        });
        check.setVisibility(View.GONE);
        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etPass.getText().toString().isEmpty() || !etPass.getText().toString().isEmpty()) {
                    if (etPass.getText().toString().equals(etConfirm.getText().toString()))
                        check.setImageResource(R.drawable.ic_check_black_24dp);
                    else
                        check.setImageResource(R.drawable.wrong_24dp);
                    check.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
        etConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etPass.getText().toString().isEmpty() || !etPass.getText().toString().isEmpty()) {
                    if (etPass.getText().toString().equals(etConfirm.getText().toString())) {
                        check.setImageResource(R.drawable.ic_check_black_24dp);
                    }
                    else
                        check.setImageResource(R.drawable.wrong_24dp);
                    check.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });





    }
}
