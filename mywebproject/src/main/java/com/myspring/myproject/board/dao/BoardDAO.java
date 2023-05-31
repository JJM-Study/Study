package com.myspring.myproject.board.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.myproject.board.vo.BoardVO;

public interface BoardDAO {

	public List selectAllPostList() throws DataAccessException;

	public BoardVO postview(int postNO) throws Exception;
	
}
