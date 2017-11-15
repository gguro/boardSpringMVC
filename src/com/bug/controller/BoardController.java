package com.bug.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bug.dao.BoardDAO;
import com.bug.dto.BoardVO;
import com.bug.service.BoardService;

/**
 * BoardController : 게시판 리스트/등록/수정/삭제, 비밀번호 체크(수정/삭제시 필요)
 * @author 2-18
 * @version 1.0
 * @since 2017-11-09
 */

@Controller
public class BoardController {

	/*
	 * DAO와 Service의 차이
	 * - DAO : 디비 처리에 필요한 쿼리문에 대한 메소드정의
	 * - Service : 디비 처리 외에 필요한 기능에 대한 메소드정의
	 * 
	 * 사용법
	 * - 선언하는 어노테이션이 다를뿐 방식은 동일
	 * - 선언한 멤버변수명.메소드() 호출
	 */
	
	// 쿼리문 처리를 위한 객체
	// 필요한 쿼리에 대한 메소드 호출은 dao.메소드() 형식으로 할 수 있다
	@Resource(name = "boardDAO")
	private BoardDAO dao;

	// 서비스 실습을 위해 넣었다
	// 메소드 호출시 boardService.메소드() 형식으로 할 수 있다
	@Autowired
	private BoardService boardService;
	
	// 게시판 리스트 start
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String list(Model model) {
		String view = "boardList";

		System.out.println("BoardController list() >>>>>>>>>>");
		List<BoardVO> list = dao.selectAllBoards(); // 모든 게시판글(BoardVO)을 리스트로 불러옴
		System.out.println(list);
		System.out.println("<<<<<<<<<< BoardController list()");
		
		model.addAttribute("boardList", list); // model에 뷰에서 보여줄 값 담기
		
		return view;
	}
	// 게시판 리스트 end
	
	// 게시판 등록 start
	@RequestMapping(value = "/boardWriteForm.do", method = RequestMethod.GET)
	public String writeForm() {
		return "boardWrite"; // 게시판 등록 페이지
	}
	
	@RequestMapping(value = "/boardWrite.do", method = RequestMethod.POST)
	public String write(@Valid @ModelAttribute("board") BoardVO bVo, Errors errors, Model model) {
		
		System.out.println("BoardController write() >>>>>>>>>>");
		System.out.println("error : " + errors.getAllErrors()); // 오류 정보 확인(디버그 코드)
		System.out.println(bVo);
		System.out.println("<<<<<<<<<< BoardController write()");
		
		if (errors.hasErrors()) {
			System.out.println("오류발생 boardWrite로 돌아감");
			return "boardWrite";
		}
		
		dao.insertBoard(bVo); // 글등록

		return "redirect:boardList.do"; // 글리스트를 새로 받기위해 boardList.do 명령으로 리다이렉트
	}
	// 게시판 등록 end
	
	// 게시판 상세보기 start
	@RequestMapping(value = "/boardView.do", method = RequestMethod.GET)
	public String view(String num, Model model) {
		String view = "boardView";

		System.out.println("BoardController view() >>>>>>>>>>");
		dao.updateReadCount(num); // 조회수증가
		BoardVO bVo = dao.selectOneBoardByNum(num); // 글번호로 게시글(BoardVO) 불러오기
		System.out.println(bVo);
		System.out.println("<<<<<<<<<< BoardController view()");
		
		model.addAttribute("board", bVo); // model에 뷰에서 보여줄 값 담기
		
		return view;
	}
	// 게시판 상세보기 end
	
	// 비밀번호 체크 start
	@RequestMapping(value = "/boardCheckPassForm.do", method = RequestMethod.GET)
	public String checkPassForm() {
		return "boardCheckPass"; // 비밀번호 확인 팝업 페이지
	}

	@RequestMapping(value = "/boardCheckPass.do", method = RequestMethod.GET)
	public String checkPass(String num, String pass, Model model) {
		String view = "checkSuccess";

		System.out.println("BoardController checkPass() >>>>>>>>>>");
		BoardVO bVo = dao.selectOneBoardByNum(num); // 글 번호로 게시글(BoardVO) 조회
		System.out.println(bVo);
		System.out.println("<<<<<<<<<< BoardController checkPass()");
		
		// 서비스로 분리(서비스를 만들어서 사용해보기위해.. 간단한 코드이지만 분리하였다)
		// 비밀번호 일치 여부
		boolean isPass = boardService.isCheckPass(bVo, pass); // isCheckPass() : 일치하면 true, 불일치하면 false 리턴하는 메소드
		if (!isPass) { // 불일치하면 메세지를 model에 담아 다시 비밀번호 확인 팝업 페이지로 이동
			model.addAttribute("message", "비밀번호가 틀렸습니다.");
			return "boardCheckPass";
		}
		
		return view; // 일치시 성공처리 페이지로 이동
	}
	// 비밀번호 체크 end
	
	// 게시판 수정 start
	@RequestMapping(value = "/boardUpdateForm.do", method = RequestMethod.GET)
	public String updateForm(String num, Model model) {
		String view = "boardUpdate";

		System.out.println("BoardController updateForm() >>>>>>>>>>");
		BoardVO bVo = dao.selectOneBoardByNum(num); // 등록된 게시글(BoardVO)을 불러옴
		System.out.println(bVo);
		System.out.println("<<<<<<<<<< BoardController updateForm()");
		
		model.addAttribute("board", bVo); // model에 뷰에서 보여줄 값 담기
		
		return view;
	}
	
	@RequestMapping(value = "/boardUpdate.do", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("board") BoardVO bVo, Errors errors, Model model) {
		String view = "boardView";

		System.out.println("BoardController update() >>>>>>>>>>");
		System.out.println("error : " + errors.getAllErrors()); // 오류 정보 확인(디버그 코드)
		System.out.println(bVo);
		System.out.println("<<<<<<<<<< BoardController update()");
		
		if (errors.hasErrors()) {
			System.out.println("오류발생 boardUpdate로 돌아감");
			return "boardUpdate";
		}
		
		dao.updateBoard(bVo); // 글수정
		
		return view; // 게시글 상세보기 페이지로 이동
	}
	// 게시판 수정 end
	
	// 게시판 삭제 start
	@RequestMapping(value = "/boardDelete.do", method = RequestMethod.GET)
	public String delete(String num) {
		System.out.println("BoardController delete() >>>>>>>>>>");
		dao.deleteBoard(num); // 글삭제
		System.out.println(num + "번 글 삭제");
		System.out.println("<<<<<<<<<< BoardController delete()");
		
		return "redirect:boardList.do"; // 게시글리스트를 새로 받기위해 boardList.do 명령으로 리다이렉트
	}
	// 게시판 삭제 end
}
