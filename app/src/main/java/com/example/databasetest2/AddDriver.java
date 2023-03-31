package com.example.databasetest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddDriver extends AppCompatActivity {

    EditText driver_name_input, driver_team_input, driver_points_input;
    Button saveDriverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        driver_name_input = findViewById(R.id.driver_name_input);
        driver_team_input = findViewById(R.id.driver_team_input);
        driver_points_input = findViewById(R.id.driver_points_input);
        saveDriverButton = findViewById(R.id.saveDriverButton);
        saveDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddDriver.this);
                myDB.addDriver(driver_name_input.getText().toString().trim(),
                        driver_team_input.getText().toString().trim(),
                        Integer.valueOf(driver_points_input.getText().toString().trim()));

            }
        });
    }
}