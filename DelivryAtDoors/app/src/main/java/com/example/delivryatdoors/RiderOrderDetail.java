package com.example.delivryatdoors;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RiderOrderDetail extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback,LocationListener {
    SupportMapFragment supportMapFragment;
    LocationManager locationManager;
    TextView tvRAddress,tvRName,tvCAddress,tvOTP,tvCNumber,tcCName,tvOrder;
    Button btnfinish;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    String provider;
    double lat;
    double lng;
    private GoogleMap mMap;
    String address,Uaddress;
    Polyline currentPolyline;
    LatLng latLngR,latLngU;
    PendingOrders Porder=new PendingOrders();
    public RiderOrderDetail() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_order_detail);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment);
        supportMapFragment.getMapAsync(this);
        Porder=getIntent().getParcelableExtra("OS");
        address=Porder.getRestAddress();
        Uaddress=Porder.getUserAddress();
        tvOrder=findViewById(R.id.tvOrder);
        tcCName=findViewById(R.id.tcCName);
        tvCNumber=findViewById(R.id.tvCNumber);
        tvRName=findViewById(R.id.tvRName);
        tvRAddress=findViewById(R.id.tvRAddress);
        tvCAddress=findViewById(R.id.tvCAddress);
        tvOTP=findViewById(R.id.tvMoney);
        tvOrder.setText(Porder.getOrderId());
        tcCName.setText(Porder.getUserName());
        tvCNumber.setText(Porder.getNumber());
        tvRName.setText(Porder.getrestName());
        Log.e("OnCaddress",Porder.getMoney());
        tvOTP.setText("₹ "+Porder.getMoney());
        tvRAddress.setText(address);
        tvCAddress.setText(Uaddress);

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);// last known locations
        Criteria criteria= new Criteria();
        provider = locationManager.getBestProvider(criteria,false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(RiderOrderDetail.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }
        else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                onLocationChanged(location);
                Log.e("Location", location.toString());
            }
        }
        btnfinish=findViewById(R.id.cdGoogle);
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert= new AlertDialog.Builder(RiderOrderDetail.this)
                        .setTitle("DelivryAtDoors")
                        .setMessage("Is the food Delivered?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(RiderOrderDetail.this,Rider_Activity.class));
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF0A833D"));
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF0A833D"));
            }
        });
    }
//
    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
        if(mMap!=null){
            latLngU=new LatLng(lat,lng);

            mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(latLngU)
                    .title("Current Location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngU,10f));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(RiderOrderDetail.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                onLocationChanged(location);
            }
        }

        geoLocate();
        final String url= getUrl(latLngU,Uaddress,"driving");
        new FetchUrl(this).execute(url,"driving");
    }
    private void geoLocate() {
        Log.e("trackOrder","geolocate");
        Geocoder geocoder=new Geocoder(RiderOrderDetail.this);
        List<Address> list= new ArrayList<>();
        try{
            if(address!=null) {
                Log.e("Raddress", address);
                list = geocoder.getFromLocationName(address, 1);
                String loc = list.get(0).toString();
                double lat = Double.parseDouble(loc.substring(loc.lastIndexOf("latitude=") + 9, loc.lastIndexOf(",hasLo")));
                double lng = Double.parseDouble(loc.substring(loc.lastIndexOf("longitude") + 10, loc.lastIndexOf(",phone")));
                latLngR = new LatLng(lat, lng);

                Marker k = mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title("Restaurant")
                        .position(latLngR)
                );
                if (Uaddress != null) {
                    Log.e("Raddress", address);
                    list = geocoder.getFromLocationName(Uaddress, 1);
                    String loc1 = list.get(0).toString();
                    double lat1 = Double.parseDouble(loc1.substring(loc1.lastIndexOf("latitude=") + 9, loc1.lastIndexOf(",hasLo")));
                    double lng1 = Double.parseDouble(loc1.substring(loc1.lastIndexOf("longitude") + 10, loc1.lastIndexOf(",phone")));
                    latLngR = new LatLng(lat1, lng1);

                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker())
                            .title("Destination")
                            .position(latLngR)
                    );
                    Log.e("address", loc);
                }
            }
        }catch (IOException e){
            Log.e("geoLocate",e.getMessage());
        }

    }
    private String getUrl(LatLng origin,String dest,String dmode){
        String str_origin="origin="+origin.latitude+","+origin.longitude;
        String str_dest="destination="+dest;
        String Res="waypoints="+ address;
        String mode="mode="+dmode;
        String parameter= str_origin+"&"+Res+"&"+str_dest+"&"+mode;
        String output="json";

        String url= "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameter+"&key="+"AIzaSyAQUJhnisi1IUxkn52XQMqNCdKGv06zDUY";
        //Log.e("karan",url);
        return url;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(RiderOrderDetail.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }
        else {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(RiderOrderDetail.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }
        else {
            locationManager.requestLocationUpdates(provider,MIN_TIME,MIN_DISTANCE,this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    public void onTaskDone(Object... values) {
        if(currentPolyline!=null)
            currentPolyline.remove();
        currentPolyline= mMap.addPolyline((PolylineOptions) values[0]);

    }
}
