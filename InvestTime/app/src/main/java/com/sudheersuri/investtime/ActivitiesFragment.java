package com.sudheersuri.investtime;

import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivitiesFragment extends Fragment {
    DatabaseHelper myDb;
    private RecyclerView rv;
    TextView datetext;
    ImageButton imageButton;
    String selecteddate;
    String todaydate;
    Cursor res;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activities, container, false);
        todaydate = new SimpleDateFormat("MM-DD-YY", Locale.getDefault()).format(new Date());
        rv = v.findViewById(R.id.myrecyclerview);
        datetext = v.findViewById(R.id.datetext);
        imageButton = v.findViewById(R.id.calendarimg);
        myDb = new DatabaseHelper(getActivity());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            selecteddate= bundle.getString("selecteddate");
            if (todaydate.equals(selecteddate))
                datetext.setText("Today");
            else
                datetext.setText(selecteddate);
        }
        if(selecteddate==null)
            res= myDb.getAllData(todaydate);
        else
            res=myDb.getAllData(selecteddate);

        if (res.getCount() == 0) {
            // show message
            /* showMessage("Error","Nothing found");*/
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_LONG).show();
        } else {
            MyAdapter adapter = new MyAdapter(getContext(), res);

            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = null;
                selectedFragment = new DateSelectorFragment();
                ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        });
        return v;
    }
}