package com.myspring.myproject.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.myproject.board.dao.BoardDAO;
//import com.myspring.myproject.board.mapper.BoardMapper;
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
	public int b_count() throws Exception {
		return boardDAO.b_count();
	}
	
	@Transactional
	@Override
	public void deletePost(BoardVO boardVO) throws Exception {
		boardDAO.postDelete(boardVO);
	}
	
	@Override
	@Transactional
	public void updatePost(BoardVO boardVO) throws Exception {
		boardDAO.postUpdate(boardVO);
	}
	
	@Override
	@Transactional
	public int insertPost(BoardVO boardVO) throws Exception {
		return boardDAO.postInsert(boardVO);
	}
	
	@Override
	public List<Object> BoardList(HashMap<String, Object> map) {
		return boardDAO.BoardList(map);
	}
	
	@Override
	@Transactional
	public int replyPost(BoardVO boardVO) throws Exception {
		return boardDAO.replyPost(boardVO);
	}
	
	@Override
	@Transactional
	public int fileUpload(Map<String, Object> map) throws Exception {
		return boardDAO.fileUpload(map);
	}
	
	@Override
	public List<Map<String, Object>> listFile(int pNO) throws Exception {
		return boardDAO.listFile(pNO);
	}
	
	@Override
	public List<Map<String, Object>> downloadList(int seq) throws Exception {
		return boardDAO.downloadList(seq);
	}
}
