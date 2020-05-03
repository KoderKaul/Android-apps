package com.example.delivryatdoors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    TextView tvOrder,tvPrice,tvQuantity,tvTotal,tvSubT2,tvSubT,tvSubT3,tvSubT4,tvTax;
    ArrayList<Orders> porders=new ArrayList<>();
    String restName,restAddress;
    String key;
    private DatabaseReference databaseUser,databaseOrder;
    FirebaseUser userF;
    FirebaseDatabase database;
    DatabaseReference userRef;
    PendingOrders pending;
    int childCount=0;
    int SubT=0;
    String number="",address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        tvSubT=findViewById(R.id.tvSubT);
        tvSubT3=findViewById(R.id.tvSubT3);
        tvSubT4=findViewById(R.id.tvSubT4);
        tvSubT2=findViewById(R.id.tvSubT2);
        tvTax=findViewById(R.id.tvTax);
        tvOrder=findViewById(R.id.tvOrderName);
        tvPrice=findViewById(R.id.tvPrice);
        tvQuantity=findViewById(R.id.tvQuantity);
        tvTotal=findViewById(R.id.tvTotal);
        porders=getIntent().getParcelableArrayListExtra("orders");
        restName=getIntent().getStringExtra("RestName");
        restAddress=getIntent().getStringExtra("RestAddress");
        String name="",price="",quantity="",Total="";
        SubT=0;
        for(int i=0;i<porders.size();i++){
            if(i!=porders.size()-1) {
                name = name + porders.get(i).getOrder_name() + "\n\n";
                price = price + (porders.get(i).getOrder_price()) + "\n\n";
                quantity = quantity + (porders.get(i).getOrder_quantity()) + "\n\n";
                Total = Total + (porders.get(i).getOrder_quantity() * porders.get(i).getOrder_price()) + "\n\n";
                SubT+=porders.get(i).getOrder_quantity() * porders.get(i).getOrder_price();
            }
            else{
                name = name + porders.get(i).getOrder_name() + "\n";
                price = price + (porders.get(i).getOrder_price()) + "\n";
                quantity = quantity + (porders.get(i).getOrder_quantity()) + "\n";
                Total = Total + (porders.get(i).getOrder_quantity() * porders.get(i).getOrder_price()) + "\n";
                SubT+=porders.get(i).getOrder_quantity() * porders.get(i).getOrder_price();
            }
        }
        final DecimalFormat db=new DecimalFormat("#0.00");
        tvTax.setText("₹ "+ db.format(SubT * 0.05));
        tvSubT.setText("₹ "+SubT);
        tvSubT4.setText("₹ "+db.format(SubT*1.05+50));
        tvSubT3.setText("-  ₹ "+db.format(0.2*(SubT*1.05+50)));
        tvSubT2.setText("₹ "+db.format(0.8*(SubT*1.05+50)));
        tvOrder.setText(name);
        tvTotal.setText(Total);
        tvQuantity.setText(quantity);
        tvPrice.setText(price);


        databaseUser= FirebaseDatabase.getInstance().getReference("Cart Order");
        databaseOrder=FirebaseDatabase.getInstance().getReference("Pending Orders");
        key= databaseUser.push().getKey();// order id
        userF= FirebaseAuth.getInstance().getCurrentUser();
        userRef=FirebaseDatabase.getInstance().getReference();
        databaseUser.child(MainActivity.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                childCount= (int) dataSnapshot.getChildrenCount();
                if(childCount<10){
                    findViewById(R.id.textView30).setVisibility(View.INVISIBLE);
                    findViewById(R.id.tvSubT3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textView31).setVisibility(View.INVISIBLE);
                    findViewById(R.id.textView13).setVisibility(View.INVISIBLE);
                    findViewById(R.id.tvSubT2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.view4).setVisibility(View.INVISIBLE);
                }else
                    findViewById(R.id.tvNotice).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int position = 0; position < porders.size(); position++) {
                    Orders order = new Orders(porders.get(position).getOrder_name(), porders.get(position).getOrder_type(),
                            porders.get(position).getOrder_price(), porders.get(position).getOrder_quantity());
                    databaseUser.child(userF.getUid()).child(key).child(porders.get(position).getOrder_name()).setValue(order);
                    Log.e("porders",order.toString());
//  test
//                        User user = new User("name","email","phone_number","","",MainActivity.uid);
//                        userRef.child("Users").child(MainActivity.uid).setValue(user);
                }
                //get phone number and address from database
                userRef.child("users").child(MainActivity.uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                Log.e("cart", data.getValue().toString());
                            if (data.getKey().equals("number")) {
                                number = data.getValue().toString();
//                                    Log.e("number", number);
                            }
                            if (data.getKey().equals("address"))
                                address = data.getValue().toString();
                        }
                        //sending placed orders
                        Log.e("Money",db.format(0.8*(SubT*1.05+50)));
                        if(childCount<10)
                            pending = new PendingOrders(key, userF.getDisplayName(), number, address, restName,restAddress, db.format(SubT*1.05+50));
                        else
                            pending = new PendingOrders(key, userF.getDisplayName(), number, address, restName,restAddress, db.format(0.8*(SubT*1.05+50)));
                        databaseOrder.child(key).setValue(pending);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(PaymentActivity.this,"Order Placed",Toast.LENGTH_SHORT).show();
            finish();
            }

        });
    }
}
