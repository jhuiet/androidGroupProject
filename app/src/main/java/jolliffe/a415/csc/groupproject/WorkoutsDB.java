package jolliffe.a415.csc.groupproject;

/**
 * Created by jacob on 4/11/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WorkoutsDB {
    // database constants
    public static final String DATABASE_NAME = "workouts.db";
    public static final int DB_VERSION = 1;

    //user table constants
    public static final String WORKOUTS_TABLE = "workouts";

    public static final String WORKOUTS_COLUMN_REPS = "reps";

    public static final int WORKOUTS_REPS_COL = 0;
    public static final String WORKOUTS_COLUMN_SETS = "sets";
    public static final int WORKOUTS_SETS_COL = 1;
    public static final String WORKOUTS_COLUMN_WEIGHT ="weight";
    public static final int WORKOUTS_WEIGHT_COL = 2;
    public static final String WORKOUTS_COLUMN_DATE = "date";
    public static final int WORKOUTS_DATE_COL = 3;
    public static final String WORKOUTS_COLUMN_WORKOUT_TYPE = "workoutType";
    public static final int WORKOUTS_WORKOUT_TYPE_COL = 4;
    public static final String WORKOUTS_COLUMN_WORKOUT_ID = "workoutID";
    public static final int WORKOUTS_ID_COL = 5;

    public static final String CREATE_WORKOUT_TABLE =
            "CREATE TABLE " + WORKOUTS_TABLE + " (" +
                    WORKOUTS_COLUMN_WORKOUT_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                    WORKOUTS_COLUMN_WORKOUT_TYPE + " STRING NOT NULL, " +
                    WORKOUTS_COLUMN_REPS + " INTEGER, " +
                    WORKOUTS_COLUMN_SETS + " INTEGER, " +
                    WORKOUTS_COLUMN_WEIGHT + " INTEGER, " +
                    WORKOUTS_COLUMN_DATE + " STRING NOT NULL);";

    public static final String DROP_USERS_TABLE =
            "DROP TABLE IF EXISTS " + WORKOUTS_TABLE;

    public static class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_WORKOUT_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("User list", "Upgrading db from version " + oldVersion +"to " + newVersion);

            //db.execSQL(USERS_TABLE

            //Drop Statements needed here.

        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    //constructor
    public WorkoutsDB(Context context){
        dbHelper = new DBHelper(context, DATABASE_NAME, null, DB_VERSION);
    }

    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }
    private void openWriteableDB(){
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    public long insertWorkout(Workout workout){
        ContentValues cv = new ContentValues();
       // cv.put(WORKOUTS_COLUMN_WORKOUT_ID, workout.getWorkoutId());
        cv.put(WORKOUTS_COLUMN_WORKOUT_TYPE, workout.getWorkoutType());
        cv.put(WORKOUTS_COLUMN_REPS, workout.getReps());
        cv.put(WORKOUTS_COLUMN_SETS, workout.getSets());
        cv.put(WORKOUTS_COLUMN_DATE, workout.getWorkoutDate());
        cv.put(WORKOUTS_COLUMN_WEIGHT, workout.getWeight());

        this.openWriteableDB();
        long rowID = db.insertOrThrow(WORKOUTS_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public void clearDatabase(String TABLE_NAME) {
        String clearDBQuery = "DELETE FROM "+ WORKOUTS_TABLE;
        db.execSQL(clearDBQuery);
    }

    public int updateWorkout(Workout workout){
        ContentValues cv = new ContentValues();
        cv.put(WORKOUTS_COLUMN_WORKOUT_ID, workout.getWorkoutId());
        cv.put(WORKOUTS_COLUMN_WORKOUT_TYPE, workout.getWorkoutType());
        cv.put(WORKOUTS_COLUMN_REPS, workout.getReps());
        cv.put(WORKOUTS_COLUMN_SETS, workout.getSets());
        cv.put(WORKOUTS_COLUMN_WEIGHT, workout.getWeight());
        cv.put(WORKOUTS_COLUMN_DATE, workout.getWorkoutDate());

        String where = WORKOUTS_COLUMN_WORKOUT_ID + "= ?";
        String[] whereArgs = { String.valueOf(workout.getWorkoutId())};

        this.openWriteableDB();
        int rowCount = db.update(WORKOUTS_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;


    }

    public int deleteWorkout(int workoutId){
        String where = WORKOUTS_COLUMN_WORKOUT_ID + "=?}";
        String[] whereArgs = { String.valueOf(workoutId)};

        this.openWriteableDB();
        int rowCount = db.delete(WORKOUTS_TABLE, where, whereArgs);

        this.closeDB();

        return rowCount;

    }


    public ArrayList<Workout> getAllWorkouts() {

        this.openReadableDB();
        Cursor cursor = db.query(WORKOUTS_TABLE, null, null, null, null, null, WORKOUTS_COLUMN_DATE+" DESC");
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        while (cursor.moveToNext()) {
            workouts.add(getWorkoutsFromCursor(cursor));
        }

        if (cursor != null) {
            cursor.close();
        }
        this.closeDB();
        return workouts;

    }


    public static Workout getWorkoutsFromCursor(Cursor cursor){
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Workout workout = new Workout(
                        cursor.getInt(WORKOUTS_REPS_COL),
                        cursor.getInt(WORKOUTS_SETS_COL),
                        cursor.getInt(WORKOUTS_WEIGHT_COL),
                        cursor.getInt(WORKOUTS_ID_COL),
                        cursor.getString(WORKOUTS_DATE_COL),
                        cursor.getString(WORKOUTS_WORKOUT_TYPE_COL)
                        );
                return workout;

            } catch (Exception e) {
                return null;
            }
        }
    }

    public  ArrayList<Workout> getWorkoutWithType(String workoutType){

        String where =
                WORKOUTS_COLUMN_WORKOUT_TYPE + " = ?";
        String[] whereArgs = { workoutType};

        this.openReadableDB();
        Cursor cursor = db.query(WORKOUTS_TABLE, null, where, whereArgs, null, null, null );
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        while (cursor.moveToNext()) {
            workouts.add(getWorkoutsFromCursor(cursor));
        }

        if (cursor != null) {
            cursor.close();
        }
        this.closeDB();
        return workouts;

    }



}
