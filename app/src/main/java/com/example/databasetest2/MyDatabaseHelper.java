package com.example.databasetest2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "F1DatabaseNew.db";
    private static final int DATABASE_VERSION = 3;

    //schema for schedule table
    private static final String SCHEDULE_TABLE = "schedule";
    private static final String RACE_ID = "race_id";
    private static final String COLUMN_GRAND_PRIX = "grand_prix_name";
    private static final String COLUMN_CIRCUIT = "circuit_name";
    private static final String COLUMN_GP_DATE = "gp_date";

    //schema for driver table
    private static final String DRIVER_TABLE = "driver_table";
    private static final String COLUMN_DRIVER_ID = "driver_id";
    private static final String COLUMN_DRIVER_NAME = "driver_name";
    private static final String COLUMN_DRIVER_TEAM = "driver_team";
    private static final String COLUMN_DRIVER_POINTS = "driver_points";

    //schema for driver table
    private static final String CONSTRUCTORS_TABLE = "constructors_table";
    private static final String COLUMN_TEAM_ID = "team_id";
    private static final String COLUMN_TEAM_NAME = "team_name";
    private static final String COLUMN_TEAM_POINTS = "team_points";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + SCHEDULE_TABLE + " ("
                + RACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_GRAND_PRIX + " TEXT, " +
                COLUMN_CIRCUIT + " TEXT, " + COLUMN_GP_DATE + " INTEGER" + ")";
        String queryDriver= "CREATE TABLE " + DRIVER_TABLE + " ("
                + COLUMN_DRIVER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DRIVER_NAME + " TEXT, " +
                COLUMN_DRIVER_TEAM + " TEXT, " + COLUMN_DRIVER_POINTS + " INTEGER" + ")";
        String queryConstructor= "CREATE TABLE " + CONSTRUCTORS_TABLE + " ("
                + COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TEAM_NAME + " TEXT, "
                + COLUMN_TEAM_POINTS + " INTEGER" + ")";
        db.execSQL(query);
        db.execSQL(queryDriver);
        db.execSQL(queryConstructor);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DRIVER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CONSTRUCTORS_TABLE);
        onCreate(db);
    }
    void addRace(String grandPrix, String circuit, String dateOfGP){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_GRAND_PRIX, grandPrix);
        cv.put(COLUMN_CIRCUIT, circuit);
        cv.put(COLUMN_GP_DATE, dateOfGP);
        long result = db.insert(SCHEDULE_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Data added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addDriver(String driverName, String driverTeam, int driverPoints){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvDriver = new ContentValues();

        cvDriver.put(COLUMN_DRIVER_NAME, driverName);
        cvDriver.put(COLUMN_DRIVER_TEAM, driverTeam);
        cvDriver.put(COLUMN_DRIVER_POINTS, driverPoints);

        long result = db.insert(DRIVER_TABLE, null, cvDriver);
        if (result == -1){
            Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Data added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addConstructor(String teamName, int teamPoints){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TEAM_NAME, teamName);
        cv.put(COLUMN_TEAM_POINTS, teamPoints);
        long result = db.insert(CONSTRUCTORS_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Data added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllRaceData(){
        String query = "SELECT * FROM " + SCHEDULE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db !=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllDriverData(){
        String query = "SELECT * FROM " + DRIVER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
           cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }

   Cursor readAllConstructorData(){
      String query = "SELECT * FROM " + CONSTRUCTORS_TABLE;
      SQLiteDatabase db = this.getReadableDatabase();

      Cursor cursor = null;
      if (db != null){
          cursor = db.rawQuery(query, null);
      }
      return cursor;
   }

    void updateScheduleData(String scheduleRow_id, String grandPrix, String circuit, String dateOfGP){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GRAND_PRIX, grandPrix);
        cv.put(COLUMN_CIRCUIT, circuit);
        cv.put(COLUMN_GP_DATE, dateOfGP);

        long result = db.update(SCHEDULE_TABLE, cv, "race_id=?", new String[]{scheduleRow_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Update was Successful!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDriverData(String driver_row_id, String name, String team, String points){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DRIVER_NAME, name);
        cv.put(COLUMN_DRIVER_TEAM, team);
        cv.put(COLUMN_DRIVER_POINTS, points);

        long result = db.update(DRIVER_TABLE, cv, "driver_id=?",
                new String[]{driver_row_id});
        if (result == -1){
            Toast.makeText(context, "Update Failed.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateConstructorData(String constructorRow_id, String teamName, String teamPoints){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEAM_NAME, teamName);
        cv.put(COLUMN_TEAM_POINTS, teamPoints);

        long result = db.update(CONSTRUCTORS_TABLE, cv, "team_id=?",
                new String[]{constructorRow_id});
        if (result == -1){
            Toast.makeText(context, "Update Failed.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }


    void deleteOneScheduleRow(String scheduleRow_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(SCHEDULE_TABLE, "race_id=?", new String[]{scheduleRow_id});
        if (result == -1){
            Toast.makeText(context, "Item could not be deleted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Item was successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneDriverRow(String driver_row_id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(DRIVER_TABLE, "driver_id=?", new String[]{driver_row_id});
        if (result == -1){
            Toast.makeText(context, "Item could not be deleted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Item was successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneConstructorRow(String constructorRow_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(CONSTRUCTORS_TABLE, "team_id=?",
                new String[]{constructorRow_id});
        if (result == -1){
            Toast.makeText(context, "Item could not be deleted", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Item was successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllRaces(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + SCHEDULE_TABLE);
    }

    void deleteAllDrivers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DRIVER_TABLE);
    }

    void deleteAllTeams(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CONSTRUCTORS_TABLE);
    }
}
