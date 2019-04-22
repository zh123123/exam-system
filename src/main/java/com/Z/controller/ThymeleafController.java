package com.Z.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/th")
public class ThymeleafController {
	
	@RequestMapping(value = { "/index", "/" })
	public String index() {

		return "index";
	}

	@RequestMapping("/login")
	public String login() {

		return "index";
	}

	@RequestMapping("/regist")
	public String regist() {

		return "regist";
	}

}
