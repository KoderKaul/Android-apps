package com.karan.recyclerfrags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    ItemClicked activity;
    public interface ItemClicked{
        void onItemCLicked(int index);
    }

    public myAdapter(Context context, ArrayList<Person>list) {
        activity=(ItemClicked)context;// connect krni h activity
    }
    //Instead of Recycler View holder we create our own viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);//itemView will be linked to list_view.xml
            tvName=itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemCLicked(ApplicationClass.peep.indexOf((Person)v.getTag()));//jo bi click hua wo ek View h uska niche Tag diya tha ab
                        //us tag ke through apn Index NIKALENGE people Array ke Items ka jo click hua tha taki uski deatils mile
                }
            });

        }
    }
    @NonNull
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we now will use this class to link to list_view.xml
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);
        //u created a View holder that keeps views in it
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.ViewHolder holder, int position) {
        //its job is to connect Viewholder, Arraylist and ur List_view.xml
        holder.itemView.setTag(ApplicationClass.peep.get(position));// OnclickListener apne ko position bejega clicked ki
                // idhar apn items ko tag dere jo bi list me jaega
        holder.tvName.setText(ApplicationClass.peep.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return ApplicationClass.peep.size();
    }
}
