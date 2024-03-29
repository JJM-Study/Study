package com.myspring.myproject.board.dao;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
//	@Override
//	public List selectAllPostList() throws DataAccessException {
//		List<BoardVO> PostList = sqlSession.selectList("mapper.board.selectAllPostList");
//		return PostList;
//	}
	
	@Override
	public BoardVO postview(int postNO) throws Exception { 
		return sqlSession.selectOne("mapper.board.selectPost", postNO);
	}
	
	@Override
	public int b_count() throws Exception {
		return sqlSession.selectOne("mapper.board.b_totalPage");
	}
	
	@Override
	public void postDelete(BoardVO boardVO) throws Exception {
		sqlSession.update("mapper.board.deletePost", boardVO);
	}
	
	// https://wbluke.tistory.com/15 DataAcessException 등 참고
	// https://yulfsong.tistory.com/44 update 반환 타입 참고
	@Override
	public void postUpdate(BoardVO boardVO) throws Exception {
		sqlSession.update("mapper.board.updatePost", boardVO);
	}
	
	@Override
	public int postInsert(BoardVO boardVO) throws Exception {
		return sqlSession.insert("mapper.board.insertPost", boardVO);
	}
	
	@Override
	public List<Object> BoardList(HashMap<String, Object> map) {
		return sqlSession.selectList("mapper.board.pro_selectAllPostList", map);
	}
	
	@Override
	public int replyPost(BoardVO boardVO) throws Exception {
		return sqlSession.insert("mapper.board.replyPost", boardVO);
	}
	
	// 파라미터를 VO 대신 map 형식으로 가져오기. https://loco-motive.tistory.com/56
	@Override
	public int fileUpload(Map<String, Object> map) throws Exception {
		return sqlSession.insert("mapper.board.upload", map);
	}
	
	@Override
	public List<Map<String, Object>> listFile(int pNO) throws Exception {
		return sqlSession.selectList("mapper.board.fileList", pNO);
	}
	
	@Override
	public List<Map<String, Object>> downloadList(int seq) throws Exception {
		return sqlSession.selectList("mapper.board.downloadList", seq);
	}
}
