package com.example.databasetest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRaceToScheduleActivity extends AppCompatActivity {

    EditText grand_prix_input, circuit_input, gp_date_input;
    Button add_gp_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_race_to_schedule);

        grand_prix_input = findViewById(R.id.grand_prix_input);
        circuit_input = findViewById(R.id.circuit_input);
        gp_date_input = findViewById(R.id.gp_date_input);
        add_gp_button = findViewById(R.id.add_gp_button);
        add_gp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddRaceToScheduleActivity.this);
                myDB.addRace(grand_prix_input.getText().toString().trim(),
                        circuit_input.getText().toString().trim(), gp_date_input.getText().toString().trim());
            }
        });
    }
}