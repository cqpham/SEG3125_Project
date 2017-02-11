package com.cpham.lab1_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SummaryFragment extends FragmentManager {

    public SummaryFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the layout of this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        return view;
    }
}
