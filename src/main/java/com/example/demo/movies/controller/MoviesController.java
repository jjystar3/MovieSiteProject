package com.example.demo.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.movies.dto.MoviesDTO;
import com.example.demo.movies.service.MoviesService;

@Controller
@RequestMapping("/movies")
public class MoviesController {

	@Autowired
	MoviesService moviesService;
	
	@GetMapping("/list")
	public void list(Model model) {

		List<MoviesDTO> list = moviesService.getList();
		
		model.addAttribute("list", list);
	}

	// 상세화면을 반환하는 메소드
	@GetMapping("/read") // /board/read?no=1
	public void read(@RequestParam(name = "movieId") Long movieId, Model model) {
		
		// 게시물 번호를 파라미터로 전달받아 게시물 정보 조회
		MoviesDTO dto = moviesService.read(movieId);
		
		// 조회한 데이터를 화면에 전달
		model.addAttribute("dto", dto);
	}
}
