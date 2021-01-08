package com.sudheersuri.investtime;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    Cursor res;
    Context ctx;

    public SummaryAdapter(Context ctx, Cursor res)
    {
        this.ctx=ctx;
        this.res=res;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.summaryeachrow,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return res.getCount();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        res.moveToPosition(position);
        holder.activityname.setText(res.getString(0));
        holder.investedtime.setText(res.getString(1));
       /* holder.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView activityname,investedtime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityname= itemView.findViewById(R.id.activityname);
            investedtime= itemView.findViewById(R.id.investedtime);
        }
    }
}
