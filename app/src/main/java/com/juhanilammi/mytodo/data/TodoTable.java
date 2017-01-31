package com.juhanilammi.mytodo.data;

import android.database.sqlite.SQLiteDatabase;


public class TodoTable {

    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_TITLE = "title";

    private static final String DATABASE_CREATE = "create table "+TABLE_TODO
            +"( "+COLUMN_ID+" integer primary key autoincrement, "
            +COLUMN_TITLE+" text not null, "
            +COLUMN_MESSAGE+" text not null);";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }
}
