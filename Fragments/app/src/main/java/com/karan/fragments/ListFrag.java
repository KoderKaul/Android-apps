package com.karan.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFrag extends ListFragment {
    //Ye fragment sends Index which List Item selected that need to be shown on the Detail View
    //Fragments me interface likho aur Main me implement karo
    ItemSelected activity;// ItemSelected ka object coz we need to connect to the activity that connects the Fragments i. MAIN here

    public interface ItemSelected{
        void onItemSelected(int index);// methods that our interface uses
    }
    public ListFrag() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {// context is the activity that called these Fragments
        super.onAttach(context);

        activity= (ItemSelected) context;// context wali activity ko typecast krke ItemSelected me convert krre but before we need to implement ItemSelected
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /* ArrayList <String> arr = new ArrayList<>();// All this method to create a list of items
        arr.add("1. Item one");
        arr.add("2. Item two");
        arr.add("3. Item three");
        */
        String [] arr= getResources().getStringArray(R.array.pieces);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arr));
        //activity.onItemSelected(0);//Otherwise start me TextView show hora tha

    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
       // super.onListItemClick(l, v, position, id);
        activity.onItemSelected(position); //uses the onItemSelected method implented in main which now will send info to Details view
    }
}
