package com.myspring.myproject.member.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.myproject.member.vo.MemberVO;

public interface MemberDAO {
	public List SelectmemberList();
	public MemberVO login(Map loginMap) throws DataAccessException;
	public void InsertMember(MemberVO memberVO) throws DataAccessException;
	public String checkId(String id) throws DataAccessException;
}
