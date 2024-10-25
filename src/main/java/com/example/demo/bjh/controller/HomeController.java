package com.example.demo.bjh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "/home/main";
	}

	@GetMapping("/customlogin")
	public String login() {

		return "/home/login";
	}

}