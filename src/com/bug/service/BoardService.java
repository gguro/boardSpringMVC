package com.bug.service;

import org.springframework.stereotype.Service;

import com.bug.dto.BoardVO;

/*
 * 1. @Service 선언
 * 2. BoardController 에 @Autowired 선언
 * 두가지 어노테이션 선언을 해주면 컨트롤러(BoardController)에서 Service에 있는 메소드를 호출하는 방식이 간단해진다
 * 사용은 BoardController클래스를 참고하도록 한다
 */
@Service
public class BoardService {

	// 비밀번호 일치 여부
	public boolean isCheckPass(BoardVO bVo, String pass) {
		if (bVo.getPass().equals(pass)) {
			return true; // 일치
		} else {
			return false; // 불일치
		}
	}
}
