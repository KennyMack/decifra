package com.example.jonathan.decifraapp.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by otequio on 02/06/16.
 */
public class CreateDatabase extends SQLiteOpenHelper {

    private static final String DATABASE = "decifra.db";

    public static int getVERSION() {
        return VERSION;
    }

    public static String getDATABASE() {
        return DATABASE;
    }

    public static String getTABLE() {
        return TABLE;
    }

    public static String getID() {
        return ID;
    }

    public static String getIDAPI() {
        return IDAPI;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getARTIST() {
        return ARTIST;
    }

    public static String getTYPE() {
        return TYPE;
    }

    public static String getTAB() {
        return TAB;
    }

    private static final String TABLE = "cipher";
    private static final String ID = "_id";
    private static final String IDAPI = "idapi";
    private static final String NAME = "name";
    private static final String ARTIST = "artist";
    private static final String TYPE = "type";
    private static final String TAB = "tab";
    private static final int VERSION = 1;

    public CreateDatabase(Context context){
        super(context, DATABASE, null,  VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + ID + " integer primary key autoincrement,"
                + IDAPI + " text,"
                + NAME + " text,"
                + ARTIST + " text,"
                + TAB + " text,"
                + TYPE + " string"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }
}
