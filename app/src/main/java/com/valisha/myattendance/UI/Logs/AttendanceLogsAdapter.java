package com.valisha.myattendance.UI.Logs;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.valisha.myattendance.R;
import com.valisha.myattendance.model.Logs;

import java.util.ArrayList;

public class AttendanceLogsAdapter extends  RecyclerView.Adapter<AttendanceLogsAdapter.ExampleViewHolder> {


    private ArrayList<Logs> logs;
    Logs currentItem;
    private Context context;




    public AttendanceLogsAdapter(ArrayList<Logs> logsArrayList, Context context) {
         this.context = context;
        this.logs = logsArrayList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dlist,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        currentItem = logs.get(position);
        holder.name.setText(currentItem.getUserName());


        Log.e("TAG", "onBindViewHolder: "+currentItem.getUserName() );
        String punch = currentItem.getTime()+","+currentItem.getDate()+" | "+currentItem.getLat()+","+currentItem.getLng();
        holder.punch.setText(punch);

        if(!currentItem.getStatus().equals("synced"))
        {
            holder.dp.setBackgroundResource(R.drawable.baseline_person_red_24);
        }
        else
            holder.dp.setBackgroundResource(R.drawable.baseline_person_24);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder  {

         public TextView name,punch;
         public ImageView dp;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.log_user_name);
            punch = (TextView) itemView.findViewById(R.id.log_punch);
            dp = (ImageView) itemView.findViewById(R.id.log_user_dp);


        }


    }

    public void filterList(ArrayList<Logs> headsArrayList) {
        logs = headsArrayList;
        notifyDataSetChanged();
    }







}
