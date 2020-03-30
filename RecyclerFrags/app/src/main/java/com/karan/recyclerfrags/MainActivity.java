package com.karan.recyclerfrags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements myAdapter.ItemClicked {
    EditText etName;
    EditText number;
    Button btn;
    ImageView img;
    TextView tvName;
    TextView tvNum;
    ListFrag listFrag;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        number=findViewById(R.id.etNumber);
        btn=findViewById(R.id.btnAdd);
        img=findViewById(R.id.imageView);
        tvName=findViewById(R.id.tvName);
        tvNum=findViewById(R.id.tvNum);
        manager= this.getSupportFragmentManager();
        listFrag=(ListFrag) manager.findFragmentById(R.id.fragment3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty()||number.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this,"Please enter all fields",Toast.LENGTH_SHORT).show();
                else {
                    ApplicationClass.peep.add(new Person(etName.getText().toString(), number.getText().toString()));
                    Toast.makeText(MainActivity.this, etName.getText().toString() + "added successfully", Toast.LENGTH_SHORT).show();
                    etName.setText(null);
                    number.setText(null);
                    // now we need to tell recycler to update hence Notify the changes
                    listFrag.notifyChange();
                }
            }

        });
        onItemCLicked(0);//initially kuch to show ho
    }

    @Override
    public void onItemCLicked(int index) {
        tvName.setText(ApplicationClass.peep.get(index).getName());
        tvNum.setText(ApplicationClass.peep.get(index).gettele());
    }
}
