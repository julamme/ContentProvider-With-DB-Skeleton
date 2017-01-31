package com.juhanilammi.mytodo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.juhanilammi.mytodo.data.TodoContentProvider;
import com.juhanilammi.mytodo.data.TodoTable;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 23441;
    private RecyclerView list;
    private TodoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Uri uri = Uri.parse(TodoContentProvider.CONTENT_URI + "/");
        list = (RecyclerView) findViewById(R.id.testing);
        list.setLayoutManager(new LinearLayoutManager(this));
        getContent(uri);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void getContent(Uri uri) {
        String[] projection = {TodoTable.COLUMN_TITLE, TodoTable.COLUMN_MESSAGE, TodoTable.COLUMN_ID};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
      /*  List<String> strings = new ArrayList<>();
        if(cursor != null) {
            cursor.moveToLast();
            int length = cursor.getCount();
            cursor.moveToFirst();
            for(int i = 0; i < length; i++){
                strings.add(cursor.getString(i));
            }
            Log.i("TEST", ""+strings.size());
          //  String category = cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_TITLE));
           // text.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_TITLE)));

            cursor.close();*/
     //  }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {TodoTable.COLUMN_ID, TodoTable.COLUMN_MESSAGE, TodoTable.COLUMN_TITLE};
        CursorLoader cursorLoader = new CursorLoader(this, TodoContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<String> strings = new ArrayList<>();
        data.moveToFirst();

        while (!data.isAfterLast()) {
            Log.i("TEST 2", data.getString(1));
            strings.add(data.getString(1));
            data.moveToNext();
        }
        data.close();
       /* data.moveToLast();
            int length = data.getCount();
            data.moveToFirst();
            for(int i = 0; i < length; i++){
                Log.i("TEST", data.getString(1));
            }
            Log.i("TEST", ""+strings);*/
            //  String category = cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_TITLE));
            // text.setText(cursor.getString(cursor.getColumnIndexOrThrow(TodoTable.COLUMN_TITLE)));

          //  data.close();
        Log.i("TEST", ""+strings);
        adapter = new TodoAdapter(strings, ListActivity.this);
        list.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        list.setAdapter(new TodoAdapter(null, this));
    }


}
