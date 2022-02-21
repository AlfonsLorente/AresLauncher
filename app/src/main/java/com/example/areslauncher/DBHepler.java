package com.example.areslauncher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static final String KEY_NAME_USERS = "User";
    private static final String KEY_PASSWORD_USERS = "Password";
    private static final String[] COLUMNS_USERS = { KEY_ID_COMMON, KEY_NAME_USERS, KEY_PASSWORD_USERS };
    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY, " + // id will auto-increment if no value passed
                    KEY_NAME_USERS + " TEXT, " +
                    KEY_PASSWORD_USERS + " TEXT );";



    //Database game2048 table
    public static final String GAME2048_TABLE = "GAME_2048";
    private static final String KEY_NAME_GAME2048 = "User";
    private static final String KEY_HIGHSCORE_GAME2048 = "Highscore";
    private static final String KEY_GAME_TIME_GAME2048 = "Game_Time";
    private static final String[] COLUMNS_GAME2048 = {KEY_ID_COMMON, KEY_NAME_GAME2048, KEY_HIGHSCORE_GAME2048, KEY_GAME_TIME_GAME2048 };
    private static final String GAME2048_TABLE_CREATE =
            "CREATE TABLE " + GAME2048_TABLE + " (" +
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY, " + // id will auto-increment if no value passed
                    KEY_NAME_GAME2048 + " TEXT, " +
                    KEY_HIGHSCORE_GAME2048 + " TEXT, " +
                    KEY_GAME_TIME_GAME2048 + " TEXT );";


    //Database gamePeg table
    public static final String GAMEPEG_TABLE = "GAME_PEG";
    private static final String KEY_NAME_GAMEPEG = "User";
    private static final String KEY_HIGHSCORE_GAMEPEG = "Highscore";
    private static final String KEY_GAME_TIME_GAMEPEG = "Game_Time";
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
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(GAME2048_TABLE_CREATE);
        db.execSQL(GAMEPEG_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHepler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GAME2048_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GAMEPEG_TABLE);
        onCreate(db);
    }

    public long insertUser(String name, String password){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_USERS, name);
        values.put(KEY_PASSWORD_USERS, password);
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(USERS_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public boolean isUser(String user, String password) {
        boolean userFound = false;
        String[] columns = new String[]{KEY_NAME_USERS, KEY_PASSWORD_USERS};
        String whereClause = KEY_NAME_USERS + " = ? AND " + KEY_PASSWORD_USERS  + " = ?";
        String[] whereArgs = new String[] {
                user,
                password
        };
        Cursor cursor = null;

        try {
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            cursor = mReadableDB.query(USERS_TABLE, columns, whereClause, whereArgs,
                    null, null, null);
            if (cursor.getCount() == 1) userFound = true;
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            cursor.close();
            return userFound;
        }
    }

    public long insertScorePeg(ScoreModel scoreModel){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_GAMEPEG, scoreModel.getUser());
        values.put(KEY_HIGHSCORE_GAMEPEG, scoreModel.getHighScore());
        values.put(KEY_GAME_TIME_GAMEPEG, scoreModel.getTime());
        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(GAMEPEG_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;

    }


    public ScoreModel selectUserPeg(String user){
        ScoreModel scoreModel = new ScoreModel();
        String[] columns = new String[]{KEY_NAME_GAMEPEG, KEY_HIGHSCORE_GAMEPEG, KEY_GAME_TIME_GAMEPEG};
        String whereClause = KEY_NAME_USERS + " = ?";
        String[] whereArgs = new String[] {
                user
        };
        Cursor cursor = null;

        try {
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            cursor = mReadableDB.query(USERS_TABLE, columns, whereClause, whereArgs,
                    null, null, null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                scoreModel.setUser(cursor.getString(0));
                scoreModel.setHighScore(Integer.parseInt(cursor.getString(1)));
                scoreModel.setTime(cursor.getString(2));
            }else{
                scoreModel.setHighScore(-99);

            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            cursor.close();
            return scoreModel;
        }
    }






}
