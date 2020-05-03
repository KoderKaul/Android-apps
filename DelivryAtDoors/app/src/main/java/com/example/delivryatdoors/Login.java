package com.example.delivryatdoors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    EditText etMail,etPass;
    Button facebook,google,btn;
    TextView tvGotoCreateAccount;
    String mail;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClint;
    private int GOOGLE_SIGN_IN = 1;
    private static final String TAG = "LOGIN";
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mFirebaseAuth=FirebaseAuth.getInstance();
        facebook = findViewById(R.id.cdFacebook);
        google = findViewById(R.id.cdGoogle);
        tvGotoCreateAccount = findViewById(R.id.tvGotoCreateAccount);
        etMail=findViewById(R.id.eTMail);
        etPass=findViewById(R.id.eTPassword);
        btn =findViewById(R.id.btnLogout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth=FirebaseAuth.getInstance();
                mail=etMail.getText().toString();
                if(mail.isEmpty()==false && etPass.getText().toString().isEmpty()==false) {
                    mFirebaseAuth.signInWithEmailAndPassword(etMail.getText().toString().trim(), etPass.getText().toString().trim())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.e(TAG, "createUserwithEmail:success");
                                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                        if(mail.trim().substring(mail.indexOf("@")).equals("@foodie.com")){
                                            startActivity(new Intent(Login.this, Rider_Activity.class));
                                        }else
                                            updateUI(user);
                                    }else {
                                        // If sign in fails, display a message to the user.
                                        Log.e(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(Login.this, "Enter all fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.tvForget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etMail.getText().toString().trim().isEmpty()) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(etMail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                        Toast.makeText(Login.this, "Not a Registered Email!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else
                    Toast.makeText(Login.this, "Please enter an Email Address first", Toast.LENGTH_SHORT).show();
            }
        });

        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClint = GoogleSignIn.getClient(this,gso);

        mCallbackManager = CallbackManager.Factory.create();


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    LoginManager.getInstance().logInWithReadPermissions(Login.this,Arrays.asList("email", "public_profile"));
                    LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            Log.d(TAG, "facebook:onSuccess:" + loginResult);
                            handleFacebookAccessToken(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            Log.d(TAG, "facebook:onCancel");
                            // ...
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Log.d(TAG, "facebook:onError", error);
                            // ...
                        }
                    });
            }
        });


        tvGotoCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Login.this,
                        sign_up_activity.class);
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.e("Error","Button Clicked");
                    signInGoogle();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signInGoogle(){
        Log.e("Error","Came in Sign In");
        Intent signInIntent = mGoogleSignInClint.getSignInIntent();
        startActivityForResult(signInIntent,GOOGLE_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode == GOOGLE_SIGN_IN){
                Log.e("Error","Activity Result RC_SIGN_IN");
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResults(task);

        }
           else{
                super.onActivityResult(requestCode, resultCode, data);
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
    }

    private void handleSignInResults(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            FirebaseAuthWithGoogle(account);
        }
        catch (ApiException e){
            Log.e(TAG, "Google sign in failed",e);
        }

    }

    private void FirebaseAuthWithGoogle(GoogleSignInAccount account){

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        Log.e("Error", "firebaseAuthWithGoogle:" + account.getId());

        mFirebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG,"signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        }

                        else{
                            Log.e(TAG,"signInWithCredential:failure",task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    public void  updateUI(FirebaseUser account){
        if(account != null){
            Log.e("Error","Came in Update UI");
            startActivity(new Intent(Login.this,MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
