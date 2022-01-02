package com.kh.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.MemberVo;

@WebServlet("/search")
public class MemberController extends HttpServlet {
	
	// 탐색 화면 보여주기
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String type = req.getParameter("searchType");
		String value = req.getParameter("searchValue");
		String currentPage = req.getParameter("currentPage");
		if(currentPage == null) currentPage="1";
		
		System.out.println(type);
		System.out.println(value);
		System.out.println("currentPage : " + currentPage);
//-------------------------------------------------------------------
		int maxPage = 4;
		req.setAttribute("maxPage", maxPage);
		
		int startPage = Integer.parseInt(currentPage) - 2;
		if(startPage < 0); startPage = 1;
		int endPage = startPage + 5;
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		
		
		List<MemberVo> memberList = new MemberService().search(type, value, currentPage);
		
		req.setAttribute("memberList", memberList);
		
		req.getRequestDispatcher("/WEB-INF/views/member/searchAllUser.jsp").forward(req, resp);
		
	}
}
