package com.karan.recyclerchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.ViewHolder> {
    ItemClicked activity;

    public interface ItemClicked{
        void onItemClick(int index);
    }



    public PersonalAdapter(Context context) {
        activity=(ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        TextView tvModel;
        TextView tvOwner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPic=itemView.findViewById(R.id.ivPic);
            tvModel=itemView.findViewById(R.id.tvModel);
            tvOwner=itemView.findViewById(R.id.tvOwner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClick(ApplicationClass.buyer.indexOf((Details)v.getTag()));
                }
            });
        }

    }
    @NonNull
    @Override
    public PersonalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(ApplicationClass.buyer.get(position));
        holder.tvOwner.setText(ApplicationClass.buyer.get(position).getName());
        holder.tvModel.setText(ApplicationClass.buyer.get(position).getModel());
        if(ApplicationClass.buyer.get(position).getType().equals("Volkswagen")){
            holder.ivPic.setImageResource(R.drawable.volkswagen);
        }
        if(ApplicationClass.buyer.get(position).getType().equals("Nissan")){
            holder.ivPic.setImageResource(R.drawable.nissan);
        }
    }


    @Override
    public int getItemCount() {
        return ApplicationClass.buyer.size();
    }
}
