package com.example.delivryatdoors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    private ArrayList<Orders> orders;
    private ArrayList<Restaurants> restro;
    int Price=0;
    SetText set;
    Orders order;
    public interface SetText{
        void priceSet(int pri,int price);
    }
        CartAdapter(Context context, ArrayList<Orders> orders, ArrayList<Restaurants> restro){
            this.context=context;
            this.orders = orders;
            this.restro=restro;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView order_name,order_price,order_type,order_quantity;
            ImageView decreaseByone, increaseByone;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                order_name = itemView.findViewById(R.id.order_name);
                order_price = itemView.findViewById(R.id.order_price);
                order_type = itemView.findViewById(R.id.order_type);
                order_quantity = itemView.findViewById(R.id.order_quantity);
                decreaseByone = itemView.findViewById(R.id.decreaseByone);
                increaseByone = itemView.findViewById(R.id.increaseByone);
            }
        }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        set=(SetText) context;
    }

    @NonNull
        @Override
        public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
                holder.order_name.setText(orders.get(position).getOrder_name());
             //   holder.order_price.setText(orders.get(position).getOrder_price());
    /* this is price */            holder.order_type.setText(Integer.toString(orders.get(position).getOrder_price()));// price h ye
                holder.order_quantity.setText(Integer.toString(orders.get(position).getOrder_quantity()));
                holder.increaseByone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Price",String.valueOf(Price));
                        orders.get(position).setOrder_quantity(orders.get(position).getOrder_quantity()+1);
                        holder.order_quantity.setText(Integer.toString(orders.get(position).getOrder_quantity()));
//                        tvTotal.setText(String.valueOf(Price)+ " Rs");
                    }
                });

                holder.decreaseByone.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Log.e("Price",String.valueOf(Price));
                    if(orders.get(position).getOrder_quantity()>0) {
                        orders.get(position).setOrder_quantity(orders.get(position).getOrder_quantity()-1);
                    }
                    if(orders.get(position).getOrder_quantity()==0){
                        order=orders.get(position);
                        orders.remove(order);
                        if (restro.size()!=0) {
                            Restaurants r = restro.get(position);
                            restro.remove(r);
                        }
                        notifyDataSetChanged();
                    }else{
                        holder.order_quantity.setText(Integer.toString(orders.get(position).getOrder_quantity()));
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            int i= orders.size();
           if(i == 0)
            return 0;
           else
               return i;
        }

}
