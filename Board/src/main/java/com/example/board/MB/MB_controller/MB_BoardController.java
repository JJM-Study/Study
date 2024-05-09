//package com.example.board.MB.MB_controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.example.board.MB.MB_dto.MB_BoardDto;
//import com.example.board.MB.MB_service.MB_BoardService;
//
//
//@Controller
//public class MB_BoardController {
//	
//
//	@Autowired
//	MB_BoardService boardService;
//
//	@GetMapping("/board/boardList")
//	public ModelAndView openBoardList() throws Exception {
//	  ModelAndView mv = new ModelAndView("/board/boardList");   //view를 설정해준다.
//	  List<MB_BoardDto> list = boardService.selectBoardList();     //service를 이용하여 게시판 목록을 데이터베이스에서 조회한다.
//	  mv.addObject("list",list);                                //설정한 뷰로 넘겨줄 데이터를 추가
//
//	  return mv;
//	}
//	
//	@RequestMapping("/board/openBoardWrite")
//	  public String openBoardWrite() {
//	  return "/board/boardWrite";
//	}
//	
//	@RequestMapping("/board/insertBoard")
//	  public String insertBoard(MB_BoardDto board) throws Exception {
//	  boardService.insertBoard(board);
//	  return "redirect:/board/boardList";
//
//	}
//	
//	@RequestMapping("/board/openBoardDetail")
//	public ModelAndView openBoardDetail(@RequestParam(value="board_id") int board_id) throws Exception{
//	  ModelAndView mv = new ModelAndView("/board/boardDetail");
//	  MB_BoardDto board = boardService.selectBoardDetail(board_id);
//	  mv.addObject("board",board);
//	  return mv;
//	}
//	
//	@RequestMapping("/board/updateBoard")  // 수정요청
//	public String updateBoard(MB_BoardDto board) throws Exception {
//	  boardService.updateBoard(board);         //게시글 수정
//	  return "redirect:/board/boardList";  //수정완료 후 게시판 목록으로
//	}
//
//
//	@RequestMapping("/board/deleteBoard")  //삭제요청
//	public String deleteBoard(MB_BoardDto board) throws Exception {
//	  boardService.deleteBoard(board.getBoardId());      //게시글 삭제
//	  return "redirect:/board/boardList";  //삭제완료 후 게시판 목록으로
//	}
//	
//}