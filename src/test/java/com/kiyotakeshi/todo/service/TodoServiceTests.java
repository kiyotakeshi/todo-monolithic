package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Category;
import com.kiyotakeshi.todo.entity.Progress;
import com.kiyotakeshi.todo.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ComponentScan(basePackageClasses = TodoService.class)
// @ActiveProfiles("test") // if you set application-test.yml
class TodoServiceTests {

	@Autowired
	TodoService service;

	@Test
	void findAll() {
		List<Todo> todoList = this.service.findAll();
		System.out.println("Todo List");
		todoList.stream().forEach(System.out::println);
		assertThat(todoList).isNotEmpty();
	}

	@Test
	void findById() {
		var todo = this.service.findById(1000L);
		assertThat(todo.getActivityName()).isEqualTo("go to supermarket");
	}

	@Test
	void findByIdExpectedException() {
		assertThrows(ResponseStatusException.class, () -> this.service.findById(10000L));
	}

	@Test
	void save() {
		int before = this.service.findAll().size();
		var todo = new Todo("sleep", Progress.Open, Category.None, "test");
		this.service.save(todo);
		int after = this.service.findAll().size();
		assertThat(before + 1).isEqualTo(after);
	}

	@Test
	void findByCategory() {
		List<Todo> todoByCategory = this.service.findByCategory(Category.Hobby);
		assertThat(todoByCategory).hasSize(1);
		assertThat(todoByCategory.get(0).getCategory()).isEqualTo(Category.Hobby);
	}

	@Test
	@DirtiesContext
	void updateTodo() {
		var todo = this.service.findById(1001L);
		todo.setActivityName("update");
		todo.setProgress(Progress.Doing);
		todo.setCategory(Category.Other);

		this.service.updateTodo(1001L, todo);
		var updatedTodo = this.service.findById(1001L);
		assertThat(updatedTodo.getActivityName()).isEqualTo("update");
		assertThat(updatedTodo.getProgress()).isEqualTo(Progress.Doing);
		assertThat(updatedTodo.getCategory()).isEqualTo(Category.Other);
	}

	@Test
	@DirtiesContext
	void deleteTodo() {
		int before = this.service.findAll().size();
		this.service.deleteTodo(1001L);
		int after = this.service.findAll().size();
		assertThat(after).isEqualTo(before - 1);

		// 削除したため取得できないこと
		assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() -> {
			this.service.findById(1001L).getId();
		});
	}

}