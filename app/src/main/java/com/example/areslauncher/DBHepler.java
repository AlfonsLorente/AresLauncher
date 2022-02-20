package com.example.areslauncher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHepler extends SQLiteOpenHelper {
    private static final String TAG = DBHepler.class.getSimpleName();

    //Database basic
    public static final String DB_NAME = "ARES_LAUCHER_DB";
    public static final int DB_VERSION = 1;

    //Database common id - PK, AUTOINCREMENT
    public static final String KEY_ID_COMMON = "_id";


    //Database user table
    private static final String USERS_TABLE = "USERS";
    public static final String KEY_NAME_USERS = "User";
    public static final String KEY_PASSWORD_USERS = "Password";
        private static final String[] COLUMNS_USERS = { KEY_ID_COMMON, KEY_NAME_USERS, KEY_PASSWORD_USERS };
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY, " + // id will auto-increment if no value passed
                    KEY_NAME_USERS + " TEXT, " +
                    KEY_PASSWORD_USERS + " TEXT );";



    //Database game2048 table
    private static final String GAME2048_TABLE = "GAME_2048";
    public static final String KEY_NAME_GAME2048 = "User";
    public static final String KEY_HIGHSCORE_GAME2048 = "Highscore";
    public static final String KEY_GAME_TIME_GAME2048 = "Game_Time";
    private static final String[] COLUMNS_GAME2048 = {KEY_ID_COMMON, KEY_NAME_GAME2048, KEY_HIGHSCORE_GAME2048, KEY_GAME_TIME_GAME2048 };
    private static final String GAME2048_TABLE_CREATE =
            "CREATE TABLE " + GAME2048_TABLE + " (" +
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY, " + // id will auto-increment if no value passed
                    KEY_NAME_GAME2048 + " TEXT, " +
                    KEY_HIGHSCORE_GAME2048 + " TEXT, " +
                    KEY_GAME_TIME_GAME2048 + " TEXT );";


    //Database gamePeg table
    private static final String GAMEPEG_TABLE = "GAME_PEG";
    public static final String KEY_NAME_GAMEPEG = "User";
    public static final String KEY_HIGHSCORE_GAMEPEG = "Highscore";
    public static final String KEY_GAME_TIME_GAMEPEG = "Game_Time";
    private static final String[] COLUMNS_GAMEPEG = {KEY_ID_COMMON, KEY_NAME_GAMEPEG, KEY_HIGHSCORE_GAMEPEG, KEY_GAME_TIME_GAMEPEG};
    private static final String GAMEPEG_TABLE_CREATE =
            "CREATE TABLE " + GAMEPEG_TABLE + " (" +
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY, " + // id will auto-increment if no value passed
                    KEY_NAME_GAMEPEG + " TEXT, " +
                    KEY_HIGHSCORE_GAMEPEG + " TEXT, " +
                    KEY_GAME_TIME_GAMEPEG + " TEXT );";




    //Database access variables.
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public DBHepler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
