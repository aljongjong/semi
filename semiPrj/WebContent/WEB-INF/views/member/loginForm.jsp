<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    #div-main{
        width: 50vw;
        height: 50vh;
        margin: auto;
        background-color: cadetblue;
        text-align: center;
    }
</style>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp"%>
    
    <div id="div-main">
        <form action="login" method="POST">
            <label>아이디   : </label> <input type="text" name="id">
            <br>
            <label>비밀번호 : </label> <input type="text" name="pwd">
            <br>
            <input type="submit" value="로그인">
            <a href="join">회원가입</a>
        </form>
    </div>

</body>
</html>