package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardMapper boardMapper;

	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception {
	  boardMapper.insertBoard(board);

	}
	
	@Override
	public BoardDto selectBoardDetail(int board_id) throws Exception {
	  boardMapper.updateHitCount(board_id);	// 조회수(hit_cnt)
	  return boardMapper.selectBoardDetail(board_id);
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception {
	  boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int board_id) throws Exception {
	  boardMapper.deleteBoard(board_id);
	}
	
}