package com.kiyotakeshi.todo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TodoController {

	final TodoRepository todoRepository;

	public TodoController(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@GetMapping("/todo")
	public List<Todo> getTodoList() {
		return this.todoRepository.findAll();
	}

	@GetMapping("/todo/{id}")
	public Todo getTodo(@PathVariable("id") Long id) {
		return this.todoRepository.findById(id).orElseThrow();
	}

	@PostMapping("/todo")
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(Todo todo) {
		return this.todoRepository.save(todo);
	}

	// TODO: update

	// TODO: delete

}
