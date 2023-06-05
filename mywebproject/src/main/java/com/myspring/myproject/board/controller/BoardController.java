package com.myspring.myproject.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardController {
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView viewPost(int postNO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String updatePost(int postNO, HttpServletRequest request, HttpServletResponse response) throws Exception;


}
