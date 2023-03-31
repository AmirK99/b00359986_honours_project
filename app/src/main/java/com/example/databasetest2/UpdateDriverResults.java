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

public class UpdateDriverResults extends AppCompatActivity {

    EditText driver_name_input, driver_team_input, driver_points_input;
    Button updateResultsButton, deleteDriverResultsButton;

    String id, name, team, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_driver_results);

        driver_name_input = findViewById(R.id.driver_name_input_update);
        driver_team_input = findViewById(R.id.driver_team_input_update);
        driver_points_input = findViewById(R.id.driver_points_input_update);
        updateResultsButton = findViewById(R.id.updateDriverResultsButton);
        deleteDriverResultsButton = findViewById(R.id.deleteDriverResultsButton);
        //First the get and set data method
        getAndSetDriverIntentData();

        //sets action bar title after getAndSetDriverIntent method has been executed
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(name);
        }

        updateResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //and once we have the data, we can update it using the method below
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateDriverResults.this);
                name = driver_name_input.getText().toString().trim();
                team = driver_team_input.getText().toString().trim();
                points = driver_points_input.getText().toString().trim();
                myDB.updateDriverData(id, name, team, points);
            }
        });

        deleteDriverResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }
    void getAndSetDriverIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("team") && getIntent().hasExtra("points")){
            //getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            team = getIntent().getStringExtra("team");
            points = getIntent().getStringExtra("points");

            //setting intent data
            driver_name_input.setText(name);
            driver_team_input.setText(team);
            driver_points_input.setText(points);

        }else {
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name +  " from results ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateDriverResults.this);
                myDB.deleteOneDriverRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}