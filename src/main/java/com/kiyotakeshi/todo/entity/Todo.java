package com.kiyotakeshi.todo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動インクリメントで一意の値を生成
	// @GeneratedValue(generator = "todo_id_gen")
	// @SequenceGenerator(name = "todo_id_gen", sequenceName = "todo_id_seq",
	// allocationSize = 1)
	private Long id;

	private String activityName;

	// TODO:
	// private boolean done;

	// TODO:
	// private enum color;
	private String color;

	private String category;

	public Todo(String activityName, String color, String category) {
		this.activityName = activityName;
		this.color = color;
		this.category = category;
	}

	protected Todo() {
	}

	public Long getId() {
		return id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Todo{" + "id=" + id + ", activityName='" + activityName + '\'' + ", color='" + color + '\''
				+ ", category='" + category + '\'' + '}';
	}

}
