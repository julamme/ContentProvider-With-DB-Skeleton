package com.juhanilammi.mytodo.data;

import com.juhanilammi.mytodo.model.TodoModel;

import java.util.ArrayList;
import java.util.List;



public class TodoHolder {
    
    private static TodoHolder instance = null;
    private List<TodoModel> todoList;

    public TodoHolder() {
        todoList = new ArrayList<>();
    }

    public static TodoHolder getInstance() {
        if(instance == null) {
            return instance = new TodoHolder();
        } else {
            return instance;
        }
    }
    
    public List<TodoModel> getItems() {
        return todoList;
    }
    
    public void addToList(TodoModel item) {
        todoList.add(item); 
    } 
}
