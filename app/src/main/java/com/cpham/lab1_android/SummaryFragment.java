package com.cpham.lab1_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummaryFragment extends FragmentManager {

    private TextView txtViewTotalAmount, txtViewTotalTripAmount, txtViewTotalTipAmount, txtViewAmountPerPayee;
    private LinearLayout numPayeesLayout;

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
        txtViewTotalAmount.setText("$" + b.getString("total_amount") + " CAD");
        txtViewTotalTripAmount = (TextView) view.findViewById(R.id.total_trip_cost_value);
        txtViewTotalTripAmount.setText("$" + b.getString("total_trip_amount") + " CAD");
        txtViewTotalTipAmount = (TextView) view.findViewById(R.id.total_tip_cost_value);
        txtViewTotalTipAmount.setText("$" + b.getString("total_tip_amount") + " CAD");
        txtViewAmountPerPayee = (TextView) view.findViewById(R.id.summary_price_per_payee_value);

        numPayeesLayout = (LinearLayout) view.findViewById(R.id.price_per_payee_layout);
        if (Integer.parseInt(b.getString("num_payees")) > 1) {
            //show layout
            numPayeesLayout.setVisibility(LinearLayout.VISIBLE);
            txtViewAmountPerPayee.setText("$" + b.getString("amount_per_payee") + " CAD");
        } else {
            //hide layout
            numPayeesLayout.setVisibility(LinearLayout.GONE);
        }

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
