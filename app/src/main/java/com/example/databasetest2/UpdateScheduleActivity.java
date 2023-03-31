package com.example.databasetest2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateScheduleActivity extends AppCompatActivity {

    EditText grand_prix_update, circuit_update, gp_date_update;
    Button update_race_details_button, delete_race_button;

    String race_id, grandPrix, circuit, dateOfGP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        grand_prix_update = findViewById(R.id.grand_prix_update);
        circuit_update = findViewById(R.id.circuit_update);
        gp_date_update = findViewById(R.id.gp_date_update);
        update_race_details_button = findViewById(R.id.update_race_details_button);
        delete_race_button = findViewById(R.id.delete_race_button);

        //The data is first gathered and set using the method below
        getAndSetIntentDataSchedule();

        //sets the title for the action bar after getAndSetIntentData method is called
        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setTitle(grandPrix);
        }


        update_race_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //once the data has been collected it can then be updated by calling the method below
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateScheduleActivity.this);
                grandPrix = grand_prix_update.getText().toString().trim();
                circuit = circuit_update.getText().toString().trim();
                dateOfGP = gp_date_update.getText().toString().trim();
                myDB.updateScheduleData(race_id, grandPrix, circuit, dateOfGP);
            }
        });
        delete_race_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialogSchedule();
            }
        });
    }

    void getAndSetIntentDataSchedule(){
        if (getIntent().hasExtra("race_id") && getIntent().hasExtra("grandPrix") &&
        getIntent().hasExtra("circuit") && getIntent().hasExtra("dateOfGP")){
           //getting data from Intent
            race_id = getIntent().getStringExtra("race_id");
            grandPrix = getIntent().getStringExtra("grandPrix");
            circuit = getIntent().getStringExtra("circuit");
            dateOfGP = getIntent().getStringExtra("dateOfGP");

            //setting intent data
            grand_prix_update.setText(grandPrix);
            circuit_update.setText(circuit);
            gp_date_update.setText(dateOfGP);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialogSchedule(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + grandPrix + " ?");
        builder.setMessage("Are you sure you want to delete " + grandPrix + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateScheduleActivity.this);
                myDB.deleteOneScheduleRow(race_id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //does nothing
            }
        });
        builder.create().show();
    }
}