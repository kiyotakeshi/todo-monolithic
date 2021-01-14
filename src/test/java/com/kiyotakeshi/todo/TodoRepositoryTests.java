package com.kiyotakeshi.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoRepositoryTests {

	@Autowired
	TodoRepository todoRepository;

	@Test
	void name() {
		Todo todo = this.todoRepository.findById(1000L).orElseThrow();
		assertThat(todo.getActivityName()).isEqualTo("go to supermarket");
	}

	@Test
	void findByActivityName() {
		List<Todo> todoByCategory = this.todoRepository.findByCategory("hobby");
		assertThat(todoByCategory).hasSize(1);
		assertThat(todoByCategory.get(0).getActivityName()).isEqualTo("listen to music");
	}

}