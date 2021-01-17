package com.kiyotakeshi.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * return API reference
	 * @return index.html
	 */
	@RequestMapping({ "", "/", "index", "index.html" })
	public String index() {
		return "index";
	}

}
