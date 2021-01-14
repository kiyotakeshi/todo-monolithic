package com.kiyotakeshi.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		return todoRepository.findById(id).orElseThrow();
	}

}
