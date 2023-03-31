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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConstructorCustomAdapter extends RecyclerView.Adapter<ConstructorCustomAdapter.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList teamID, teamName, teamPoints;



    ConstructorCustomAdapter(Activity activity, Context context, ArrayList teamID, ArrayList teamName,
                             ArrayList teamPoints){
        this.activity = activity;
        this.context = context;
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamPoints = teamPoints;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_constructor, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.constructor_id_txt.setText(String.valueOf(teamID.get(position)));
        holder.constructor_name_txt.setText(String.valueOf(teamName.get(position)));
        holder.constructor_points_txt.setText(String.valueOf(teamPoints.get(position)));
        holder.constructorMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateConstructor.class);
                intent.putExtra("teamID", String.valueOf(teamID.get(position)));
                intent.putExtra("teamName", String.valueOf(teamName.get(position)));
                intent.putExtra("teamPoints", String.valueOf(teamPoints.get(position)));
               activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return teamID.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView constructor_id_txt, constructor_name_txt, constructor_points_txt;
        LinearLayout constructorMainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constructor_id_txt = itemView.findViewById(R.id.constructor_id_txt);
            constructor_name_txt = itemView.findViewById(R.id.constructor_name_txt);
            constructor_points_txt = itemView.findViewById(R.id.constructor_points_txt);
            constructorMainLayout = itemView.findViewById(R.id.constructorMainLayout);
        }
    }
}
