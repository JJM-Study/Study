package com.example.board.service;

import java.util.List;

import com.example.board.dto.BoardDto;

public interface BoardService {

	List<BoardDto> selectBoardList() throws Exception;
	void insertBoard(BoardDto board) throws Exception;
	BoardDto selectBoardDetail(int board_id) throws Exception;
	void updateBoard(BoardDto board) throws Exception;
	void deleteBoard(int board_id) throws Exception;
	
}