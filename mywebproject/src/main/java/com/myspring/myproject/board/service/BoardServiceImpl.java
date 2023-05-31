package com.myspring.myproject.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.myproject.board.dao.BoardDAO;
import com.myspring.myproject.board.vo.BoardVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO boardDAO;
	
	@Override
	public List listPost() throws Exception {
		List PostList = boardDAO.selectAllPostList();
		return PostList;
	}
	
	@Override
	public BoardVO postview(int postNO) throws Exception {
		BoardVO Postview = boardDAO.postview(postNO);
		return boardDAO.postview(postNO);
	}
	
}
