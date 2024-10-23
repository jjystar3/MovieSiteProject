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

	@GetMapping("/read")
	public void read(@RequestParam(name = "movieId") Long movieId, Model model) {
		
		MoviesDTO dto = moviesService.read(movieId);
		
		model.addAttribute("dto", dto);
	}

	@GetMapping("/modify")
	public void modify(@RequestParam(name = "movieId") Long movieId, Model model) {
		
		MoviesDTO dto = moviesService.read(movieId);
		
		model.addAttribute("dto", dto);		
	}
	
    @PostMapping("/modify")
    public String modifyPost(MoviesDTO dto, RedirectAttributes redirectAttributes) {
        
    	moviesService.modify(dto);
        
        redirectAttributes.addAttribute("movieId", dto.getMovieId());

        return "redirect:/movies/read";
    }
    
	@GetMapping("/video")
	public void video(@RequestParam(name = "movieId") Long movieId, Model model) {
		
		MoviesDTO dto = moviesService.read(movieId);
		
		model.addAttribute("dto", dto);
	}


    @PostMapping("/remove")
    public String removePost(@RequestParam("movieId") Long movieId) {
    	
    	moviesService.remove(movieId);
    	
        return "redirect:/movies/list";
    }
}
