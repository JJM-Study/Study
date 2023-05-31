package com.myspring.myproject.board.dao;



import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.myproject.board.vo.BoardVO;
import com.myspring.myproject.member.vo.MemberVO;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List selectAllPostList() throws DataAccessException {
		List<BoardVO> PostList = sqlSession.selectList("mapper.board.selectAllPostList");
		return PostList;
	}
	
	@Override
	public BoardVO postview(int postNO) throws Exception { 
		return sqlSession.selectOne("mapper.board.selectPost", postNO);
	}
}
