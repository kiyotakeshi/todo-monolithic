package com.kiyotakeshi.todo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 自動インクリメントで一意の値を生成
	// sequence を指定して作成する場合
	// @GeneratedValue(generator = "todo_id_gen")
	// @SequenceGenerator(name = "todo_id_gen", sequenceName = "todo_id_seq",
	// allocationSize = 1)
	private Long id;

	@NotNull
	private String activityName;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Progress progress;

	@Enumerated(EnumType.STRING)
	private Category category;

	private String label;

	public Todo(@NotNull String activityName, @NotNull Progress progress, Category category, String label) {
		this.activityName = activityName;
		this.progress = progress;
		this.category = category;
		this.label = label;
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

	public Progress getProgress() {
		return progress;
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "Todo{" + "id=" + id + ", activityName='" + activityName + '\'' + ", progress=" + progress + ", colors="
				+ category + ", category='" + category + '\'' + '}';
	}

}
