package com.kiyotakeshi.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TodoController {

	final TodoService todoService;

	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@GetMapping("/todo")
	public List<Todo> getTodoList() {
		return this.todoService.findAll();
	}

	@GetMapping("/todo/{id}")
	public Todo getTodo(@PathVariable("id") Long id) {
		return this.todoService.findById(id).orElseThrow();
	}
	
	@PostMapping("/todo")
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(Todo todo) {
		return this.todoService.save(todo);
	}

	@PutMapping("/todo/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Todo updateTodo(@PathVariable("id") Long id, Todo todo) {
		return this.todoService.save(todo);
	}

	// TODO: delete

}
