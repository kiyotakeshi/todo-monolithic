package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Category;
import com.kiyotakeshi.todo.entity.Todo;

import java.util.List;

public interface TodoService {

	List<Todo> findAll();

	Todo findById(Long id);

	Todo save(Todo todo);

	Todo updateTodo(Long id, Todo update);

	void deleteTodo(Long id);

	List<Todo> findByCategory(Category category);
}
