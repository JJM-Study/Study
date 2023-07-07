package com.myspring.myproject.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.myproject.member.vo.MemberVO;

public interface MemberController {
	public ModelAndView memberlist(HttpServletRequest request, HttpServletResponse response);

	public ModelAndView login(@RequestParam Map<String, String> loginMap, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public void InsertMember(MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String checkId(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
