package com.juhanilammi.mytodo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.juhanilammi.mytodo.data.TodoContentProvider;
import com.juhanilammi.mytodo.data.TodoTable;
import com.juhanilammi.mytodo.model.TodoModel;

public class MainActivity extends AppCompatActivity{

    Button beginButton;
    Button newTodoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beginButton = (Button) findViewById(R.id.begin_button);
        newTodoButton = (Button) findViewById(R.id.button_new_todo);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
        newTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                final EditText text = new EditText(MainActivity.this);
                dialogBuilder.setView(text);
                dialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TodoModel todo = new TodoModel();
                        todo.setTitle(text.getText().toString());
                        todo.setMessage(text.getText().toString());

                        save(todo);
                    }
                });
                dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    private void save(TodoModel todo) {
        Log.i("TODO", " "+todo.getTitle()+" "+todo.getMessage());
        String title = todo.getTitle();
        String message = todo.getMessage();

        ContentValues values = new ContentValues();
        values.put(TodoTable.COLUMN_MESSAGE, message);
        values.put(TodoTable.COLUMN_TITLE, title);

        getContentResolver().insert(TodoContentProvider.CONTENT_URI, values);
    }
}
