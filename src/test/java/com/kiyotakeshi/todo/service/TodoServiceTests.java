package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
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
		var todo = new Todo("sleep", "rainbow", "free");
		this.service.save(todo);
		int after = this.service.findAll().size();
		assertThat(before + 1).isEqualTo(after);
	}

	@Test
	void findByCategory() {
		List<Todo> todoByCategory = this.service.findByCategory("hobby");
		assertThat(todoByCategory).hasSize(1);
		assertThat(todoByCategory.get(0).getCategory()).isEqualTo("hobby");
	}

	@Test
	void updateTodo() {
		var update = new Todo("update", "red", "free");
		this.service.updateTodo(1001L, update);
		var updatedTodo = this.service.findById(1001L);
		assertThat(updatedTodo.getActivityName()).isEqualTo("update");
		assertThat(updatedTodo.getColor()).isEqualTo("red");
		assertThat(updatedTodo.getCategory()).isEqualTo("free");
	}

	@Test
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