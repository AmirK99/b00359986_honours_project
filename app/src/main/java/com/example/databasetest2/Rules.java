package com.example.databasetest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rules extends AppCompatActivity {

    ConstraintLayout rulesConstraintLayout;
    BottomNavigationView bottomNavigationView;

    Button btnSportingRegs, btnTechnicalRegs, btnFinancialRegs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.rules);
        rulesConstraintLayout = findViewById(R.id.rulesConstraintLayout);
        btnSportingRegs = findViewById(R.id.sportingRegsButton);
        btnTechnicalRegs = findViewById(R.id.technicalRegsButton);
        btnFinancialRegs = findViewById(R.id.financialRegsButton);

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
                        return true;
                }
                return false;
            }
        });


        btnSportingRegs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent rulesLink = new Intent(Intent.ACTION_VIEW);
                rulesLink.setData(Uri.parse("https://www.fia.com/sites/default/files" +
                        "/fia_2026_formula_1_sporting_regulations_pu_-_issue_2_-_2023-03-03.pdf"));
                startActivity(rulesLink);
            }
        });


        btnTechnicalRegs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent rulesLink = new Intent(Intent.ACTION_VIEW);
                rulesLink.setData(Uri.parse("https://www.fia.com/sites/default/files/" +
                        "fia_2023_formula_1_technical_regulations_-_issue_5_-_2023-02-22.pdf"));
                startActivity(rulesLink);
            }
        });


        btnFinancialRegs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent rulesLink = new Intent(Intent.ACTION_VIEW);
                rulesLink.setData(Uri.parse("https://www.fia.com/sites/default/files/" +
                        "fia_formula_1_financial_regulations_iss.14_.pdf"));
                startActivity(rulesLink);
            }
        });
    }

    private void load_Setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean nightModeCheckBox = sp.getBoolean("NIGHT", false);
        if (nightModeCheckBox) {
            rulesConstraintLayout.setBackgroundColor(Color.parseColor("#808080"));

        } else {
            rulesConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alternate_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId() == R.id.preference){
             Intent intent = new Intent(this, Preferences.class);
             startActivity(intent);
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        load_Setting();
        super.onResume();
    }
}