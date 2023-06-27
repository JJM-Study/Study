package com.myspring.myproject.board.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardDAO {

	//public List selectAllPostList() throws DataAccessException;

	public BoardVO postview(int postNO) throws Exception;

	public int b_count() throws Exception;
	
	public void postUpdate(BoardVO boardVO) throws Exception;

	public void postInsert(BoardVO boardVO) throws Exception;
	
	public List<Object> BoardList(HashMap<String, Object> map);

	public void postDelete(BoardVO boardVO) throws Exception;

	public int replyPost(BoardVO boardVO) throws Exception;
	
}
