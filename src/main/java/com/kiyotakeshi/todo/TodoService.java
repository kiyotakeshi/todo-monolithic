package com.kiyotakeshi.todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

	List<Todo> findAll();

	Optional<Todo> findById(Long id);

	Todo save(Todo todo);

	List<Todo> findByCategory(String hobby);

}
