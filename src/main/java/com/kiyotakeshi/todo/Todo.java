package com.kiyotakeshi.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue
    private int id;
    private String activityName;
    private String color;
    private String category;

    public Todo(String activityName, String color, String category) {
        this.activityName = activityName;
        this.color = color;
        this.category = category;
    }

    protected Todo(){
    }

    public int getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }
}
