package com.example.beermanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "dataBaseOne.db", null, 1);
    }

    private static final String TIMES_TABLE = "TIMES_TABLE";
    private static final String DAYS_TABLE = "DAYS_TABLE";
    private static final String COLUMN_DATE_INFO = "DATE_INFO";
    private static final String COLUMN_TIME_INFO = "TIME_INFO";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_IS_LAST = "IS_LAST";
    private static final String COLUMN_CURRENT = "CURRENT";
    private static final String COLUMN_GOAL = "GOAL";
    private static final String COLUMN_ACHIEVED = "ACHIEVED";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTIMES = "CREATE TABLE " + TIMES_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TIME_INFO + " TEXT )";

                String createTableDAYS = "CREATE TABLE " + DAYS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DATE_INFO + " TEXT, "
                + COLUMN_CURRENT + " INTEGER, "
                + COLUMN_GOAL + " INTEGER, "
                + COLUMN_IS_LAST + " INTEGER, "
                + COLUMN_ACHIEVED + " INTEGER )";

        db.execSQL(createTableDAYS);
        db.execSQL(createTableTIMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //vola set v nastaveni cile
    public boolean addOneDay(DayInfo newDay){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE_INFO, newDay.getDate());
        cv.put(COLUMN_CURRENT, newDay.getCurrent());
        cv.put(COLUMN_GOAL, newDay.getGoal());
        cv.put(COLUMN_IS_LAST, 1);
        if(newDay.getTrue()) {
            cv.put(COLUMN_ACHIEVED, 1);
        }
        else {
            cv.put(COLUMN_ACHIEVED, 0);
        }
        long insert = db.insert(DAYS_TABLE, null, cv);
        db.close();

        return (insert != -1);
    }
    //vola add v pridej pivo
    public boolean addOneBeer(TimeInfo newBeer){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME_INFO, newBeer.getTime());

        long insert = db.insert(TIMES_TABLE, null, cv);
        db.close();
        return (insert != -1);
    }
    //vola oncreat v last day
    public List<TimeInfo> getAllBeers(){
        List<TimeInfo> returnList = new ArrayList<>();

        String selectFromTable = "SELECT * FROM " + TIMES_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectFromTable, null);

        int poradi = 1;
        if(cursor.moveToFirst()){
            do{
                int myID = cursor.getInt(0);
                String myTime = cursor.getString(1);

                TimeInfo beer = new TimeInfo(poradi, myTime);
                returnList.add(beer);
                poradi++;

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;

    }
    //vola show all/oncreat v historii
    public List<DayInfo> getAllDays(){
        List<DayInfo> returnList = new ArrayList<>();

        String selectFromTable = "SELECT  * FROM " + DAYS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectFromTable, null);

        if(cursor.moveToFirst()){
            do{
                int myID = cursor.getInt(0);
                String myDate = cursor.getString(1);
                int curr = cursor.getInt(2);
                int goal = cursor.getInt(3);
                boolean isLast = cursor.getInt(4) == 1;

                DayInfo day = new DayInfo(myID, myDate, goal, curr, isLast);
                returnList.add(day);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;

    }
    //vola show good v historii
    public List<DayInfo> getGoodDays(){
        List<DayInfo> returnList = new ArrayList<>();

        String selectFromTable = "SELECT  * FROM " + DAYS_TABLE + " WHERE " + COLUMN_ACHIEVED + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectFromTable, null);

        if(cursor.moveToFirst()){
            do{
                int myID = cursor.getInt(0);
                String myDate = cursor.getString(1);
                int curr = cursor.getInt(2);
                int goal = cursor.getInt(3);
                boolean isLast = cursor.getInt(4) == 1;

                DayInfo day = new DayInfo(myID, myDate, goal, curr, isLast);
                returnList.add(day);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnList;

    }
    //vola show bad v historii
    public List<DayInfo> getBadDays(){
        List<DayInfo> returnList = new ArrayList<>();

        String selectFromTable = "SELECT  * FROM " + DAYS_TABLE + " WHERE " + COLUMN_ACHIEVED + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectFromTable, null);

        if(cursor.moveToFirst()){
            do{
                int myID = cursor.getInt(0);
                String myDate = cursor.getString(1);
                int curr = cursor.getInt(2);
                int goal = cursor.getInt(3);
                boolean isLast = cursor.getInt(4) == 1;

                DayInfo day = new DayInfo(myID, myDate, goal, curr, isLast);
                returnList.add(day);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;

    }
    //vola delete all v historii
    public void deleteDays(){
        SQLiteDatabase db = this.getWritableDatabase();
        String drop = "DELETE FROM " + DAYS_TABLE;
        db.execSQL(drop);
        db.close();

    }
    //vola set v nastaveni cile
    public void deleteBeers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String drop = "DELETE FROM " + TIMES_TABLE;
        db.execSQL(drop);
        db.close();
    }
    //vola set v nastaveni cile
    public void updateIsLast(){
        SQLiteDatabase db = this.getWritableDatabase();
        String upgradeWholeTable = "UPDATE " + DAYS_TABLE + " SET " + COLUMN_IS_LAST + " = 0";
        db.execSQL(upgradeWholeTable);
        db.close();
    }
    //vola add v pridej pivo
    public void updateCurrent(){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateWholeTable = "UPDATE " + DAYS_TABLE + " SET " + COLUMN_CURRENT + " = " + COLUMN_CURRENT + " + 1 WHERE " + COLUMN_IS_LAST + " = 1";
        db.execSQL(updateWholeTable);
        db.close();
    }
    //vola add v pridej pivo
    public void updateAchieved(){

        SQLiteDatabase db = this.getWritableDatabase();
        String updateAchievement = "UPDATE " + DAYS_TABLE + " SET " + COLUMN_ACHIEVED + " = 1 WHERE " + COLUMN_IS_LAST + " = 1";
        db.execSQL(updateAchievement);
        db.close();

    }
    //vola oncreate/add v pridej pivo
    public DayInfo getToday(){
        DayInfo day = new DayInfo(0, "ERROR", 0, 0, true);
        SQLiteDatabase db = this.getReadableDatabase();
        String getOneDay = "SELECT * FROM " + DAYS_TABLE + " WHERE " + COLUMN_IS_LAST + " = 1";

        Cursor cursor = db.rawQuery(getOneDay, null);

        if(cursor.moveToFirst()){
            do{
                int myID = cursor.getInt(0);
                String myDate = cursor.getString(1);
                int curr = cursor.getInt(2);
                int goal = cursor.getInt(3);
                boolean isLast = cursor.getInt(4) == 1;

                day = new DayInfo(myID, myDate, goal, curr, isLast);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return day;

    }
    //vola oncreate v nastaveni cile
    public boolean isThere(String thisDay){

        int returnInt;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT COUNT(*) FROM " + DAYS_TABLE + " WHERE " + COLUMN_DATE_INFO + " = " + "'" + thisDay + "'";
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            returnInt = cursor.getInt(0);
        }
        else
            returnInt = 0;
        cursor.close();
        db.close();

        return returnInt == 1;
    }

}