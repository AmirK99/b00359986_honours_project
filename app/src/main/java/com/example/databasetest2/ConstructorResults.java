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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ConstructorResults extends AppCompatActivity {

    ConstraintLayout constructorConstraintLayout;

    RecyclerView constructorRecyclerView;
    FloatingActionButton addConstructorButton;

    ImageView noConstructorData_image;
    TextView noConstructorData_txt;

    MyDatabaseHelper myDB;
    ArrayList<String> teamID, teamName, teamPoints;
    ConstructorCustomAdapter constructorCustomAdapter;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor_results);

        constructorRecyclerView = findViewById(R.id.constructorRecyclerView);
        addConstructorButton = findViewById(R.id.addConstructorButton);
        noConstructorData_image = findViewById(R.id.noConstructor_imageView);
        noConstructorData_txt = findViewById(R.id.no_constructor_data);
        constructorConstraintLayout = findViewById(R.id.constructorConstraintLayout);

        load_Setting();

        addConstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructorResults.this, AddConstructor.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(ConstructorResults.this);
        teamID = new ArrayList<>();
        teamName = new ArrayList<>();
        teamPoints = new ArrayList<>();

        storeConstructorDataInArrays();

        constructorCustomAdapter = new ConstructorCustomAdapter(ConstructorResults.this,
                this, teamID, teamName, teamPoints);
        constructorRecyclerView.setAdapter(constructorCustomAdapter);
        constructorRecyclerView.setLayoutManager(new LinearLayoutManager(ConstructorResults.this));


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.teamResults);

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
                        startActivity(new Intent(getApplicationContext(), DriverResults.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.teamResults:
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
    }

    private void load_Setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nightModeCheckBox = sp.getBoolean("NIGHT", false);
        if (nightModeCheckBox) {
            constructorConstraintLayout.setBackgroundColor(Color.parseColor("#808080"));
            constructorRecyclerView.setBackgroundColor(Color.parseColor("#808080"));
            noConstructorData_image.setBackgroundColor(Color.parseColor("#ffffff"));
            noConstructorData_txt.setTextColor(Color.parseColor("#ffffff"));
        } else {
            constructorConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            constructorRecyclerView.setBackgroundColor(Color.parseColor("#ffffff"));
            noConstructorData_image.setBackgroundColor(Color.parseColor("#000000"));
            noConstructorData_txt.setTextColor(Color.parseColor("#000000"));
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

    void storeConstructorDataInArrays(){
        Cursor cursor = myDB.readAllConstructorData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
            noConstructorData_image.setVisibility(View.VISIBLE);
            noConstructorData_txt.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                teamID.add(cursor.getString(0));
                teamName.add(cursor.getString(1));
                teamPoints.add(cursor.getString(2));
            }
            noConstructorData_image.setVisibility(View.GONE);
            noConstructorData_txt.setVisibility(View.GONE);
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
        builder.setTitle("Delete All Teams?");
        builder.setMessage("Are you sure you want to delete all teams from the Constructor's " +
                "standings ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(ConstructorResults.this);
                myDB.deleteAllTeams();
                Intent intent = new Intent(ConstructorResults.this, ConstructorResults.class);
                startActivity(intent);
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