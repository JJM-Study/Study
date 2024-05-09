//package com.example.board.MB.MB_service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.board.MB.MB_dto.MB_BoardDto;
//import com.example.board.MB.mapper.BoardMapper;
//
//@Service
//public class MB_BoardServiceImpl implements MB_BoardService{
//	
//	@Autowired
//	BoardMapper boardMapper;
//
//	@Override
//	public List<MB_BoardDto> selectBoardList() throws Exception {
//		
//		return boardMapper.selectBoardList();
//	}
//	
//	@Override
//	public void insertBoard(MB_BoardDto board) throws Exception {
//	  boardMapper.insertBoard(board);
//
//	}
//	
//	@Override
//	public MB_BoardDto selectBoardDetail(int board_id) throws Exception {
//	  boardMapper.updateHitCount(board_id);	// 조회수(hit_cnt)
//	  return boardMapper.selectBoardDetail(board_id);
//	}
//	
//	@Override
//	public void updateBoard(MB_BoardDto board) throws Exception {
//	  boardMapper.updateBoard(board);
//	}
//
//	@Override
//	public void deleteBoard(int board_id) throws Exception {
//	  boardMapper.deleteBoard(board_id);
//	}
//	
//}