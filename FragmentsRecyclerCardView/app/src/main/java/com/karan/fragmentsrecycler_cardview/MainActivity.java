package com.karan.fragmentsrecycler_cardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;//A LayoutManager is responsible for measuring and positioning item views within a
                        // RecyclerView as well as determining the policy for when to recycle item views that are no longer visible to the user.
    ArrayList<Person> peeps;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);//sets size of recycler fixed i.e adapter ke list items me change se farak nai padega isko
                                                //otherwise Recycler needs to optimize its list
        //layoutManager=new LinearLayoutManager(this);
        //layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peeps.add(new Person("Sexy","Sri","plane"));
                myAdapter.notifyDataSetChanged();
            }
        });
        peeps=new ArrayList<Person>();
        peeps.add(new Person("Karan","Kaul","plane"));
        peeps.add(new Person("Kan","K","bus"));
        peeps.add(new Person("Ran","Koul","plane"));
        peeps.add(new Person("Karan","Kaul","plane"));
        peeps.add(new Person("Kan","T","bus"));
        peeps.add(new Person("Ran","Koul","bus"));
        peeps.add(new Person("Karan","Kaul","plane"));
        peeps.add(new Person("Kan","S","bus"));
        peeps.add(new Person("Ran","Koul","plane"));

        myAdapter= new PersonAdapter(this,peeps);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        Toast.makeText(this,"Name: "+peeps.get(index).getName()+"\n"+"Surname: "+ peeps.get(index).getSurname(),Toast.LENGTH_SHORT ).show();
    }
}
