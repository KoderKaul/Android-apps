package com.example.delivryatdoors;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rider_Activity extends AppCompatActivity implements MyRiderAdapter.onAcceptance,NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerview;
    private RecyclerView.Adapter myadapter;
    private RecyclerView.LayoutManager LayoutManager;
    DatabaseReference databaseReference;
    private ArrayList<PendingOrders> orders=new ArrayList<>();
    private ArrayList<PendingOrders> porders=new ArrayList<>();
    TextView tvDist;
    SeekBar seekBar;
    int radius=15;
    LocationManager locationManager;
    String provider;
    double lng,lat;
    Toolbar toolbars;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    public static String key="";
    OTPFrag otpFrag;
    public Rider_Activity(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riderrealshit);
        otpFrag=new OTPFrag();
        tvDist=findViewById(R.id.tvDist);
        seekBar=findViewById(R.id.seekBar);
        toolbars = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbars);

        fragmentManager=this.getSupportFragmentManager();
//        if(savedInstanceState==null){
//            fragmentManager.beginTransaction()
//                    .hide(fragmentManager.findFragmentById(R.id.OTPfrag))
//                    .commit();
//        }


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbars,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbars.setTitle(null);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.browser_actions_bg_grey));
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.browser_actions_bg_grey));
        navigationView.setNavigationItemSelectedListener(this);

        recyclerview=findViewById(R.id.recyclerCart);
        LayoutManager= new LinearLayoutManager(this);
        recyclerview.setLayoutManager(LayoutManager);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Rider_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Pending Orders");
            databaseReference.orderByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (orders.size() != 0) {
                        orders.clear();
                    }
                    myadapter.notifyDataSetChanged();
                    if (dataSnapshot.getChildrenCount() != 0) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            orders.add(data.getValue(PendingOrders.class));
                            Log.e("money",orders.get(0).getMoney());
                        }
                        if (porders.size() != 0)
                            porders.clear();
                        for (PendingOrders order : orders) {
                            Log.e("radius", String.valueOf(10));
                            if (radius >= (int) dist(order.getUserAddress())) {
                                porders.add(order);
                            }
                            if(porders.size()==0)
                                Toast.makeText(Rider_Activity.this, "No Orders for the set Radius", Toast.LENGTH_SHORT).show();
                        }
                        myadapter.notifyDataSetChanged();
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                radius = progress;
                                if (porders.size() != 0)
                                    porders.clear();
                                for (PendingOrders order : orders) {
                                    tvDist.setText(String.valueOf(progress) + " km");
                                    Log.e("radius", String.valueOf(progress));
                                    if (progress >= (int) dist(order.getUserAddress())) {
                                        porders.add(order);
                                    }
                                }
                                if(porders.size()==0)
                                    Toast.makeText(Rider_Activity.this, "No Orders for the set Radius", Toast.LENGTH_SHORT).show();
                                myadapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }
                        });
                        myadapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Rider_Activity.this, "No New Orders", Toast.LENGTH_SHORT).show();
                        myadapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            myadapter = new MyRiderAdapter(Rider_Activity.this, porders);
            recyclerview.setAdapter(myadapter);
        }
    }
    float[] result;
    private float dist(String User){
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);// last known locations
        Criteria criteria= new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Rider_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.SEND_SMS}, 0);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//provider
           Log.e("Current",location.toString());
            LatLng user = geoLocate(User);
            Log.e("user",user.toString());
            result = new float[1];
            Location.distanceBetween(location.getLatitude(),location.getLongitude(), user.latitude, user.longitude, result);
        }
//        Log.e("dist",String.valueOf(result[0]/1000));
        return result[0]/1000;
    }
    private LatLng geoLocate(String address) {
        Log.e("trackOrder", "geolocate");
        Geocoder geocoder = new Geocoder(Rider_Activity.this);
        List<Address> list = new ArrayList<>();
        try {
            if (address != null) {
                Log.e("Raddress1", address);
                list = geocoder.getFromLocationName(address, 1);
                String loc = list.get(0).toString();
                lat = Double.parseDouble(loc.substring(loc.lastIndexOf("latitude=") + 9, loc.lastIndexOf(",hasLo")));
                lng = Double.parseDouble(loc.substring(loc.lastIndexOf("longitude") + 10, loc.lastIndexOf(",phone")));
            }
        } catch (IOException e) {
            Log.e("geoLocate", e.getMessage());
        }
        return new LatLng(lat, lng);
    }
    @Override
    public void sendMsg(String UserName,String number,String OrderId,String Restname,String OTP) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
            }
        }
        else {
            SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("+91"+number, null, "Hi "+UserName+", your order has been Placed. I am on my way to Pick up" +
                                        " your Meal.\nOrder Id: "+
                                                            OrderId+"\nRestaurant: "+Restname+"\nOTP: "+OTP , null, null);
        Toast.makeText(this, "SMS sent.",Toast.LENGTH_LONG).show();
    }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e("SendMsgReq", "gaya");
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("SendMsgReq", "gaya");
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("+919018585703", null, "Msg sent", null, null);
                Toast.makeText(this, "SMS sent.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "SMS faild, please try again.", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.exit:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                AlertDialog alert= new AlertDialog.Builder(Rider_Activity.this)
                        .setTitle("DelivryAtDoors")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Rider_Activity.this,Login.class));
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF0A833D"));
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF0A833D"));

                break;

        }
        return true;
    }
    @Override
    public void frag(String n,PendingOrders orders) {
        key=(n);
        Log.e("intAct",orders.getMoney());
//        otpFrag= OTPFrag.newInstance(orders1);
        Bundle args=new Bundle();
        args.putParcelable("OrderSum",orders);
        otpFrag.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.RiderFrame,otpFrag,"OTPFRAG")
                .commit();
    }
}
