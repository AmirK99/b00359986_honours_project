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

public class Schedule extends AppCompatActivity {

    RecyclerView recyclerViewSchedule;
    FloatingActionButton add_gp_button;
    ImageView empty_imageview;
    TextView no_data;

    ConstraintLayout scheduleConstraintLayout;

    BottomNavigationView bottomNavigationView;

    MyDatabaseHelper myDB;
    ArrayList<String> race_id, grandPrixName, circuit, dateOfGP;
    ScheduleCustomAdapter scheduleCustomAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        recyclerViewSchedule = findViewById(R.id.recyclerViewSchedule);
        add_gp_button = findViewById(R.id.add_gp_button);
        empty_imageview = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.schedule);
        scheduleConstraintLayout = findViewById(R.id.scheduleConstraintLayout);

        load_Setting();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.schedule:
                        return true;
                    case R.id.driverResults:
                        startActivity(new Intent(getApplicationContext(), DriverResults.class));
                        overridePendingTransition(0, 0);
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

        add_gp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Schedule.this, AddRaceToScheduleActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(Schedule.this);
        race_id = new ArrayList<>();
        grandPrixName = new ArrayList<>();
        circuit = new ArrayList<>();
        dateOfGP = new ArrayList<>();

        storeScheduleDataInArrays();

        scheduleCustomAdapter = new ScheduleCustomAdapter(Schedule.this,this, race_id, grandPrixName, circuit
                , dateOfGP);
        recyclerViewSchedule.setAdapter(scheduleCustomAdapter);
        recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(Schedule.this));

    }

    private void load_Setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nightModeCheckBox = sp.getBoolean("NIGHT", false);
        if (nightModeCheckBox) {
            scheduleConstraintLayout.setBackgroundColor(Color.parseColor("#808080"));
           recyclerViewSchedule.setBackgroundColor(Color.parseColor("#808080"));
           empty_imageview.setBackgroundColor(Color.parseColor("#ffffff"));
           no_data.setTextColor(Color.parseColor("#ffffff"));
        } else {
            scheduleConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            recyclerViewSchedule.setBackgroundColor(Color.parseColor("#ffffff"));
            empty_imageview.setBackgroundColor(Color.parseColor("#000000"));
            no_data.setTextColor(Color.parseColor("#000000"));
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

    void storeScheduleDataInArrays() {
        Cursor cursor = myDB.readAllRaceData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                race_id.add(cursor.getString(0));
                grandPrixName.add(cursor.getString(1));
                circuit.add(cursor.getString(2));
                dateOfGP.add(cursor.getString(3));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
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
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Schedule.this);
                myDB.deleteAllRaces();
                //refreshes the recycler view after items are deleted
                Intent intent = new Intent(Schedule.this, Schedule.class);
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