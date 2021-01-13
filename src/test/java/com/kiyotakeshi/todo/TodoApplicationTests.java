package com.kiyotakeshi.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoApplicationTests {

	@Autowired
	TestEntityManager em;

	@Test
	void todo() {
		var todo1 = this.em.find(Todo.class, 1000);
		assertThat(todo1.getActivityName()).isEqualTo("go to supermarket");
	}
}
