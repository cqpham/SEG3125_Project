package com.cpham.lab1_android;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainFragment extends FragmentManager {

    private ImageButton btnAddPayee, btnDelPayee;
    private EditText editTxtTotalDistance, editTxtTipPercentage;
    private TextInputLayout txtLayoutTotalDistance, txtLayoutTipPercentage;
    private Button btnCalculate, btnReset;

    public MainFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the layout of this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btnAddPayee = (ImageButton) view.findViewById(R.id.addPayee);
        btnDelPayee = (ImageButton) view.findViewById(R.id.subPayee);
        editTxtTotalDistance = (EditText) view.findViewById(R.id.input_totalDistance);
        editTxtTipPercentage = (EditText) view.findViewById(R.id.input_tipPercent);
        txtLayoutTotalDistance = (TextInputLayout) view.findViewById(R.id.input_layout_totalDistance);
        txtLayoutTipPercentage = (TextInputLayout) view.findViewById(R.id.input_layout_tipPercent);
        btnCalculate = (Button) view.findViewById(R.id.calculate_trip);
        btnReset = (Button) view.findViewById(R.id.reset_trip);

        addListenerAddBtn();
        addListenerDelBtn();
        editTxtTotalDistance.addTextChangedListener(new CustomTextWatcher(editTxtTotalDistance));
        editTxtTipPercentage.addTextChangedListener(new CustomTextWatcher(editTxtTipPercentage));
        //addListenerResetBtn();
        addListenerCalBtn();

        return view;
    }

    public void addListenerAddBtn() {
        btnAddPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtNumPayees = (TextView) getView().findViewById(R.id.input_num_payees);
                int value = Integer.parseInt(txtNumPayees.getText().toString());
                if (value < 10) {
                    value++;
                    txtNumPayees.setText(String.valueOf(value));
                }
            }
        });
    }

    public void addListenerDelBtn() {
        btnDelPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtNumPayees = (TextView) getView().findViewById(R.id.input_num_payees);
                int value = Integer.parseInt(txtNumPayees.getText().toString());
                if (value > 1) {
                    value--;
                    txtNumPayees.setText(String.valueOf(value));
                }
            }
        });
    }

    public void addListenerCalBtn() {
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDistance() && validateTipPercent()) {
                    ((MainActivity) getActivity()).displaySummary(getView());
                }
            }
        });
    }

    public void addListenerResetBtn() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private class CustomTextWatcher implements TextWatcher {
        private View view;

        private CustomTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.input_totalDistance:
                    validateDistance();
                    break;
                case R.id.input_tipPercent:
                    validateTipPercent();
            }
        }
    }

    private boolean validateDistance() {
        String txt = editTxtTotalDistance.getText().toString().trim();
        if (txt.isEmpty()) {
            txtLayoutTotalDistance.setError(getString(R.string.err_msg_invalid));
            return false;
        } else if (Double.parseDouble(txt) > 10000) {
            txtLayoutTotalDistance.setError(getString(R.string.err_msg_distExceed));
            return false;
        } else if(Double.parseDouble(txt) == 0) {
            txtLayoutTotalDistance.setError(getString(R.string.err_msg_zero));
            return false;
        } else {
            txtLayoutTotalDistance.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTipPercent() {
        String txt = editTxtTipPercentage.getText().toString().trim();
        if (txt.isEmpty()) {
            txtLayoutTipPercentage.setError(getString(R.string.err_msg_invalid));
            return false;
        } else if (Integer.parseInt(txt) > 100) {
            txtLayoutTipPercentage.setError(getString(R.string.err_msg_tipExceed));
            return false;
        } else if (Integer.parseInt(txt) == 0) {
            txtLayoutTipPercentage.setError(getString(R.string.err_msg_zero));
            return false;
        } else {
            txtLayoutTipPercentage.setErrorEnabled(false);
        }
        return true;
    }
}
