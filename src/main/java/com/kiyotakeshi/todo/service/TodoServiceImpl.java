package com.kiyotakeshi.todo.service;

import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
	public Todo findById(Long id) {
		return this.todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
		var todo = this.findById(id);
		todo.setActivityName(update.getActivityName());
		todo.setColor(update.getColor());
		todo.setProgress(update.getProgress());
		todo.setCategory(update.getCategory());
		return this.todoRepository.save(todo);
	}

	@Override
	public void deleteTodo(Long id) {
		this.todoRepository.deleteById(id);
	}

}
