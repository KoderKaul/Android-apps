package com.karan.recyclerchallenge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFrag extends Fragment {

    FragmentManager fragmentManager;

    public DetailFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentManager=this.getChildFragmentManager();
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentById(R.id.car))
                .hide(fragmentManager.findFragmentById(R.id.own))
                .commit();
    }
    public void Owner(){
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentById(R.id.car))
                .hide(fragmentManager.findFragmentById(R.id.own))
                .commit();
    }
    public void Car(){
        fragmentManager.beginTransaction()
                .show(fragmentManager.findFragmentById(R.id.own))
                .hide(fragmentManager.findFragmentById(R.id.car))
                .commit();
    }
}
