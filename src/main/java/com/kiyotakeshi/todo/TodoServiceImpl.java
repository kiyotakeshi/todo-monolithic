package com.kiyotakeshi.todo;

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

}
