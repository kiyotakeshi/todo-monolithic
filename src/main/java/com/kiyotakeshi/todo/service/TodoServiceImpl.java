package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

	final TodoRepository todoRepository;

	public TodoServiceImpl(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@Override
	public List<Todo> findAll() {
		return this.todoRepository.findAll();
	}

	@Override
	public Optional<Todo> findById(Long id) {
		return this.todoRepository.findById(id);
	}

	@Override
	public Todo save(Todo todo) {
		return this.todoRepository.save(todo);
	}

	@Override
	public List<Todo> findByCategory(String hobby) {
		return this.todoRepository.findByCategory(hobby);
	}

	@Override
	public Todo updateTodo(Long id, Todo update) {
		var todo = this.findById(id).orElseThrow();
		todo.setActivityName(update.getActivityName());
		todo.setColor(update.getColor());
		todo.setCategory(update.getCategory());
		return this.todoRepository.save(todo);
	}

	@Override
	public void deleteTodo(Long id) {
		this.todoRepository.deleteById(id);
	}

}
