package com.kiyotakeshi.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue
    private Long id;
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

    public Long getId() {
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

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", color='" + color + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
