package com.karan.fragmentsrecycler_cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter <PersonAdapter.ViewHolder>{//Add RecyclerView.Adapter used to feed all your data to list, creates views for Items
    private ArrayList<Person> people;
    ItemClicked activity;
    public interface ItemClicked{
        void onItemClicked(int index);
    }
    public PersonAdapter(Context context, ArrayList<Person> list){//Constructor ko jo list pass hogi
        people=list;// woo people ko initialize karega
        activity=(ItemClicked)context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{//defines where we place items on the layout
        ImageView IvPic;
        TextView TvName, TvSurname;

        public ViewHolder(@NonNull View itemView) {// basically the lists_items layout's components
            super(itemView);
            TvName=itemView.findViewById(R.id.TvName);
            TvSurname=itemView.findViewById(R.id.TvSurname);
            IvPic=itemView.findViewById(R.id.ivPic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(people.indexOf((Person)v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {// added PersonAdapter in place of RecyclerView to make our own ViewHolder

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items,viewGroup,false);// inflate
        return new ViewHolder(v);//
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {// har bande ke liye run hoga The ArrayList we created
        holder.itemView.setTag(people.get(position));
        holder.TvName.setText(people.get(position).getName());
        holder.TvSurname.setText(people.get(position).getSurname());
        if(people.get(position).getPreference().equals("bus"))
            holder.IvPic.setImageResource(R.drawable.bus);
        if(people.get(position).getPreference().equals("plane"))
                holder.IvPic.setImageResource(R.drawable.plane);
    }

    @Override
    public int getItemCount() {
        return people.size();// itti bar onBind chalega
    }
}
