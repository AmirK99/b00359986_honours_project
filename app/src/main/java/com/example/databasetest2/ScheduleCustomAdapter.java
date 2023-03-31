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

public class ScheduleCustomAdapter extends RecyclerView.Adapter<ScheduleCustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList race_id, grandPrix, circuit, dateOfGP;

    ScheduleCustomAdapter(Activity activity, Context context, ArrayList race_id, ArrayList grandPrix, ArrayList circuit,
                          ArrayList dateOfGP){
        this.activity = activity;
        this.context = context;
        this.race_id = race_id;
        this.grandPrix = grandPrix;
        this.circuit = circuit;
        this.dateOfGP = dateOfGP;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.race_id_txt.setText(String.valueOf(race_id.get(position)));
        holder.grand_prix_txt.setText(String.valueOf(grandPrix.get(position)));
        holder.circuit_txt.setText(String.valueOf(circuit.get(position)));
        holder.gp_date_txt.setText(String.valueOf(dateOfGP.get(position)));
        holder.mainLayoutSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateScheduleActivity.class);
                intent.putExtra("race_id", String.valueOf(race_id.get(position)));
                intent.putExtra("grandPrix", String.valueOf(grandPrix.get(position)));
                intent.putExtra("circuit", String.valueOf(circuit.get(position)));
                intent.putExtra("dateOfGP", String.valueOf(dateOfGP.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return race_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView race_id_txt, grand_prix_txt, circuit_txt, gp_date_txt;
        LinearLayout mainLayoutSchedule;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            race_id_txt = itemView.findViewById(R.id.race_id_txt);
            grand_prix_txt = itemView.findViewById(R.id.grand_prix_txt);
            circuit_txt = itemView.findViewById(R.id.circuit_txt);
            gp_date_txt = itemView.findViewById(R.id.gp_date_txt);
            mainLayoutSchedule = itemView.findViewById(R.id.mainLayoutSchedule);
        }
    }
}
