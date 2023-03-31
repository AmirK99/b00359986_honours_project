package com.example.databasetest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddConstructor extends AppCompatActivity {

    EditText constructorNameInput, constructorPointsInput;
    Button saveConstructorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constructor);

        constructorNameInput = findViewById(R.id.constructorNameInput);
        constructorPointsInput = findViewById(R.id.constructorPointsInput);
        saveConstructorButton = findViewById(R.id.saveConstructorButton);
        saveConstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddConstructor.this);
                myDB.addConstructor(constructorNameInput.getText().toString().trim(),
                        Integer.valueOf(constructorPointsInput.getText().toString().trim()));
            }
        });
    }
}