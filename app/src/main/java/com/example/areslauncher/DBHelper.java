package com.example.areslauncher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * HELPS ON MANAGING THE DATABASE ON THE APPLICATION
 */
public class DBHelper extends SQLiteOpenHelper {

    //VARIABLES

    private static final String TAG = DBHelper.class.getSimpleName();

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
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // id will auto-increment if no value passed
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
                    KEY_ID_COMMON + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // id will auto-increment if no value passed
                    KEY_NAME_GAME2048 + " TEXT, " +
                    KEY_HIGHSCORE_GAME2048 + " INTEGER, " +
                    KEY_GAME_TIME_GAME2048 + " TEXT );";
    ;


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
                    KEY_HIGHSCORE_GAMEPEG + " INTEGER, " +
                    KEY_GAME_TIME_GAMEPEG + " TEXT );";




    //Database access variables.
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    //CONSTRUCTOR

    /**
     * Constructor that calls "super"
     * @param context - Context
     */
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //OVERRIDES
    /**
     * Override of onCreate
     * Creates all the tables
     * @param db - SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(GAME2048_TABLE_CREATE);
        db.execSQL(GAMEPEG_TABLE_CREATE);
    }

    /**
     * Override of onUpgrade
     * Drops the old tables and calls onCreate
     * @param db - SQLiteDatabase
     * @param oldVersion - int
     * @param newVersion - int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //Drop tables
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GAME2048_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GAMEPEG_TABLE);
        onCreate(db);
    }


    //METHODS

    /**
     * Inserts a new user in Users
     * @param name - String
     * @param password - String
     */
    public void insertUser(String name, String password){
        long newId = 0;
        //Create the values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_USERS, name);
        values.put(KEY_PASSWORD_USERS, password);
        try {
            //get the database on writable mode
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //insert query
            newId = mWritableDB.insert(USERS_TABLE, null, values);
            if (newId == 0){
                throw new Exception("Error, nothing inserted");
            }
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
    }


    /**
     * Looks up if a user exists
     * @param user - String
     * @param password - String
     * @return boolean - user found
     */
    public boolean isUser(String user, String password) {
        boolean userFound = false;
        //Set query clauses up
        String[] columns = new String[]{KEY_NAME_USERS, KEY_PASSWORD_USERS};
        String whereClause = KEY_NAME_USERS + " = ? AND " + KEY_PASSWORD_USERS  + " = ?";
        String[] whereArgs = new String[] {
                user,
                password
        };
        Cursor cursor = null;

        try {
            //get readable database
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            //execute select query
            cursor = mReadableDB.query(USERS_TABLE, columns, whereClause, whereArgs,
                    null, null, null);
            //See if user is found
            if (cursor.getCount() == 1) userFound = true;
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            //close resources
            if (cursor!= null) {
                cursor.close();
            }
            return userFound;
        }
    }


    /**
     * Inserts a score in the peg table
     * @param scoreModel - ScoreModel
     * @return long
     */
    public long insertScorePeg(ScoreModel scoreModel){
        long newId = 0;
        //contents to insert
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_GAMEPEG, scoreModel.getUser());
        values.put(KEY_HIGHSCORE_GAMEPEG, scoreModel.getHighScore());
        values.put(KEY_GAME_TIME_GAMEPEG, scoreModel.getTime());

        try {
            //get writable database
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //insert query
            newId = mWritableDB.insert(GAMEPEG_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;

    }


    /**
     * Selects a user from the peg table
     * @param user - String
     * @return ScoreModel
     */
    public ScoreModel selectUserPeg(String user){
        //set up query clauses
        ScoreModel scoreModel = new ScoreModel();
        String[] columns = new String[]{KEY_NAME_GAMEPEG, KEY_HIGHSCORE_GAMEPEG, KEY_GAME_TIME_GAMEPEG};
        String whereClause = KEY_NAME_GAMEPEG + " = ?";
        String[] whereArgs = new String[] {
                user
        };
        Cursor cursor = null;

        try {
            //get readable database
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            //execute select query
            cursor = mReadableDB.query(GAMEPEG_TABLE, columns, whereClause, whereArgs,
                    null, null, null);
            //Fill up the ScoreModel with the query information
            if(cursor!=null){
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    scoreModel.setUser(cursor.getString(0));
                    scoreModel.setHighScore(cursor.getInt(1));
                    scoreModel.setTime(cursor.getString(2));
                }else{
                    scoreModel = null;

                }
            }else{
                scoreModel = null;

            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            //close cursor
            if (cursor!= null) {
                cursor.close();
            }
            return scoreModel;
        }
    }


    /**
     * Gets all the users and its scores from the score peg table.
     * @param orderBy - DBHelper.OrderBy
     * @return ArrayList<ScoreModel>
     */
    public ArrayList<ScoreModel> selectAllUsersPeg(DBHelper.OrderBy orderBy){
        //Set up args
        ArrayList<ScoreModel> scoreModels = new ArrayList<>();
        String[] columns = new String[]{KEY_NAME_GAMEPEG, KEY_HIGHSCORE_GAMEPEG, KEY_GAME_TIME_GAMEPEG};
        Cursor cursor = null;
        String orderByParam;
        switch (orderBy){
            case USER:
                orderByParam = KEY_NAME_GAMEPEG + " ASC";
                break;
            case HIGHSCORE:
                orderByParam = KEY_HIGHSCORE_GAMEPEG + " ASC";

                break;
            case TIME:
                orderByParam = KEY_GAME_TIME_GAMEPEG + " ASC";
                break;
            default:
                orderByParam = null;
                break;
        }


        try {
            //get readable database
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            cursor = mReadableDB.query(GAMEPEG_TABLE, columns, null, null,
                    null, null, orderByParam);
            //fill the scoremodels up and insert them in the arraylist
            while (cursor.moveToNext()) {
                ScoreModel scoreModel = new ScoreModel();
                scoreModel.setUser(cursor.getString(0));
                scoreModel.setHighScore(cursor.getInt(1));
                scoreModel.setTime(cursor.getString(2));
                scoreModels.add(scoreModel);
            }

        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            //close cursor
            if (cursor!= null) {
                cursor.close();
            }
            return scoreModels;
        }

    }

    /**
     * Deletes a user score from the peg table
     * @param oldScore - ScoreModel
     * @return long
     */

    public long deleteScorePeg(ScoreModel oldScore) {
        long newId = 0;
        //query args
        String[] whereArgs = new String[]{oldScore.getUser()};
        String whereClause = KEY_NAME_GAMEPEG + " =?";
        try {
            //get writable database
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //Execute delete query
            newId = mWritableDB.delete(GAMEPEG_TABLE,whereClause,whereArgs);

        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }

        return newId;
    }

    /**
     * Gets all the users and its scores from the score 2048 table.
     * @param orderBy - OrderBy
     * @return ArrayList of ScoreModel
     */
    public ArrayList<ScoreModel> selectAllUsers2048(OrderBy orderBy) {
        //create the clauses
        ArrayList<ScoreModel> scoreModels = new ArrayList<>();
        String[] columns = new String[]{KEY_NAME_GAME2048, KEY_HIGHSCORE_GAME2048, KEY_GAME_TIME_GAME2048};
        Cursor cursor = null;

        String orderByParam;
        switch (orderBy){
            case USER:
                orderByParam = KEY_NAME_GAME2048 + " ASC";
                break;
            case HIGHSCORE:
                orderByParam = KEY_HIGHSCORE_GAME2048 + " DESC";

                break;
            case TIME:
                orderByParam = KEY_GAME_TIME_GAME2048 + " ASC";
                break;
            default:
                orderByParam = null;
                break;
        }

        try {
            //get readable database
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            //make select query
            cursor = mReadableDB.query(GAME2048_TABLE, columns, null, null,
                    null, null, orderByParam);

            //put the cursor information in the scoremodel and this in the list
            while (cursor.moveToNext()) {
                ScoreModel scoreModel = new ScoreModel();
                scoreModel.setUser(cursor.getString(0));
                scoreModel.setHighScore(cursor.getInt(1));
                scoreModel.setTime(cursor.getString(2));
                scoreModels.add(scoreModel);
            }

        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            //close cursor
            if (cursor!= null) {
                cursor.close();
            }
            return scoreModels;
        }
    }


    /**
     * Select a user from the 2048 table
     * @param user - String
     * @return ScoreModel
     */
    public ScoreModel selectUser2048(String user) {
        //create the clauses
        ScoreModel scoreModel = new ScoreModel();
        String[] columns = new String[]{KEY_NAME_GAME2048, KEY_HIGHSCORE_GAME2048, KEY_GAME_TIME_GAME2048};
        String whereClause = KEY_NAME_GAME2048 + " = ?";
        String[] whereArgs = new String[] {
                user
        };
        Cursor cursor = null;

        try {
            //get the database in readable mode
            if (mReadableDB == null) mReadableDB = getReadableDatabase();
            //make the select query
            cursor = mReadableDB.query(GAME2048_TABLE, columns, whereClause, whereArgs,
                    null, null, null);

            //get the cursor information into scoremodel
            if(cursor!=null){
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    scoreModel.setUser(cursor.getString(0));
                    scoreModel.setHighScore(cursor.getInt(1));
                    scoreModel.setTime(cursor.getString(2));
                }else{
                    scoreModel = null;

                }
            }else{
                scoreModel = null;

            }
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION: " + e.getMessage());

        } finally {
            //close cursor
            if (cursor!= null) {
                cursor.close();
            }
            return scoreModel;
        }
    }


    /**
     * delete a score from 2048 table
     * @param oldScore - ScoreModel
     * @return long
     */
    public long  deleteScore2048(ScoreModel oldScore) {
        long newId = 0;
        //prepare clauses
        String[] whereArgs = new String[]{oldScore.getUser()};
        String whereClause = KEY_NAME_GAME2048 + " =?";
        try {
            //get database in writable mode
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //make delete query
            newId = mWritableDB.delete(GAME2048_TABLE,whereClause,whereArgs);

        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    /**
     * insert a score from 2048 table
     * @param scoreModel
     */
    public void insertScore2048(ScoreModel scoreModel) {
        long newId = 0;
        //prepare values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_GAME2048, scoreModel.getUser());
        values.put(KEY_HIGHSCORE_GAME2048, scoreModel.getHighScore());
        values.put(KEY_GAME_TIME_GAME2048, scoreModel.getTime());

        try {
            //get the writable database
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //execute the insert query
            newId = mWritableDB.insert(GAME2048_TABLE, null, values);
            if (newId == 0){
                throw new Exception("Error, nothing inserted");
            }
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }

    }

    /**
     * Change the password of a user from the user table
     * @param user - String
     * @param password - String
     * @return int
     */
    public int changePassword(String user, String password){
        int newId=0;
        //prepare values
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD_USERS, password);

        try {
            //get writable database
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //update query
            newId = mWritableDB.update(USERS_TABLE, values, KEY_NAME_USERS + " =? ", new String[]{user});

            if (newId == 0){
                throw new Exception("Error, nothing inserted");
            }
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }


        return newId;
    }


    /**
     * changes a username from the user table and so the other related tables.
     * @param username - String
     * @param newUsername - String
     * @return int
     */
    public int changeUsername(String username, String newUsername){
        int newId=0;
        //prepare values
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_USERS, newUsername);

        try {
            //get writable database
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //execute update
            newId = mWritableDB.update(USERS_TABLE, values, KEY_NAME_USERS + " =? ", new String[]{username});

            if (newId == 0){
                throw new Exception("Error, nothing inserted");
            }
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }

        //change the username on the 2048 table
        ScoreModel scoreModel2048 = selectUser2048(username);
        if (scoreModel2048 != null){
            deleteScore2048(scoreModel2048);
            scoreModel2048.setUser(newUsername);
            insertScore2048(scoreModel2048);
        }

        //change the username on the peg table
        ScoreModel scoreModelPeg = selectUserPeg(username);
        if (scoreModelPeg != null){
            deleteScorePeg(scoreModelPeg);
            scoreModelPeg.setUser(newUsername);
            insertScorePeg(scoreModelPeg);
        }

        return newId;
    }

    /**
     * Deletes a user form user tabeles and the related tables
     * @param user
     * @return
     */
    public long deleteUser(String user){
        //create args
        long newId = 0;
        String[] whereArgs = new String[]{user};
        String whereClause = KEY_NAME_USERS + " =?";
        try {
            //get the database in writable mode
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            //execute delete
            newId = mWritableDB.delete(USERS_TABLE,whereClause,whereArgs);
            if (newId == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }

        //delete it on the peg and 2048 tables too
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setUser(user);
        deleteScore2048(scoreModel);
        deleteScorePeg(scoreModel);
        return newId;
    }


    /**
     * Control the type of orderby that will execute
     */
    enum OrderBy{
        USER,
        HIGHSCORE,
        TIME
    }



}
