package com.example.areslauncher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHepler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    public static String DB_NAME = "AresLaucherDB";
    public static String USERS_TABLE = "USERS";
    public static String _colUsersId = "id";
    public static String colUsersName = "commentidno";
    public static String colUsersPassword = "commentTitle";
    public static String colCommentBody = "commentBody";
    private final ArrayList<CommentModel> commentList = new ArrayList<CommentModel>();
    private final Context context;

    public DBHepler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists " + COMMENT_TABLE + "("
                + _colCommentId + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + colCommentId + " TEXT,"
                + colCommentTitle + " TEXT ,"
                + colCommentBody + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        android.util.Log.w("Constants", "Upgrading database, which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE);
        onCreate(db);
    }

    //add
    public void addComment(CommentModel comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colCommentId, comment.idNumber);
        contentValues.put(colCommentTitle, comment.title);
        contentValues.put(colCommentBody, comment.body);
        db.insert(COMMENT_TABLE, null, contentValues);
        db.close();
    }


    // update
    public void updateComment(CommentModel comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colCommentTitle, comment.title);
        contentValues.put(colCommentBody, comment.body);
        try {
            String[] args = {comment.idNumber};
            db.update(COMMENT_TABLE, contentValues, colCommentId + "=?", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    //empty
    public void emptyComment() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + COMMENT_TABLE);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //remeove
    public void removeComment(String commentid) {
        try {
            String[] args = {commentid};
            // SQLiteDatabase db = this.getWritableDatabase();
            // db.execSQL("delete from producttable where productidno=?,  args);
            // db.close();
            getWritableDatabase().delete(COMMENT_TABLE, colCommentId + "=?", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //getComments
    public ArrayList<CommentModel> getComments() {
        commentList.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + COMMENT_TABLE, null);
        while (cursor.moveToNext()) {
            CommentModel comment = new CommentModel();
            comment.idNumber = cursor.getString(cursor.getColumnIndexOrThrow(colCommentId));
            comment.title = cursor.getString(cursor.getColumnIndexOrThrow(colCommentTitle));
            comment.body = cursor.getString(cursor.getColumnIndexOrThrow(colCommentBody));
            commentList.add(comment);
        }
        cursor.close();
        db.close();
        return commentList;
    }


}
