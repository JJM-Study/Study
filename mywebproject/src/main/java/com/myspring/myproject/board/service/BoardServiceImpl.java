package com.myspring.myproject.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.myproject.board.dao.BoardDAO;
import com.myspring.myproject.board.mapper.BoardMapper;
import com.myspring.myproject.board.vo.BoardVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO boardDAO;
	
//	@Override
//	public List listPost() throws Exception {
//		List PostList = boardDAO.selectAllPostList();
//		return PostList;
//	}
	
	
	@Override
	public BoardVO postview(int postNO) throws Exception {
		BoardVO Postview = boardDAO.postview(postNO);
		return boardDAO.postview(postNO);
	}

	@Override
	@Transactional
	public void updatePost(BoardVO boardVO) throws Exception {
		boardDAO.postUpdate(boardVO);
	}
	
	@Override
	@Transactional
	public void insertPost(BoardVO boardVO) throws Exception {
		boardDAO.postInsert(boardVO);
	}
	
	@Override
	public List<Object> BoardList(HashMap<String, Object> map) {
		return boardDAO.BoardList(map);
	}
	
}
