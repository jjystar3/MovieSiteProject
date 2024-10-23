package com.example.demo.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	// 수정화면을 반환하는 메소드
	@GetMapping("/modify") // /board/modify?no=1
	public void modify(@RequestParam(name = "movieId") Long movieId, Model model) {
		
		// 전달받은 게시물 번호로 게시물 정보 조회
		MoviesDTO dto = moviesService.read(movieId);
		
		// 조회한 데이터를 화면에 전달
		model.addAttribute("dto", dto);		
	}
	
	// 수정 처리 메소드
    @PostMapping("/modify")
    public String modifyPost(MoviesDTO dto, RedirectAttributes redirectAttributes) {
        
    	// 전달받은 폼데이터로 기존 게시물 수정
    	moviesService.modify(dto);
        
        // 상세화면으로 이동할때 파라미터 전달
        redirectAttributes.addAttribute("movieId", dto.getMovieId());

        // 상세화면으로 이동
        return "redirect:/movies/read";
    }

    // 삭제 처리 메소드
    @PostMapping("/remove")
    // 폼 데이터 중 단일 항목을 처리할 때는 자동으로 매핑이 안됨
    // @RequestParam을 사용하여 처리
    public String removePost(@RequestParam("movieId") Long movieId) {
    	
    	// 전달받은 파라미터로 게시물 삭제
    	moviesService.remove(movieId);
    	
    	// 삭제 후 목록화면으로 이동
        return "redirect:/movies/list";
    }
}
