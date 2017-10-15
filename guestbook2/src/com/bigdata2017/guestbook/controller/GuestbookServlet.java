package com.bigdata2017.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bigdata2017.guestbook.dao.GuestbookDao;
import com.bigdata2017.guestbook.vo.GuestbookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		
		GuestbookDao dao = new GuestbookDao();
		GuestbookVo vo = new GuestbookVo();
		RequestDispatcher rd = null;
		
		if( "add".equals( actionName ) ) {
			request.setCharacterEncoding("UTF-8");
			
			vo.setName( request.getParameter("name") );
			vo.setPassword( request.getParameter("pass") );
			vo.setContent( request.getParameter("content") );
			
			dao.insert(vo);
			
			//redirection!!
			response.sendRedirect(request.getContextPath() + "/gb");
		} else if( "deleteform".equals( actionName ) ) {
			rd = request.getRequestDispatcher( "/WEB-INF/views/deleteform.jsp" );
			rd.forward(request, response);
		} else if( "delete".equals( actionName ) ) {
			/* 삭제 후 redirection */
			dao.delete(Long.parseLong(request.getParameter("no")), request.getParameter("password"));
			
			response.sendRedirect(request.getContextPath() + "/gb");
		} else {
			/* list action 처리 */
			List<GuestbookVo> list = dao.getList();
			request.setAttribute("list", list);
			
			rd = request.getRequestDispatcher( "/WEB-INF/views/index.jsp" );
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
