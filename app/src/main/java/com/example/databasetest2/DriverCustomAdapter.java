package com.example.databasetest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DriverCustomAdapter extends RecyclerView.Adapter<DriverCustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList driver_id, driver_name, driver_team, driver_points;

    int position;

    DriverCustomAdapter(Activity activity, Context context, ArrayList driver_id, ArrayList driver_name,
                        ArrayList driver_team, ArrayList driver_points){
        this.activity = activity;
        this.context = context;
        this.driver_id = driver_id;
        this.driver_name = driver_name;
        this.driver_team = driver_team;
        this.driver_points = driver_points;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.my_row_driver, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int driverPosition) {
        holder.driver_id_txt.setText(String.valueOf(driver_id.get(driverPosition)));
        holder.driver_name_txt.setText(String.valueOf(driver_name.get(driverPosition)));
        holder.driver_team_txt.setText(String.valueOf(driver_team.get(driverPosition)));
        holder.driver_points_txt.setText(String.valueOf(driver_points.get(driverPosition)));
        holder.driverMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDriverResults.class);
                intent.putExtra("id", String.valueOf(driver_id.get(driverPosition)));
                intent.putExtra("name", String.valueOf(driver_name.get(driverPosition)));
                intent.putExtra("team", String.valueOf(driver_team.get(driverPosition)));
                intent.putExtra("points", String.valueOf(driver_points.get(driverPosition)));
                activity.startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return driver_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView driver_id_txt, driver_name_txt, driver_team_txt, driver_points_txt;
        LinearLayout driverMainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            driver_id_txt = itemView.findViewById(R.id.driver_id_txt);
            driver_name_txt = itemView.findViewById(R.id.driver_name_txt);
            driver_team_txt = itemView.findViewById(R.id.driver_team_txt);
            driver_points_txt = itemView.findViewById(R.id.driver_points_txt);
            driverMainLayout = itemView.findViewById(R.id.driverMainLayout);
        }
    }
}
