package com.myspring.myproject.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardService {
	//public List listPost() throws Exception;

	public BoardVO postview(int postNO) throws Exception;

	public int b_count() throws Exception;
	
	public void updatePost(BoardVO boardVO) throws Exception;

	public void insertPost(BoardVO boardVO) throws Exception;

	public List<Object> BoardList(HashMap<String, Object> param);

	public void deletePost(BoardVO postNO) throws Exception;

	public int replyPost(BoardVO boardVO) throws Exception;
	
}
