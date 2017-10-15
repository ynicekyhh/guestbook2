<%@page import="com.bigdata2017.guestbook.vo.GuestbookVo"%>
<%@page import="java.util.List"%>
<%@page import="com.bigdata2017.guestbook.dao.GuestbookDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	List<GuestbookVo> list = (List<GuestbookVo>)request.getAttribute("list"); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	<form action="<%=request.getContextPath() %>/gb" method="post">
	<input type="hidden" name="a" value="add"/>
	
	<h4>새 글 남기기...</h4>
	<table border="1" width="500">
		<tr>
			<td>이름</td><td><input type="text" name="name"></td>
			<td>비밀번호</td><td><input type="password" name="pass"></td>
		</tr>
		<tr>
			<td colspan="4"><textarea name="content" cols="60" rows="5"></textarea></td>
		</tr>
		<tr>
			<td colspan="4" align="right"><input type="submit" VALUE=" 확인 "></td>
		</tr>
	</table>
	</form>
	
	<br>
	<%
		for( GuestbookVo vo : list) {
	%>
			<table width="510" border="1">
				<tr>
					<td width="50"><%=vo.getRownum() %></td>	<!-- 일단은 no로 출력하는데, 나중에 삭제하면 안나오니, 나오는 순서로 출력할 것(order쓰고 sequence사용?) -->
					<td width="150"><%=vo.getName() %></td>
					<td width="250"><%=vo.getDate() %></td>
					<td><a href="<%=request.getContextPath() %>/gb?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>	<!-- 비밀번호를 받아야 하니까 deleteform으로  -->
				</tr>
				<tr>
					<td colspan="4"><%=vo.getContent() %></td>
				</tr>
			</table>
			<br>
	<%
		}
	%>
</body>
</html>