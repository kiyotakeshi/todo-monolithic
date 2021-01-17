package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {

	List<Todo> findAll();

	Optional<Todo> findById(Long id);

	Todo save(Todo todo);

	List<Todo> findByCategory(String hobby);

	Todo updateTodo(Long id, Todo update);

	void deleteTodo(Long id);

}
