package com.kiyotakeshi.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "", "/index", "/index.html" })
public class IndexController {

	@GetMapping
	public String getIndex(Model model) {
		return "index";
	}

}
