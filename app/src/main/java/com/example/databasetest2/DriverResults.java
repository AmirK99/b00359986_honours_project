package com.example.databasetest2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DriverResults extends AppCompatActivity {

    ConstraintLayout driverConstraintLayout;

    RecyclerView driverRecyclerView;
    FloatingActionButton addDriverButton;
    ImageView empty_table_imageView;
    TextView no_driver_data;

    MyDatabaseHelper myDB;
    ArrayList<String> driver_id, driver_name, driver_team, driver_points;

    DriverCustomAdapter driverCustomAdapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_results);

        driverRecyclerView = findViewById(R.id.driverRecyclerView);
        addDriverButton = findViewById(R.id.addDriverButton);
        empty_table_imageView = findViewById(R.id.noDriver_imageView);
        no_driver_data = findViewById(R.id.no_driver_data);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.driverResults);
        driverConstraintLayout = findViewById(R.id.driverConstraintLayout);

        load_Setting();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.schedule:
                        startActivity(new Intent(getApplicationContext(), Schedule.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.driverResults:
                        return true;
                    case R.id.teamResults:
                        startActivity(new Intent(getApplicationContext(), ConstructorResults.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.rules:
                        startActivity(new Intent(getApplicationContext(), Rules.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        addDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverResults.this, AddDriver.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(DriverResults.this);
        driver_id = new ArrayList<>();
        driver_name = new ArrayList<>();
        driver_team = new ArrayList<>();
        driver_points = new ArrayList<>();

        storeDriverDataInArrays();

        driverCustomAdapter = new DriverCustomAdapter(DriverResults.this, this, driver_id,
                driver_name, driver_team, driver_points);
        driverRecyclerView.setAdapter(driverCustomAdapter);
        driverRecyclerView.setLayoutManager(new LinearLayoutManager(DriverResults.this));

    }

    private void load_Setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nightModeCheckBox = sp.getBoolean("NIGHT", false);
        if (nightModeCheckBox) {
            driverConstraintLayout.setBackgroundColor(Color.parseColor("#808080"));
            driverRecyclerView.setBackgroundColor(Color.parseColor("#808080"));
            empty_table_imageView.setBackgroundColor(Color.parseColor("#ffffff"));
            no_driver_data.setTextColor(Color.parseColor("#ffffff"));
        } else {
            driverConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            driverRecyclerView.setBackgroundColor(Color.parseColor("#ffffff"));
            empty_table_imageView.setBackgroundColor(Color.parseColor("#000000"));
            no_driver_data.setTextColor(Color.parseColor("#000000"));
        }

        String orientation = sp.getString("ORIENTATION", "false");
        if ("1".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        } else if ("2".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if ("3".equals(orientation)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    void storeDriverDataInArrays(){
        Cursor cursor = myDB.readAllDriverData();
        if (cursor.getCount() == 0){
           empty_table_imageView.setVisibility(View.VISIBLE);
           no_driver_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                driver_id.add(cursor.getString(0));
                driver_name.add(cursor.getString(1));
                driver_team.add(cursor.getString(2));
                driver_points.add(cursor.getString(3));
                empty_table_imageView.setVisibility(View.GONE);
                no_driver_data.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.preference:
                Intent intent = new Intent(this, Preferences.class);
                startActivity(intent);
                return true;
            case R.id.delete_all:
                confirmDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        load_Setting();
        super.onResume();
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all Driver Results?");
        builder.setMessage("Are you sure you want to delete all Driver Results?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(DriverResults.this);
                myDB.deleteAllDrivers();
                //refreshes the recycler view
                Intent intent = new Intent(DriverResults.this, DriverResults.class);
                startActivity(intent);
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