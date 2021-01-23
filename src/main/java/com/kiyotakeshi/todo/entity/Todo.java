package com.kiyotakeshi.todo.entity;

import javax.persistence.*;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動インクリメントで一意の値を生成
	// sequence を指定して作成する場合
	// @GeneratedValue(generator = "todo_id_gen")
	// @SequenceGenerator(name = "todo_id_gen", sequenceName = "todo_id_seq",
	// allocationSize = 1)
	private Long id;

	private String activityName;

	@Enumerated(EnumType.STRING)
	 private Progress progress;

	@Enumerated(EnumType.STRING)
	private Color color;

	private String category;

	public Todo(String activityName, String category) {
		// Todo 生成時のステータスは TODO
		this.progress = Progress.TODO;
		this.activityName = activityName;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Todo{" +
				"id=" + id +
				", activityName='" + activityName + '\'' +
				", progress=" + progress +
				", colors=" + color +
				", category='" + category + '\'' +
				'}';
	}
}
