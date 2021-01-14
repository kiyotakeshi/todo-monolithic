package com.kiyotakeshi.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoTests {

	@Autowired
	TestEntityManager em;

	@Test
	void mapping() {
		var todo1 = this.em.find(Todo.class, 1000L);
		assertThat(todo1.getActivityName()).isEqualTo("go to supermarket");
		assertThat(todo1.getCategory()).isEqualTo("housework");
		assertThat(todo1.getColor()).isEqualTo("white");
	}

}
