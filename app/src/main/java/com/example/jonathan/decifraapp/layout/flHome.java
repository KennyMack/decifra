package com.example.jonathan.decifraapp.layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jonathan.decifraapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class flHome extends Fragment {


    public flHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fl_home, container, false);
    }

}
