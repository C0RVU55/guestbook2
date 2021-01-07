<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.javaex.dao.GuestDao" %>
<%@ page import="com.javaex.vo.GuestVo" %>

<%
	request.setCharacterEncoding("UTF-8");

	GuestDao gDao = new GuestDao();
	List<GuestVo> gList = gDao.getList();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>guest book model 2</title>
</head>
<body>
	<!-- *****입력창***** -->
	<form action="/guestbook2/gbc" method="get"> <!-- form태그로 주소 보낼 때 action 따로 써야 됨. 주의. -->
		<table border="1">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
				<td>비밀번호</td>
				<td><input type="text" name="password"></td>
			</tr>
			<tr>
				<td colspan="4"><textarea name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="4"><button type="submit">확인</button> <!-- 숨은 action값--> <input type="hidden" name="action" value="add"> </td> 
			</tr>
		</table>
		<br><br>
	</form>
	
	<!-- *****출력 화면***** -->
	<%for (int i = 0; i < gList.size(); i++) {  %>
	<table border="1">
		<tr>
			<td><%=gList.get(i).getNo() %></td>
			<td><%=gList.get(i).getName() %></td>
			<td><%=gList.get(i).getRegDate() %></td>
			<td><a href="/guestbook2/gbc?action=dform&no=<%=gList.get(i).getNo()%>">삭제</a></td> 
			<!-- 여기 주소 action=dform?no= 로 해서 계속 아무것도 안 뜸. 키=값끼리는 &로 묶이는 거 잊지 말기.-->
		</tr>
		<tr>
			<td colspan="4"><%=gList.get(i).getContent()%></td>
		</tr>
	</table>
	<br>
	<%} %>

</body>
</html>