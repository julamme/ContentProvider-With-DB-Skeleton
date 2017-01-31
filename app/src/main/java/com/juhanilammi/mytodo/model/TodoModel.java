package com.juhanilammi.mytodo.model;

import java.io.Serializable;

public class TodoModel implements Serializable{

    private int id;
    private String title;
    private String message;

    public TodoModel() {
    }

    public TodoModel(int id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
