package com.sudheersuri.investtime;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SummaryFragment extends Fragment {
    DatabaseHelper myDb;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary, container, false);
        rv=v.findViewById(R.id.myrecyclerview);
        myDb = new DatabaseHelper(getActivity());
        Cursor res= myDb.getSummaryData();
        if(res.getCount() == 0) {
            Toast.makeText(getActivity(),"No data",Toast.LENGTH_LONG).show();
        }
        else
        {
            SummaryAdapter adapter = new SummaryAdapter(getContext(),res);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return v;
    }
}