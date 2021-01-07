<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");

	int no = Integer.parseInt(request.getParameter("no"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>guest book model 2</title>
</head>
<body>

	<form action="/guestbook2/gbc" method="get">
	
		비밀번호 <input type="text" name="password"> 
		<button type="submit">확인</button>
		<!--코드 no--> <input type="hidden" name="no" value="<%=no%>">
		<!-- action --> <input type="hidden" name="action" value="delete">

	</form>
	
	<a href="/guestbook2/gbc?action=list">메인으로 돌아가기</a>
	
</body>
</html>