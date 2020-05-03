package com.example.delivryatdoors;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends Fragment {

    private CardView btnLogout,btnUpdate;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView name, emailId;
    private DatabaseReference databaseReference;
    private User yo;
    private CircleImageView editPhoto,profilePhoto;
    private Uri imageUri;
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String naMe="",mail="";
    private String myUrl;
    private  int PICK_IMAGE_REQUEST =1;
    TextView Name,Email_Address;
    private EditText Phone_Number,Birthday,Address;
    private String checker = "";
    private TextView update;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btnLogout = view.findViewById(R.id.btnLogout);
        name = view.findViewById(R.id.nameyo);
        emailId = view.findViewById(R.id.emailyo);
        profilePhoto = view.findViewById(R.id.profilephoto);
        editPhoto = view.findViewById(R.id.editphoto);
        update = view.findViewById(R.id.update_info);

        Name = view.findViewById(R.id.actfrag_name);
        Email_Address = view.findViewById(R.id.actfrag_email);
        Phone_Number = view.findViewById(R.id.actfrag_phone);
        Birthday = view.findViewById(R.id.actfrag_bday);
        Address = view.findViewById(R.id.actfrag_address);
        userInfoDisplay();
        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(getActivity(),gso);
                googleSignInClient.signOut();
                startActivity(new Intent(getActivity(),Login.class));
            }
        });
        FirebaseUser userF= FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getContext());
        if(account!=null){
            mail= account.getEmail();
            naMe=account.getGivenName();
            ((TextView)view.findViewById(R.id.nameyo)).setText(naMe);
            Name.setText(naMe);
            ((TextView)view.findViewById(R.id.emailyo)).setText(mail);
            Email_Address.setText(mail);
        }else {
            mail= userF.getEmail();
            naMe=userF.getDisplayName();
            if( naMe == null||naMe.equals("")){
                Log.e("FB","fbcallingbabe");
                fb();
            }
            if(mail==null||mail.equals("")){
                Log.e("FB","fbcallingbabe2");
                fb();
            }

            ((TextView)view.findViewById(R.id.nameyo)).setText(naMe);
            Name.setText(naMe);
            ((TextView)view.findViewById(R.id.emailyo)).setText(mail);
            Email_Address.setText(mail);
        }



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    userInfoSaved();
                }
                else{
                    updateOnlyUserInfo();
                }
            }
        });

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(getContext(),AccountFragment.this);
            }
        });
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pics").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("pic","mili+ "+uri.toString());
                Glide.with(getActivity()/* context */)
                        .load(uri)
                        .into(profilePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
// ImageView in your Activity
       // ImageView imageView = view.findViewById(R.id.profilephoto);

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)


        return view;
    }


    private void fb() {
        Log.e("uhsu", "sidhif");
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        Log.v("LoginActivity Response ", response.toString());

                        try {
                            if(object!=null){
                            naMe = object.getString("name");
                            mail = object.getString("email");
                            ((TextView) getView().findViewById(R.id.nameyo)).setText(naMe);
                            Name.setText(naMe);
                            ((TextView) getView().findViewById(R.id.emailyo)).setText(mail);
                            Email_Address.setText(mail);
                        }} catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("IMage","t");
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!= null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            Log.e("IMage","t"+imageUri.toString());
            profilePhoto.setImageURI(imageUri);

        }
        else{
            Toast.makeText(getContext(),"Error, Try Again",Toast.LENGTH_SHORT).show();
        }

    }

    private void updateOnlyUserInfo() {
        User users = new User(Name.getText().toString().trim(), Email_Address.getText().toString().trim(),
                Phone_Number.getText().toString().trim(), Birthday.getText().toString().trim(), Address.getText().toString().trim(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(getContext(), "Data Successfully Updated", Toast.LENGTH_SHORT).show();
                System.out.println("Value was set. Error = " + databaseError);
            }
        });
    }

    private void userInfoSaved() {
        if(TextUtils.isEmpty(Name.getText().toString())){
            Toast.makeText(getContext(),"Name is mandatory",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(Email_Address.getText().toString())){
            Toast.makeText(getContext(),"Email Address is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone_Number.getText().toString())){
            Toast.makeText(getContext(),"Phone Number is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Birthday.getText().toString())){
            Toast.makeText(getContext(),"Birhtday is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Address.getText().toString())){
            Toast.makeText(getContext(),"Address is mandatory",Toast.LENGTH_SHORT).show();
        }
        
        else if(checker.equals("clicked")){
            uploadImage();
            updateOnlyUserInfo();
        }
    }

    private void uploadImage() {

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile Pics").child(currentuser.getUid());
        databaseReference= FirebaseDatabase.getInstance().getReference("users").child(currentuser.getUid());
        if(imageUri != null){

            final StorageReference fileRef = storageProfilePictureRef
                    .child(currentuser.getUid()+".jpg");

            uploadTask = fileRef.putFile(imageUri);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
    }

    private void userInfoDisplay() {

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users").child(currentuser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String number = dataSnapshot.child("number").getValue().toString();
                    String birthday = (dataSnapshot.child("birthday").getValue()).toString();
                    String address = (dataSnapshot.child("address").getValue()).toString();
//                    String image = dataSnapshot.child("image").getValue().toString();
                    Phone_Number.setText(number);
                    Birthday.setText(birthday);
                    Address.setText(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
