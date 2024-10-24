package com.example.demo.member.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.member.dto.MemberDTO;
import com.example.demo.member.service.MemberService;
import com.example.demo.movies.dto.MoviesDTO;

@Controller
public class MemberController {
	
	@Autowired
	MemberService service;

	@GetMapping("/member/list") //주소수정
	public void list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<MemberDTO> list = service.getList(page);
		model.addAttribute("list", list);
	}

	@GetMapping("/register")
	public String register() { // 리턴타입 수정
		return "member/register"; //html 경로 직접 작성
	}

	@PostMapping("/register")
	public String registerPost(MemberDTO dto, RedirectAttributes redirectAttributes) {

		boolean isSuccess = service.register(dto);

		if(isSuccess) {
			return "redirect:/customlogin";
		}else {
			redirectAttributes.addFlashAttribute("msg", "이메일이 중복되어 등록에 실패하였습니다");
			return "redirect:/register";
		}

	}

	@GetMapping("/member/read")
	public void read(@RequestParam(name = "id") Long id, @RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		
		MemberDTO dto = service.findById(id);
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		
	}

	@GetMapping("/member/profile")
	public void profile(Principal principal, Model model) {
		
		Long id = Long.parseLong(principal.getName());
		
		MemberDTO dto = service.findById(id);
		
		model.addAttribute("dto", dto);
		
	}

	@GetMapping("/member/modify")
	public void modify(Principal principal, Model model) {

		Long id = Long.parseLong(principal.getName());
		
		MemberDTO dto = service.findById(id);
		
		model.addAttribute("dto", dto);		
	}
	
    @PostMapping("/member/modify")
    public String modifyPost(MemberDTO dto, RedirectAttributes redirectAttributes) {
        
    	service.modify(dto);
        
        redirectAttributes.addAttribute("id", dto.getId());

        return "redirect:/member/profile";
    }
    
}
