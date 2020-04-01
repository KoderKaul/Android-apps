package com.karan.recyclerchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PersonalAdapter.ItemClicked {
    TextView tvCarFrag,tvNumOwnFrag,tvNameOwnFrag;
    ImageView ivCarPic;
    FragmentManager fragmentManager;

    Button Car,Owner;
    DetailFrag detailFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCarFrag = findViewById(R.id.tvCarFrag);
        tvNumOwnFrag = findViewById(R.id.tvNumOwnFrag);
        tvNameOwnFrag = findViewById(R.id.tvNameOwnFrag);
        ivCarPic = findViewById(R.id.ivCarFrag);
        fragmentManager = this.getSupportFragmentManager();
        detailFrag=(DetailFrag)fragmentManager.findFragmentById(R.id.detail);
        Car=findViewById(R.id.btnCBtnFrag);
        Owner=findViewById(R.id.btnOBtnFrag);
        Car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailFrag.Owner();
            }
        });
        Owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailFrag.Car();
            }
        });
        onItemClick(0);
    }


    @Override
    public void onItemClick(int index) {
        tvCarFrag.setText(ApplicationClass.buyer.get(index).getModel());
        if(ApplicationClass.buyer.get(index).getType().equals("Volkswagen")){
            ivCarPic.setImageResource(R.drawable.volkswagen);
        }
        if(ApplicationClass.buyer.get(index).getType().equals("Nissan")){
            ivCarPic.setImageResource(R.drawable.nissan);
        }
        tvNumOwnFrag.setText(ApplicationClass.buyer.get(index).getNumber());
        tvNameOwnFrag.setText(ApplicationClass.buyer.get(index).getName());

    }
}
