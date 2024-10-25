package com.example.demo.bjh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.bjh.dto.MemberDTO;
import com.example.demo.bjh.service.MemberService;

@Controller
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/register")
	public String register() {
		
		return "/member/register";
	}
	
	@PostMapping("/register")
	public String register1(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
		
		boolean isSuccess = memberService.signUp(memberDTO);
		
		if(isSuccess) {
			return "redirect:/"; 
		}else {
			redirectAttributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패하였습니다");
			return "redirect:/register"; 
		}
	}
}
