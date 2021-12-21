package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.MemberVo;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	// 로그인 화면 보여줌
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/member/loginForm.jsp").forward(req, resp);
	}
	
	// 로그인 진행
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		
		MemberVo m = new MemberVo();
		m.setId(id);
		m.setPwd(pwd);
		
		MemberVo loginUser = new MemberService().login(m);
		
		if(loginUser != null) {
			// success
//			req.setAttribute("msg", "로그인 성공");
//			req.getRequestDispatcher("/WEB-INF/views/common/successPage.jsp").forward(req, resp);
			req.getSession().setAttribute("loginUser", loginUser);
			req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
			
			
		} else {
			// error
			req.setAttribute("msg", "로그인 실패");
			req.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(req, resp);
		}
	}
}
