package com.example.demo.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.service.MoviesService;

@Controller
public class HomeController {

	@Autowired
	MoviesService moviesService;
	
	@GetMapping("/")
	public String home(Model model) {

		List<MoviesDTO> list = moviesService.getList("");
		
		model.addAttribute("list", list);
		
		return "/home/main";
	}
	
	//커스텀 로그인 화면을 반환하는 메소드
	@GetMapping("/customlogin")
	public String customLogin() {
		return "/home/login";
	}

}
