package com.kiyotakeshi.todo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    private final int id;
    private String activityName;
    private String color;
    private String category;

    Todo(int id, String activityName, String color, String category) {
        this.id = id;
        this.activityName = activityName;
        this.color = color;
        this.category = category;
    }

    protected Todo(){
        this.id = 0;
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
