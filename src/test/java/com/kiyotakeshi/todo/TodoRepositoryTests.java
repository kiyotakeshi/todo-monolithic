package com.kiyotakeshi.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoRepositoryTests {

	@Autowired
	TodoRepository repository;

	@Test
	void findById() {
		Todo todo = this.repository.findById(1000L).orElseThrow();
		assertThat(todo.getActivityName()).isEqualTo("go to supermarket");
	}

	@Test
	void findByCategory() {
		List<Todo> todoByCategory = this.repository.findByCategory("hobby");
		assertThat(todoByCategory).hasSize(1);
		assertThat(todoByCategory.get(0).getActivityName()).isEqualTo("listen to music");
	}

}