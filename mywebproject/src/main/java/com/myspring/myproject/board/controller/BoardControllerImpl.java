package com.myspring.myproject.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.mapper.Mapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.myspring.myproject.board.service.BoardService;
import com.myspring.myproject.board.vo.BoardVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardVO boardVO;
	
//	@Autowired 
//	private SqlSessionTemplate sqlSessiontemplate;
	
	// 웹서버의 처리 순서 . ... 객체 생성 ->init() -> httpservlet... https://sallykim5087.tistory.com/122
//	@Override
//	//@RequestMapping(value="/board/boardForm", method = {RequestMethod.GET, RequestMethod.POST})
//	@RequestMapping(value="/Listboard")
//	public ModelAndView boardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//String view = (String)request.getAttribute("view");
//		List ListPost = boardService.listPost();
//		//ModelAndView mav = new ModelAndView(view);
//		ModelAndView mav = new ModelAndView();
//		//mav.setViewName(view);
//		mav.setViewName("/board/boardForm");
//		mav.addObject("listPost", ListPost);
//		
//		return mav;
//	}
	

	
	//https://blog.naver.com/PostView.nhn?blogId=jhj9512z&logNo=222244283964&parentCategoryNo=&categoryNo=57&viewDate=&isShowPopularPosts=true&from=search
	//https://stove99.tistory.com/78 참고
	//https://chocotaste.tistory.com/116
	//https://stylishc.tistory.com/103
	@RequestMapping(value="/Listboard")
	public ModelAndView pro_selectAllPostList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		ModelAndView mav = new ModelAndView();
		param.put("c_page", 1); // 현재 페이지
		param.put("row_count", 100); // 한 페이지 당 게시물 수
		System.out.println("param" + param);
		List result = boardService.BoardList(param);

		
		mav.setViewName("/board/boardForm");
		mav.addObject("listPost", result);
		
		return mav;
	}
	
	// https://doublesprogramming.tistory.com/95 게시판 만들기 참고.
	// 게시판 보기(수정)
	@Override
	@RequestMapping(value="/board/viewPost")
	public ModelAndView viewPost(@RequestParam("postNO") int postNO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		boardVO=boardService.postview(postNO);
		mav.setViewName("/board/viewPost");
		mav.addObject("postView", boardVO); //
		return mav;
		
	}
	
	//게시판 보기(쓰기)
	@Override
	@RequestMapping(value="/board/posting")
	public String posting() throws Exception {
		return "/board/viewPost";
	}
	
//	//게시판 쓰기 https://kimvampa.tistory.com/164 참고함.
//	@Override
//	@RequestMapping(value="/board/insertPost")
//	public String insertPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		boardService.insertPost(boardVO);
//		//String view = "/board/boardForm";
//		String view = "/board/boardForm";
//		return view;
//	}
	
	//게시판 쓰기 https://kimvampa.tistory.com/164 참고함.
	@Override
	@RequestMapping(value="/board/insertPost")
	public ModelAndView insertPost(@ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		boardService.insertPost(boardVO);
		String cPath = request.getContextPath(); 
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(cPath + "/Listboard");
		redirectView.setExposeModelAttributes(false);
		mav.setView(redirectView);
		return mav;
	}
	
	// https://dion-ko.tistory.com/69 Map, Hash 관련
	@Override
	@RequestMapping(value="/board/updatePost")
	public String updatePost(@RequestParam("postNO") int postNO, @ModelAttribute("boardVO") BoardVO boardVO, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		boardVO.setPostNO(postNO);
		boardService.updatePost(boardVO);
		String view = "redirect:/board/viewPost?postNO=" + postNO;
		return view;
		
	}
	
}
