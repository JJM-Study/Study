package com.myspring.myproject.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.myproject.member.dao.MemberDAO;
import com.myspring.myproject.member.vo.MemberVO;

// https://jayviii.tistory.com/21 참고.

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)  // 트랜잭션은 서비스에서 처리. https://devlog-wjdrbs96.tistory.com/424 트랜잭션 참고
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO; 
	
	@Override
	public MemberVO login(Map loginMap) throws Exception {
		return memberDAO.login(loginMap);
	}
	
	@Override
	public List MemberList()
	{
		List memberList = memberDAO.SelectmemberList();
		return memberList;
	}
	
	@Override
	public void MemberInsert(MemberVO memberVO)
	{
		memberDAO.InsertMember(memberVO);
	}
	
	@Override
	public String checkId(String id)
	{
		return memberDAO.checkId(id);
	}
	
}
