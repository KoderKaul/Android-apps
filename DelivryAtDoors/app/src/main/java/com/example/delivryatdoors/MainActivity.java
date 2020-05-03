package com.example.delivryatdoors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements CartAdapter.SetText,BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener, RestaurantSelected ,DishSelected{

    private BottomNavigationView bottomNavigationView;
    private Fragment homeFragment,accountFragment,cartFragment,restaurantDetailFragment;
    private FragmentManager fragmentManager;
    private static final String TAG = " ";
    private Toolbar toolbars;
    private ArrayList<Dish> DishList=new ArrayList<>();
    private ArrayList<Restaurants> RestList=new ArrayList<>();
    public static String uid;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // for fragment
        fragmentManager = this.getSupportFragmentManager();
        homeFragment = new HomeFragment();
        accountFragment = new AccountFragment();
        cartFragment = new CartFragment();
        restaurantDetailFragment = new RestaurantDetailFragment();
        toolbars = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbars);
        final FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        final GoogleSignInAccount googleSignIn=GoogleSignIn.getLastSignedInAccount(this);
        Log.e("email", currentuser.getEmail()+"\t");
        Log.e("name", currentuser.getDisplayName()+"\t");


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        uid= FirebaseAuth.getInstance().getCurrentUser().getUid().trim();


        if(savedInstanceState == null){
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment).addToBackStack(null).commit();
        bottomNavigationView.setSelectedItemId(R.id.homes);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
               fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cartFragment).addToBackStack(null).commit();
                break;

            case R.id.account:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,accountFragment).addToBackStack(null).commit();
                break;

            case R.id.homes:
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment).addToBackStack(null).commit();
                break;

            case R.id.exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }

        return true;
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public void inflateViewRestaurantFragment(Restaurants restaurants, ImageUrl url, ArrayList<Dish> menus) {
        Log.e("Restaurant", restaurants.getRestaurantName());
        Bundle args = new Bundle();
        args.putParcelable("lolu", restaurants);
        args.putParcelable("dholu", url);
        args.putParcelableArrayList("bholu",menus);
        restaurantDetailFragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,restaurantDetailFragment).addToBackStack(null).commit();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    public void onDishSelected(Dish dish,Restaurants restro) {
        DishList.add(dish);
        RestList.add(restro);
        Bundle detroy = new Bundle();
        detroy.putParcelableArrayList("yo", DishList);
        detroy.putParcelableArrayList("Restro",RestList);
        cartFragment.setArguments(detroy);
    }
    @Override
    public void priceSet(int pri,int quantity) {
        tvTotal=findViewById(R.id.tvTotal);
//        Log.e("pr",String.valueOf(price));
//        tvTotal.setText(String.valueOf(price));
    }
}
