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
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/gbc")

	// 오류 : post로 하면 redirect가 안 먹음. 아마 action값이 가려져서 그런 거 같은데 일단 get으로 커밋함.
	// 오류 해결 : doGet에 코드 짜고 doPost에 doGet(request, response); 하면 됨...아예 doPost에 코드 옮겨 봤는데 addList에 접근을 못함.
	
	/*
	 * 기본화면 정하기 
	 * http://localhost:8088/guestbook2/gbc로 접속했을 때 기본화면으로 보여주려고 하거나 action에 다른 값이 들어갔을 때 쓸 수 있는 요령.
	 * 원래 list에 해당되는 코드를 default에 넣음. if문이면 else에 넣으면 됨.
	 * 근데 switch문은 action이 없으면 list에 접근 못함. --> action="" 초기값 정하거나 if(action != null) 안에 스위치문 넣어서 null체크해도 안 됨.
	 * --> 그냥 if else 쓰는 게 나음.
	 */

public class GuestController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// test
		// System.out.println("controller");

		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		switch (action) {

			case "list":
				System.out.println("list");
	
				GuestDao gDao = new GuestDao();
				List<GuestVo> guestList = gDao.getList();
	
				// attribute
				request.setAttribute("gList", guestList);
	
				// forward RequestDispatcher rd = request.getRequestDispatcher("./WEB-INF/addList.jsp");
				// rd.forward(request, response);
				 
				// WebUtil wu = new WebUtil(); 스태틱으로 올려서 매번 new 안 해도 되게 만듦. 아래처럼 쓰고 싶은 거임.
				WebUtil.forward(request, response, "./WEB-INF/addList.jsp"); // 주소가 길면 문자열로 따로 뺄 수도 있을 것
	
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
	
				// redirect : 아래 유틸로 대체
				// response.sendRedirect("/guestbook2/gbc?action=list");
	
				WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");
	
				break;
	
			case "dform":
				// System.out.println("dform");
	
				// 파라미터 받기
				// (addList에서 no 넘기기로 해놓고 여기서 안 받으니까 아무 것도 안 뜸. addList에서 오는 파라미터 받는 거 잊지 말기.)
				int no = Integer.parseInt(request.getParameter("no"));
	
				// forward
				WebUtil.forward(request, response, "./WEB-INF/deleteForm.jsp");
	
				break;
	
			case "delete":
				// System.out.println("delete");
	
				// servlet에서 html 쓸 때 한글 깨짐 방지
				response.setContentType("text/html;charset=utf-8");
	
				// 파라미터 받기
				int no2 = Integer.parseInt(request.getParameter("no"));
				String password2 = request.getParameter("password");
	
				// 조건문 + contentDelete()
				GuestDao gDao3 = new GuestDao();
				int count = gDao3.contentDelete(no2, password2);
	
				if (count == 1) {
					// redirect
					WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");
	
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
		doGet(request, response);

	}


	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get이든 post든 어떤 방식으로 요청으로 들어오든 로직은 여기에 작성.
		// --> 라고 검색해서 찾음. 이걸로 get과 post 다 처리 가능. 오류는 try, catch로 잡음? --> 작동 안 되는데?
	}
	
	
	
	/* *********아래는 테스트 때문에 if문으로 짠 거*********
	
	// System.out.println("controller");

	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");

	if("list".equals(action)) {
		System.out.println("list");

		GuestDao gDao = new GuestDao();
		List<GuestVo> guestList = gDao.getList();

		request.setAttribute("gList", guestList);

		WebUtil.forward(request, response, "./WEB-INF/addList.jsp");
		
	} else if ("add".equals(action)) {
		// System.out.println("add");

		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");

		GuestDao gDao2 = new GuestDao(); 
		GuestVo gVo = new GuestVo(name, password, content);
		gDao2.contentAdd(gVo);

		WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");
		
	} else if("dform".equals(action)) {
		// System.out.println("dform");

		int no = Integer.parseInt(request.getParameter("no"));

		WebUtil.forward(request, response, "./WEB-INF/deleteForm.jsp");
		
	} else if("delete".equals(action)) {
		// System.out.println("delete");

		response.setContentType("text/html;charset=utf-8");

		int no2 = Integer.parseInt(request.getParameter("no"));
		String password2 = request.getParameter("password");

		GuestDao gDao3 = new GuestDao();
		int count = gDao3.contentDelete(no2, password2);

		if (count == 1) {
			// redirect
			WebUtil.redirect(request, response, "/guestbook2/gbc?action=list");

		} else if (count == 0) {
			PrintWriter out = response.getWriter();
			out.println("비밀번호가 바르지 않습니다. <br>");
			out.println("<a href=\"/guestbook2/gbc?action=list\">메인으로 돌아가기</a>");
		}
	}
	*/

}
