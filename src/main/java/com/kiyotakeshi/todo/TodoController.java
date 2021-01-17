package com.kiyotakeshi.todo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoController {

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

//	@GetMapping(value = )

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Todo createTodo(Todo todo) {
		return this.todoService.save(todo);
	}

	// TODO: update
//	@PutMapping
//	@ResponseStatus(HttpStatus.OK)
//	public Todo updateTodo(Todo todo) {
//		return this.todoService.save(todo);
//	}

	// TODO: delete

}
