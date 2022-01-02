<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#div-wrap
	{
		background : #000;
		width: 70vw;
		margin: auto;
		color: #fff
	}
	table
	{
		width: 100%;
	}

</style>
</head>
<body>
	<%@ include file="../common/header.jsp" %>
	<h1>탐색</h1>
	
	
		<h2>${m.name}</h2>
	
	<div id="div-wrap">
		<form action="search" method="GET">
			<select name="searchType">
				<option value="MEMBER_NO">회원번호</option>
				<option value="ID">아이디</option>
				<option value="NAME">이름</option>
			</select>
			<input type="text" name="searchValue">
			<input type="submit" value="검색">
		</form>
	
		<table border=1>
			<tr>
				<td>회원번호</td>
				<td>아이디</td>
				<td>이름</td>
			</tr>
			<c:forEach items="${memberList}" var="m">
			<tr>
				<td>${m.memberNo}</td>
				<td>${m.id}</td>
				<td>${m.name}</td>
			</tr>
			</c:forEach>
		</table>
		
		<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
			<div class="page-area">
				<c:if test="${i le maxPage}">
					<a href="search?currentPage=${i}">${i}</a>
				</c:if>
			</div>
		</c:forEach>
	</div>
</body>
</html>