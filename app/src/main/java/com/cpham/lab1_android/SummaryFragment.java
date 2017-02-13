package com.cpham.lab1_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SummaryFragment extends FragmentManager {

    private TextView txtViewTotalAmount;

    public SummaryFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle b = getArguments();

        //Inflate the layout of this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        txtViewTotalAmount = (TextView) view.findViewById(R.id.total_amount);
        txtViewTotalAmount.setText(b.getString("total_amount"));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        calculate();
    }

    private void calculate() {

    }
}
