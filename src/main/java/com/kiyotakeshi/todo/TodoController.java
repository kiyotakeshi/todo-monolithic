package com.kiyotakeshi.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping
	public List<Todo> getTodoList() {
		return this.todoService.findAll();
	}

	@GetMapping(value = "/{id}")
	public Todo getTodo(@PathVariable("id") Long id) {
		return this.todoService.findById(id).orElseThrow();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(Todo todo) {
		return this.todoService.save(todo);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Todo updateTodo(@PathVariable("id") Long id, Todo update) {
		logger.info("update valed -> {}", update);
		return this.todoService.updateTodo(id, update);
	}

	// TODO: delete

}
