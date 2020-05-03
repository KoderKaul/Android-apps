package com.example.delivryatdoors;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RestaurantDetailFragment extends Fragment {

    private RecyclerView recyclerView2;
    private RecyclerView.Adapter adapter2;
    private RecyclerView.LayoutManager layoutManager2;
    private Restaurants mRestaurants;
    private ImageUrl mUrl;
    private ImageView image_restaurant;
    private TextView name_restaurant,speciality_restaurant, address;
    private ArrayList<Dish> mMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            mRestaurants = bundle.getParcelable("lolu");
            mUrl = bundle.getParcelable("dholu");
            mMenu =  bundle.getParcelableArrayList("bholu");
        }
        adapter2 = new RestaurantMenuAdapter(getActivity(),mMenu,mRestaurants);
        recyclerView2 = v.findViewById(R.id.restaurant_menu);
        recyclerView2.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);

        image_restaurant = v.findViewById(R.id.image_restaurant);
        name_restaurant = v.findViewById(R.id.name_restaurant);
        speciality_restaurant = v.findViewById(R.id.speciality_restaurant);
        address = v.findViewById(R.id.restAddress);

        Glide.with(getActivity())
                .load(mUrl.getImageUrl())
                .into(image_restaurant);

        name_restaurant.setText(mRestaurants.getRestaurantName());
        speciality_restaurant.setText(mRestaurants.getSpeciality_first());
        address.setText(mRestaurants.getaddress());
        recyclerView2.setAdapter(adapter2);

        return v;
    }
}
