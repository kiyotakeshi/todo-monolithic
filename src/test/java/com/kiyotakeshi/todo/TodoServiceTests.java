package com.kiyotakeshi.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class TodoServiceTests {

	@Autowired
	TodoRepository repository;

	@Test
	void findAll() {
		List<Todo> todoList = this.repository.findAll();
		System.out.println("Todo List");
		todoList.stream().forEach(System.out::println);
		assertThat(todoList).isNotEmpty();
	}

	@Test
	void findById() {
		var todo = this.repository.findById(1000L).orElseThrow();
		assertThat(todo.getActivityName()).isEqualTo("go to supermarket");
	}

	@Test
	void save() {
		int before = this.repository.findAll().size();
		var todo = new Todo("sleep", "rainbow", "free");
		this.repository.save(todo);
		int after = this.repository.findAll().size();
		assertThat(before + 1).isEqualTo(after);
	}

	@Test
	void findByCategory() {
		List<Todo> todoByCategory = this.repository.findByCategory("hobby");
		assertThat(todoByCategory).hasSize(1);
		assertThat(todoByCategory.get(0).getCategory()).isEqualTo("hobby");
	}

	@Test
	void updateTodo() {
		var todo = this.repository.findById(1001L).orElseThrow();
		todo.setActivityName("update");
		todo.setColor("red");
		todo.setCategory("free");
		this.repository.save(todo);

		var newTodo = this.repository.findById(1001L).orElseThrow();
		assertThat(newTodo.getActivityName()).isEqualTo("update");
		assertThat(newTodo.getColor()).isEqualTo("red");
		assertThat(newTodo.getCategory()).isEqualTo("free");
	}

	@Test
	void deleteTodo() {
		int before = this.repository.findAll().size();
		this.repository.deleteById(1001L);
		int after = this.repository.findAll().size();
		assertThat(after).isEqualTo(before - 1);

		assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> {
			this.repository.findById(1001L).get();
		});
	}

}