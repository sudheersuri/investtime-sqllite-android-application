package com.sudheersuri.investtime;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ActivityDetailsFragment extends BottomSheetDialogFragment {
    Button addbtn;
    EditText e1, e2;
    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_details, container, false);
        addbtn = v.findViewById(R.id.addactivitybtn);
        e1 = v.findViewById(R.id.activityname);
        e2 = v.findViewById(R.id.activitytime);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString() != null && e2.getText().toString() != null && e1.getText().toString().matches(".*[a-zA-Z]+.*") && !e2.getText().toString().matches(".*[a-zA-Z]+.*"))
                    mListener.onButtonClicked(e1.getText().toString().trim(), Integer.parseInt(e2.getText().toString().trim()));
                else
                    Toast.makeText(getActivity(),"Invalid Entry",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String actitvityname, int investedtime);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}