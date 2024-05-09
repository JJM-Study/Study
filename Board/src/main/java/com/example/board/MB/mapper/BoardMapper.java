//package com.example.board.MB.mapper;
//
//import java.util.List;
//
//import org.apache.ibatis.annotations.Mapper;
//
//import com.example.board.MB.MB_dto.MB_BoardDto;
//
//@Mapper
//public interface BoardMapper {
//
//	List<MB_BoardDto> selectBoardList() throws Exception;
//	void insertBoard(MB_BoardDto board) throws Exception;
//	void updateHitCount(int board_id) throws Exception;
//	MB_BoardDto selectBoardDetail(int board_id) throws Exception;
//	void updateBoard(MB_BoardDto board) throws Exception;
//	void deleteBoard(int board_id) throws Exception;
//	
//}