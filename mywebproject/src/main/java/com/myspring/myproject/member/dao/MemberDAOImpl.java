package com.myspring.myproject.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.myproject.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List SelectmemberList() {
		List<MemberVO> memberList = sqlSession.selectList("mapper.member.selectMemberList");
		return memberList;
	}
	
	@Override
	public MemberVO login(Map loginMap) throws DataAccessException {
		MemberVO member = (MemberVO)sqlSession.selectOne("mapper.member.login",loginMap);
		
		return member;
	}
	
}
