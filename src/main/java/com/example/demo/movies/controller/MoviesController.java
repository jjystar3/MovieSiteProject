package com.example.demo.movies.controller;

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
	
	// 목록 메소드 다시 만들기	
	@GetMapping("/list")
	// 페이지 번호 파라미터 추가 (기본값은 1페이지)
	public void list(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {
		// 게시물 목록 조회
		Page<MoviesDTO> list = moviesService.getList(page); 
		// 화면에 게시물 목록과 페이지정보 전달
		model.addAttribute("list", list);	
		System.out.println("전체 페이지 수: " + list.getTotalPages());
		System.out.println("전체 게시물 수: " + list.getTotalElements());
		System.out.println("현재 페이지 번호: " + (list.getNumber() + 1));
		System.out.println("페이지에 표시할 게시물 수: " + list.getNumberOfElements());
	}

	// 상세화면을 반환하는 메소드
	@GetMapping("/read") // /board/read?no=1
	public void read(@RequestParam(name = "movieId") Long movieId, @RequestParam(defaultValue = "0", name = "page") int page, Model model) {
		
		// 게시물 번호를 파라미터로 전달받아 게시물 정보 조회
		MoviesDTO dto = moviesService.read(movieId);
		
		// 조회한 데이터를 화면에 전달
		model.addAttribute("dto", dto);
		
		// 페이지 번호를 화면에 전달 (가지고 있기)
		model.addAttribute("page", page);
	}
}
