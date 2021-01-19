package com.kiyotakeshi.todo.controller;

import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

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
		return this.todoService.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND)
		);
	}

	@PostMapping
	// ref https://developer.mozilla.org/ja/docs/Web/HTTP/Status/201
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(Todo todo) {
		return this.todoService.save(todo);
	}

	@PutMapping(value = "/{id}")
	// ref https://developer.mozilla.org/ja/docs/Web/HTTP/Methods/PUT
	@ResponseStatus(HttpStatus.OK)
	public Todo updateTodo(@PathVariable("id") Long id, Todo update) {
		logger.info("update valed -> {}", update);
		return this.todoService.updateTodo(id, update);
	}

	@DeleteMapping(value = "{id}")
	// ref https://developer.mozilla.org/ja/docs/Web/HTTP/Methods/DELETE
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTodo(@PathVariable("id") Long id) {
		this.todoService.deleteTodo(id);
	}

}
