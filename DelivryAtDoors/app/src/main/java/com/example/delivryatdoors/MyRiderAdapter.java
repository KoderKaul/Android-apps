package com.example.delivryatdoors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static androidx.core.content.ContextCompat.startActivity;

public class MyRiderAdapter extends RecyclerView.Adapter<MyRiderAdapter.ViewHolder>  {
    ArrayList<PendingOrders> rOrders= new ArrayList<>();
    Context context;
    private onAcceptance onAcceptance;
    private DatabaseReference databaseOrder;
    FragmentManager fragmentManager;
    public MyRiderAdapter(Context context, ArrayList<PendingOrders> rOrders) {
        this.context=context;
        this.rOrders=rOrders;

    }

    public interface onAcceptance{
        void sendMsg(String UserName,String number,String OrderId,String Restname,String OTP);
        void frag(String n,PendingOrders orders);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvRname,tvRaddress,tvOrder,tvCAddress;
        Button btnAccept;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCAddress=itemView.findViewById(R.id.tvCAddress);
            tvRaddress=itemView.findViewById(R.id.tvRAddress);
            tvRname=itemView.findViewById(R.id.tvRName);
            btnAccept=itemView.findViewById(R.id.btnAccept);
        }
    }

    @NonNull
    @Override
    public MyRiderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rider_order_list,parent,false);
        databaseOrder= FirebaseDatabase.getInstance().getReference("Pending Orders");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRiderAdapter.ViewHolder holder, final int position) {
        holder.tvRname.setText(rOrders.get(position).getrestName());
        holder.tvRaddress.setText(rOrders.get(position).getRestAddress());
        holder.tvCAddress.setText(rOrders.get(position).getUserAddress());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd=new Random();
                int n=rnd.nextInt(999999);
                Log.e("otp",rOrders.get(position).getMoney());
                onAcceptance.frag(new DecimalFormat("#000000").format(n),rOrders.get(position));
                onAcceptance.sendMsg(rOrders.get(position).getUserName(),rOrders.get(position).getNumber(),
                        rOrders.get(position).getOrderId(),rOrders.get(position).getrestName(),new DecimalFormat("#000000").format(n));

                databaseOrder.child(rOrders.get(position).getOrderId()).setValue(null);
                Log.e("rOrder size", String.valueOf(rOrders.size()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        onAcceptance=(onAcceptance)context;
    }

    @Override
    public int getItemCount() {
        return rOrders.size();
    }
}
