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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Cursor res;
    Context ctx;
    DatabaseHelper db;

    public MyAdapter(Context ctx, Cursor res)
    {
        this.db=new DatabaseHelper(ctx);
        this.ctx=ctx;
        this.res=res;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.eachrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return res.getCount();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        res.moveToPosition(position);
        holder.activityname.setText(res.getString(1));
        holder.investedtime.setText(res.getString(2));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res.moveToPosition(position);
                db.deleteData(res.getString(0));
                final Activity a = (Activity)ctx;
                Fragment fragment = new ActivitiesFragment();
                FragmentTransaction fragmentTransaction =((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
       /* holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView activityname,investedtime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityname= itemView.findViewById(R.id.activityname);
            investedtime= itemView.findViewById(R.id.investedtime);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }
}
