package com.example.databasetest2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateConstructor extends AppCompatActivity {

    EditText constructorNameInput, constructorPointsInput;
    Button updateConstructorButton, deleteConstructorButton;

    String teamID, teamName, teamPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_constructor);

        constructorNameInput = findViewById(R.id.constructorNameInputUpdate);
        constructorPointsInput = findViewById(R.id.constructorPointsInputUpdate);
        updateConstructorButton = findViewById(R.id.updateConstructorButton);
        deleteConstructorButton = findViewById(R.id.deleteConstructorButton);

        getAndSetIntentData();

        //sets action bar title to the title of the item in recycler view
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(teamName);
        }

        updateConstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateConstructor.this);
                teamName = constructorNameInput.getText().toString().trim();
                teamPoints = constructorPointsInput.getText().toString().trim();
                myDB.updateConstructorData(teamID, teamName, teamPoints);
            }
        });

        deleteConstructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("teamID") && getIntent().hasExtra("teamName")
                && getIntent().hasExtra("teamPoints")){
            //getting data from intent
            teamID = getIntent().getStringExtra("teamID");
            teamName = getIntent().getStringExtra("teamName");
            teamPoints = getIntent().getStringExtra("teamPoints");

            //setting intent data
            constructorNameInput.setText(teamName);
            constructorPointsInput.setText(teamPoints);
        }else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + teamName +  " from results ?");
        builder.setMessage("Are you sure you want to delete " + teamName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateConstructor.this);
                myDB.deleteOneConstructorRow(teamID);
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