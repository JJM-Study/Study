package com.myspring.myproject.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			String message = "���̵� ��й�ȣ�� Ʋ���ϴ�.";
			// https://jhlblue.tistory.com/5   viwe�� �޼��� ������
			mav.addObject("message", message);
			mav.setViewName("/member/loginForm");
		}
		
		return mav;
		
	}
	
	@Override // �α׾ƿ� ���� �� ...
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.setAttribute("isLogOn", false);
		session.removeAttribute("memberInfo");
		System.out.println("�α׾ƿ� ����");
		mav.setViewName("redirect:/");
		return mav;
	}
	
	// https://nancording.tistory.com/90 �� ��� ������ ��.
	// Model ������� �ٲ� �� ���. // ���̵� ���� ���� Ȯ��
	@Override
	@RequestMapping(value="/checkId")
	public String checkId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		System.out.println(id);
		memberService.checkId(id);
		System.out.println(response);
		request.setAttribute("checkID", response);
		return "redirect:/signUp";
	}
	
	@Override
	@RequestMapping(value="/singUp", method = RequestMethod.POST) // ȸ������
	public void InsertMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		memberService.MemberInsert(memberVO);
		//String view = "redirect:/loginForm";
		//mav.addObject("message","Welcome!");
		//mav.setViewName(view);
		//return mav;
	}
	
}
