package com.example.delivryatdoors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private static final String TAG = " ";

    private ArrayList<Restaurants> restaurants;
    private ArrayList<ImageUrl> imageUrls,oye;
    private Context context;
    private ArrayList<ArrayList<Dish>> menus;
    private RestaurantSelected restaurantSelected;

    public RestaurantAdapter(ArrayList<Restaurants> restaurants, ArrayList<ImageUrl> imageUrls, Context context, ArrayList<ImageUrl> oye, ArrayList<ArrayList<Dish>> menus) {
        this.restaurants = restaurants;
        this.imageUrls = imageUrls;
        this.context = context;
        this.oye = oye;
        this.menus = menus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView restaurant_image;
        TextView restaurant_name,speciality,restaurant_description,cost_for_two;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurant_image = itemView.findViewById(R.id.restaurant_image);
            restaurant_description = itemView.findViewById(R.id.restaurant_description);
            restaurant_name = itemView.findViewById(R.id.restaurant_name);
            speciality = itemView.findViewById(R.id.speciality);
            cardview = itemView.findViewById(R.id.cardview);
            cost_for_two = itemView.findViewById(R.id.cost_for_two);
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        restaurantSelected = (RestaurantSelected) context;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, final int position) {
        Log.e(TAG,"onBindViewHolder: called");

        holder.itemView.setTag(restaurants.get(position));

        holder.restaurant_name.setText(restaurants.get(position).getRestaurantName());
        holder.speciality.setText(restaurants.get(position).getSpeciality_first());
        holder.cost_for_two.setText("Cost for two: ₹ "+Integer.toString(restaurants.get(position).getCost_for_Two()));
        Glide.with(context)
                .load(imageUrls.get(position).getImageUrl()).fitCenter().into(holder.restaurant_image);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Restaurant", String.valueOf(position)+" "+restaurants.get(position).getRestaurantName());
                restaurantSelected.inflateViewRestaurantFragment(restaurants.get(position) ,oye.get(position),menus.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}
