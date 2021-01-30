package com.kiyotakeshi.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiReferenceController {

	/**
	 * return API reference
	 * @return index.html
	 */
	@RequestMapping("/api")
	public String index() {
		return "api-reference";
	}

}
