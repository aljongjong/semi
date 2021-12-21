package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.MemberVo;

@WebServlet("/join")
public class JoinController extends HttpServlet {

	// 회원가입 화면 보여줌
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/views/member/joinForm.jsp").forward(req, resp);
	}
	
	// 회원가입 진행
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		String name = req.getParameter("name");
		
		MemberVo m = new MemberVo();
		m.setId(id);
		m.setPwd(pwd);
		m.setName(name);
		
		int result = new MemberService().join(m);
		
		if(result > 0) {
			// success
			req.setAttribute("msg", "회원가입 성공");
			req.getRequestDispatcher("/WEB-INF/views/common/successPage.jsp").forward(req, resp);
		} else {
			// error
			req.setAttribute("msg", "회원가입 실패");
			req.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(req, resp);
		}
	}
}
