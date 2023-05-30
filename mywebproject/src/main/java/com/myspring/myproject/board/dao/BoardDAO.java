package com.myspring.myproject.board.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BoardDAO {

	public List selectAllPostList() throws DataAccessException;
	
}
