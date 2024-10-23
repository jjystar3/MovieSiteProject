package com.example.demo.member.controller;

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

@Controller
//@RequestMapping("/member") //중간경로 제거
public class MemberController {
	
	@Autowired
	MemberService service;

	// 목록 화면을 반환하는 메소드
	// /member/list?page=1
//	@GetMapping("/list")
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
			return "redirect:/login";
		}else {
			redirectAttributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패하였습니다");
			return "redirect:/register";
		}

	}

	// 상세화면을 반환하는 메소드
	//	@GetMapping("/read")
	@GetMapping("/member/read") // 주소수정
	// /member/read?id=user1&page=1
	// /member/read?id=user1
	public void read(@RequestParam(name = "id") String id, @RequestParam(name = "page", defaultValue = "0") int page, Model model) { //파라미터 추가
		// 전달받은 파라미터로 회원 조회
		MemberDTO dto = service.read(id);
		// 조회한 회원정보를 화면에 전달
		model.addAttribute("dto", dto); //사용자 정보
		// 페이지번호를 화면에 전달
		model.addAttribute("page", page); //페이지번호 (화면을 이동해도 페이지번호를 유지하기 위해서)
	}
}
