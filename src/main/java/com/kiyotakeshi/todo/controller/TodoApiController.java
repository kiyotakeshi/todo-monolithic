package com.kiyotakeshi.todo.controller;

import com.kiyotakeshi.todo.entity.Progress;
import com.kiyotakeshi.todo.entity.Todo;
import com.kiyotakeshi.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/todo")
@CrossOrigin(origins = {"http://localhost:8081"})
public class TodoApiController {

	private static final String BASE_PATH = "/api/todo/";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	final TodoService todoService;

	public TodoApiController(TodoService todoService) {
		this.todoService = todoService;
	}

	private String getErrors(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
	}

	@GetMapping
	public List<Todo> getTodoList() {
		return this.todoService.findAll();
	}

	@GetMapping(value = "/{id}")
	public Todo getTodo(@PathVariable("id") Long id) {
		return this.todoService.findById(id);
	}

	@PostMapping
	// ref https://developer.mozilla.org/ja/docs/Web/HTTP/Status/201
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Todo> createTodo(@RequestBody @Valid Todo todo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Request contains incorrect data = [%s]", getErrors(bindingResult)));
		}
		// first created todo status is always Open
		todo.setProgress(Progress.Open);
		var savedTodo = this.todoService.save(todo);
		return ResponseEntity.created(URI.create(BASE_PATH + savedTodo.getId())).body(savedTodo);
	}

	@PutMapping(value = "/{id}")
	// ref https://developer.mozilla.org/ja/docs/Web/HTTP/Methods/PUT
	@ResponseStatus(HttpStatus.OK)
	public Todo updateTodo(@PathVariable("id") Long id, @RequestBody @Valid Todo update, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Request contains incorrect data = [%s]", bindingResult.getAllErrors()));
		}
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
