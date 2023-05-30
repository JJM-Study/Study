package com.myspring.myproject.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.myproject.board.service.BoardService;
import com.myspring.myproject.board.vo.BoardVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 웹서버의 처리 순서 . ... 객체 생성 ->init() -> httpservlet... https://sallykim5087.tistory.com/122
	@Override
	//@RequestMapping(value="/board/boardForm", method = {RequestMethod.GET, RequestMethod.POST})
	@RequestMapping(value="/Listboard")
	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String view = (String)request.getAttribute("view");
		List ListPost = boardService.listPost();
		//ModelAndView mav = new ModelAndView(view);
		ModelAndView mav = new ModelAndView();
		//mav.setViewName(view);
		mav.setViewName("/board/boardForm");
		mav.addObject("listPost", ListPost);
		
		return mav;
	}
}
