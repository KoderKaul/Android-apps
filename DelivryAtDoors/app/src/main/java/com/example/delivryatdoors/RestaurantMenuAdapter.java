package com.example.delivryatdoors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Dish> dishes;
    private DishSelected dishSelected;
    private Restaurants resto;

    RestaurantMenuAdapter(Context context, ArrayList<Dish> dishes,Restaurants resto){
        this.dishes = dishes;
        this.context = context;
        this.resto = resto;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dish_name,dish_price,dish_type;
        ImageView adddish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.dish_name);
            dish_price = itemView.findViewById(R.id.dish_price);
            dish_type = itemView.findViewById(R.id.dish_type);
            adddish = itemView.findViewById(R.id.addDish);
        }
    }

    @NonNull
    @Override
    public RestaurantMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lolu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        dishSelected = (DishSelected) context;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.itemView.setTag(dishes.get(position));

        holder.dish_name.setText(dishes.get(position).getDish_name());
        holder.dish_type.setText(dishes.get(position).getDish_type());
        holder.dish_price.setText("₹"+Integer.toString(dishes.get(position).getPrice()));
        holder.adddish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,dishes.get(position).getDish_name()+" Added",Toast.LENGTH_SHORT).show();
                dishSelected.onDishSelected(dishes.get(position),resto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }
}
