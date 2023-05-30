package com.myspring.myproject.member.service;

import java.util.List;
import java.util.Map;

import com.myspring.myproject.member.vo.MemberVO;

public interface MemberService {
	public List MemberList();

	public MemberVO login(Map loginMap) throws Exception;
}
