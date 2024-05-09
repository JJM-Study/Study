package com.example.board.HN.HN_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.HN.HN_model.HN_BoardEntity;
import com.example.board.HN.HN_service.HN_BoardService;

@Controller
public class HN_BoardController {
	

	@Autowired
	HN_BoardService boardService;

	@GetMapping("/board/boardList")
	public ModelAndView openBoardList() throws Exception {
	  ModelAndView mv = new ModelAndView("/board/boardList");   //view를 설정해준다.
	  List<HN_BoardEntity> list = boardService.getAllBoards();     //service를 이용하여 게시판 목록을 데이터베이스에서 조회한다.
	  mv.addObject("list",list);                                //설정한 뷰로 넘겨줄 데이터를 추가

	  return mv;
	}
	
	@RequestMapping("/board/openBoardWrite")
	  public String openBoardWrite() {
	  return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard")
	  public String insertBoard(HN_BoardEntity board) throws Exception {
	  boardService.Save(board);
	  return "redirect:/board/boardList";

	}
	
	@RequestMapping("/board/openBoardDetail")
	public ModelAndView openBoardDetail(@RequestParam(value="board_id") Long board_id) throws Exception{
	  ModelAndView mv = new ModelAndView("/board/boardDetail");
	  HN_BoardEntity board = boardService.ViewPost(board_id);
	  mv.addObject("board",board);
	  return mv;
	}
	
	@RequestMapping("/board/updateBoard")  // 수정요청
	public String updateBoard(@RequestParam("boardId") Long boardId, @RequestParam("title") String title, @RequestParam("contents") String contents) throws Exception {
	  boardService.Update(boardId, title, contents);         //게시글 수정
	  return "redirect:/board/boardList";  //수정완료 후 게시판 목록으로
	}


	@RequestMapping("/board/deleteBoard")  //삭제요청
	public String deleteBoard(HN_BoardEntity board) throws Exception {
	  boardService.delete(board.getBoardId());      //게시글 삭제
	  return "redirect:/board/boardList";  //삭제완료 후 게시판 목록으로
	}
	
}