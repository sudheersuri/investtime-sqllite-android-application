package com.sudheersuri.investtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.MyViewHolder> {

    Cursor res;
    Context ctx;

    public DateListAdapter(Context ctx, Cursor res) {
        this.ctx = ctx;
        this.res = res;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dateeachrow, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return res.getCount();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        res.moveToPosition(position);
        holder.activitydate.setText(res.getString(0));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res.moveToPosition(position);
                final Activity a = (Activity)ctx;
                Fragment fragment = new ActivitiesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("selecteddate",res.getString(0));
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction =((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView activitydate;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activitydate = itemView.findViewById(R.id.activitydate);
            cardView = (CardView) itemView.findViewById(R.id.datecardview);
        }
    }
}
