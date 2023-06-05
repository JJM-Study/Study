package com.myspring.myproject.board.service;

import java.util.List;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardService {
	public List listPost() throws Exception;

	public BoardVO postview(int postNO) throws Exception;

	public int updatePost(BoardVO boardVO) throws Exception;
}
