package com.juhanilammi.mytodo.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

public class TodoContentProvider extends ContentProvider {

    private MySQLiteHelper db;

    private static final int TODOS = 1;
    private static final int TODO_ID = 2;
    private static final String AUTHORITY = "com.juhanilammi.mytodo.data.contentprovider";
    private static final String BASE_PATH = "todolist";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/todolist";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todo";

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
        mUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
    }


    @Override
    public boolean onCreate() {
        db = new MySQLiteHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] args, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //validate
        String[] available = {TodoTable.COLUMN_ID, TodoTable.COLUMN_MESSAGE, TodoTable.COLUMN_TITLE};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            if (availableColumns.containsAll(requestedColumns)) {
                queryBuilder.setTables(TodoTable.TABLE_TODO);
                int uriType = mUriMatcher.match(uri);

                switch (uriType) {
                    case TODOS:
                        break;
                    case TODO_ID:
                        queryBuilder.appendWhere(TodoTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown URI " + uri);
                }
                SQLiteDatabase database = db.getWritableDatabase();
                Cursor cursor = queryBuilder.query(database, projection, selection, args, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);

                return cursor;

            } else {
                throw new IllegalArgumentException("Unknown columns");
            }
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int uriType = mUriMatcher.match(uri);
        SQLiteDatabase DB = db.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case TODOS:
                id = DB.insert(TodoTable.TABLE_TODO, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = mUriMatcher.match(uri);
        SQLiteDatabase DB = db.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case TODOS:
                rowsDeleted = DB.delete(TodoTable.TABLE_TODO, selection,
                        selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = DB.delete(
                            TodoTable.TABLE_TODO,
                            TodoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = DB.delete(
                            TodoTable.TABLE_TODO,
                            TodoTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = mUriMatcher.match(uri);
        SQLiteDatabase DB = db.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case TODOS:
                rowsUpdated = DB.update(TodoTable.TABLE_TODO,
                        values,
                        selection,
                        selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = DB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = DB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
