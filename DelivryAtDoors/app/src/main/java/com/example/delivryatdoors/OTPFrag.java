package com.example.delivryatdoors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OTPFrag extends Fragment  {
    EditText otpEdit1, otpEdit2, otpEdit3, otpEdit4, otpEdit5, otpEdit6;
    View view;
    PendingOrders pending=new PendingOrders();
    Bundle bundle;
    public OTPFrag() {
        // Required empty public constructor
    }
//    public static OTPFrag newInstance(PendingOrders orDer){
//        OTPFrag frag=new OTPFrag();
//
//        return frag;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_o_t_p, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otpEdit1=view.findViewById(R.id.otp_et1);
        otpEdit2=view.findViewById(R.id.otp_et2);
        otpEdit3=view.findViewById(R.id.otp_et3);
        otpEdit4=view.findViewById(R.id.otp_et4);
        otpEdit5=view.findViewById(R.id.otp_et5);
        otpEdit6=view.findViewById(R.id.otp_et6);
        if(this.getArguments()!=null)
            bundle=this.getArguments();
        view.findViewById(R.id.tvCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending = bundle.getParcelable("OrderSum");
               Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+pending.getNumber()));
               startActivity(intent);
            }
        });
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pending = bundle.getParcelable("OrderSum");
                    Log.e("OTPFrag", pending.getrestName());

                String k=otpEdit1.getText().toString()+otpEdit2.getText().toString()+otpEdit3.getText().toString()
                        +otpEdit4.getText().toString()+otpEdit5.getText().toString()+otpEdit6.getText().toString();
                Log.e("key,,",k+"\t"+Rider_Activity.key);
                if(k.equals(Rider_Activity.key)) {
                    Toast.makeText(getContext(), "OTP Verified", Toast.LENGTH_SHORT).show();
                    Log.e("OTPFragONclick", pending.getMoney());
                    Intent intent=new Intent(getActivity(),RiderOrderDetail.class);
                    intent.putExtra("OS",pending);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getContext(),"Try Again",Toast.LENGTH_SHORT).show();
            }
        });
            otpEdit1.requestFocus();

        otpEdit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit1.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
        otpEdit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit2.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit3.requestFocus();
                }
                if (otpEdit2.getText().toString().length()==0)
                    otpEdit1.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
        otpEdit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit3.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit4.requestFocus();
                }
                if (otpEdit3.getText().toString().length()==0)
                    otpEdit2.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
        otpEdit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit4.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit5.requestFocus();
                }
                if (otpEdit4.getText().toString().length()==0)
                    otpEdit3.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
        otpEdit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit5.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit6.requestFocus();
                }
                if (otpEdit5.getText().toString().length()==0)
                    otpEdit4.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
        otpEdit6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otpEdit6.getText().toString().length()==0)
                    otpEdit5.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable s) {   }
        });
    }

}