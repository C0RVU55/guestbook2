package com.javaex.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")

public class GuestController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 오류 : post로 하면 redirect가 안 먹음. 아마 action값이 가려져서 그런 거 같은데 일단 get으로 커밋함.
		
		// test
		// System.out.println("controller");
		
		String action = request.getParameter("action");

		switch (action) {

			case "list":
				GuestDao gDao = new GuestDao();
				List<GuestVo> guestList = gDao.getList();
	
				// attribute
				request.setAttribute("gList", guestList);
	
				// forward
				RequestDispatcher rd = request.getRequestDispatcher("./WEB-INF/addList.jsp");
				rd.forward(request, response);
	
				// System.out.println("list");
				break;
	
			case "add":
				// System.out.println("add");
	
				// 파라미터 받기
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String content = request.getParameter("content");
	
				// 리스트에 등록
				GuestDao gDao2 = new GuestDao(); // switch문 쓰니까 각 case별로 변수 중복 불가 --> gDao, gDao2... / rd, rd2...
				GuestVo gVo = new GuestVo(name, password, content);
				gDao2.contentAdd(gVo);
	
				// redirect
				response.sendRedirect("/guestbook2/gbc?action=list");
	
				break;
	
			case "dform":
				// System.out.println("dform");
	
				// 파라미터 받기 (addList에서 no 넘기기로 해놓고 여기서 안 받으니까 아무 것도 안 뜸. addList에서 오는 파라미터 받는 거 잊지 말기.)
				int no = Integer.parseInt(request.getParameter("no"));
				//request.setAttribute("no", no);
				
				// forward
				RequestDispatcher rd2 = request.getRequestDispatcher("./WEB-INF/deleteForm.jsp");
				rd2.forward(request, response);
	
				break;
	
			case "delete":
				// System.out.println("delete");
				
				// 한글 깨짐 방지
				response.setContentType("text/html;charset=utf-8");
	
				// 파라미터 받기
				int no2 = Integer.parseInt(request.getParameter("no"));
				String password2 = request.getParameter("password");
	
				// 조건문 + contentDelete()
				GuestDao gDao3 = new GuestDao();
				int count = gDao3.contentDelete(no2, password2);
	
				if (count == 1) {
					// redirect
					response.sendRedirect("/guestbook2/gbc?action=list");
					
				} else if (count == 0) {
					PrintWriter out = response.getWriter();
					out.println("비밀번호가 바르지 않습니다. <br>");
					out.println("<a href=\"/guestbook2/gbc?action=list\">메인으로 돌아가기</a>");
				}
	
				break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);

	}

}
