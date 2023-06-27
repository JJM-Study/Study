package com.myspring.myproject.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardController {

	public ModelAndView viewPost(int postNO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String updatePost(int postNO, BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String posting() throws Exception;

	public ModelAndView insertPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	//public ModelAndView pro_selectAllPostList(HttpServletRequest request, HttpServletResponse response) throws Exception;

	//public ModelAndView pro_selectAllPostList(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView pro_selectAllPostList(HttpServletRequest request, HttpServletResponse response) throws Exception;

	public String deletePost(@RequestParam("postNO") int postNO, @ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView replyPost(BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
