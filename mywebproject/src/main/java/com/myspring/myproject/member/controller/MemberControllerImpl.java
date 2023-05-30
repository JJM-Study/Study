package com.myspring.myproject.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.myproject.member.service.MemberService;
import com.myspring.myproject.member.vo.MemberVO;


@Controller("memberController")
public class MemberControllerImpl implements MemberController{
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberVO memberVO;
	
	@Override
	@RequestMapping(value="/member/memberList", method = RequestMethod.GET)
	public ModelAndView memberlist(HttpServletRequest request, HttpServletResponse response) {
		String view = (String)request.getAttribute("view");
		List memberList = memberService.MemberList();
		ModelAndView mav = new ModelAndView(view);
		mav.setViewName(view);
		mav.addObject("memberList", memberList);
		
		return mav;
	}
	
	@Override // 2023/05/11
	@RequestMapping(value="/login")	// 1149p
	public ModelAndView login(@RequestParam Map<String, String> loginMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberVO = memberService.login(loginMap);
		if (memberVO!= null && memberVO.getId()!=null) {
			HttpSession session=request.getSession();
			session=request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberInfo", memberVO);
			mav.setViewName("/home");
		}	
		else {
			String message = "아이디나 비밀번호가 틀립니다.";
			// https://jhlblue.tistory.com/5   viwe로 메세지 보내기
			mav.addObject("message", message);
			mav.setViewName("/member/loginForm");
		}
		
		return mav;
		
	}
	
	@Override // 로그아웃 구현 중 ...
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		System.out.println("로그아웃 성공");
		mav.setViewName("redirect:/");
		return mav;
	}
}
