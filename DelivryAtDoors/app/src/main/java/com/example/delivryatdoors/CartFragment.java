package com.example.delivryatdoors;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment  {
    private RecyclerView cartRecyclerview;
    private RecyclerView.Adapter cartadapter;
    private RecyclerView.LayoutManager cartLayoutManager;
    private ArrayList<Orders> orders=new ArrayList<>();
    private ArrayList<Dish> diss=new ArrayList<>();
    private ArrayList<Restaurants> restro=new ArrayList<>();
    private ArrayList<Restaurants> restro1=new ArrayList<>();
    private int Restcount;
    PendingOrders pending;
    String number ="";
    String address="";
    Button btnPlace;
    String key;
    private DatabaseReference databaseUser,databaseOrder;
    FirebaseUser userF;
    FirebaseDatabase database;
    DatabaseReference userRef;// getting data
    private User yo;
    int count,Count;
    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        btnPlace=v.findViewById(R.id.btn_placeorder);

        databaseUser= FirebaseDatabase.getInstance().getReference("Cart Order");
        databaseOrder=FirebaseDatabase.getInstance().getReference("Pending Orders");
        database=FirebaseDatabase.getInstance();
        cartRecyclerview = v.findViewById(R.id.recycler_cart);
        cartLayoutManager = new LinearLayoutManager(getActivity());
        cartRecyclerview.setLayoutManager(cartLayoutManager);
        Bundle bunddle = this.getArguments();
        if(bunddle != null) {
            diss = bunddle.getParcelableArrayList("yo");
            for(Dish dish: diss) {
                orders.add(new Orders(dish.getDish_name(), dish.getDish_type(), dish.getPrice(), 1));
            }
            restro1=bunddle.getParcelableArrayList("Restro");
            for(Restaurants res:restro1)
                restro.add(new Restaurants(res.getRestaurantName(),res.getSpeciality_first(),res.getaddress(),res.getDescription(),
                        res.getCost_for_Two(),res.getRating()));
        }
        restro1.clear();
        diss.clear();
        Log.e("Orders size",Integer.toString(orders.size()));
        Log.e("t","running adapter");
        cartadapter = new CartAdapter(getContext(),orders,restro);
        key= databaseUser.push().getKey();// order id
        userF= FirebaseAuth.getInstance().getCurrentUser();
        userRef=database.getReference();
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count= 0;
                FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if ((data.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                                    Log.e("key2", data.getKey() + data.getValue().toString());
                                    User user = data.getValue(User.class);
                                    if (user.getName() == null || user.getAddress() == null || user.getNumber() == null
                                            || user.getNumber().equals("") || user.getAddress().equals("") || user.getName().equals("")) {
                                        count = 5;
                                    }
                                    Log.e("done", String.valueOf(count));
                                    if (count == 0) {
                                        Restcount = 0;
                                        Log.e("Restro size", String.valueOf(restro.size()));
                                        for (int i = 0; i < restro.size() - 1; i++) {
                                            Log.e("Restro name", String.valueOf(i) + " " + restro.get(i).getRestaurantName());
                                            if (!restro.get(i).getRestaurantName().equals(restro.get(i + 1).getRestaurantName())) {
                                                Restcount++;
                                            }
                                        }
                                        if (Restcount == 0) {
                                            if (orders.size() != 0) {
                                                Intent intent = new Intent(getContext(), PaymentActivity.class);
                                                intent.putParcelableArrayListExtra("orders", orders);
                                                intent.putExtra("RestName", restro.get(0).getRestaurantName());
                                                intent.putExtra("RestAddress", restro.get(0).getaddress());
                                                startActivity(intent);
                                                orders.clear();
                                                restro.clear();
                                                cartadapter.notifyDataSetChanged();
                                            }
                                        } else
                                            Toast.makeText(getActivity(), "Please select dishes from only one Caterer", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Please Update all Account details", Toast.LENGTH_SHORT).show();
                                        orders.clear();
                                        restro.clear();
                                        cartadapter.notifyDataSetChanged();
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), "Please Update all Account Details", Toast.LENGTH_SHORT).show();
                            orders.clear();
                            restro.clear();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Log.e("done1", String.valueOf(count));
            }
        });
        cartRecyclerview.setAdapter(cartadapter);

        return v;

    }


}
