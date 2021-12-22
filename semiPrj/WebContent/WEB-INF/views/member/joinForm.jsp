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
    }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp"%>
    
    <div id="div-main">
        <form action="join" method="post" onsubmit=check()>
        	<input type="hidden" value="false"> <!-- 아이디가 중복인 상태로 회원가입을 눌렀을때 check 펑션의 기준이 되게 하는 것, value를 기준으로 -->
            <label>아이디   : </label> <input type="text" name="id" id="idBox"><button type="button" id="dupCheck">중복확인</button>
            <br>
            <label>비밀번호 : </label> <input type="text" name="pwd">
            <br>
            <label>이름 : </label> <input type="text" name="name">
            <br>
            <input type="submit" value="가입하기">
        </form>
    </div>
    
    <script>
    	$("#dupCheck").on('click', function() {
    		$.ajax({
    			url : '/semi/memberDupCheck',
    			type : 'GET',
    			data : {
    				key1 : "value1",
    				key2 : "value2",
    				id : $("#idBox").val()
    			},
    			success : function(data) {
    				alert(data);
    				$("#checkResultBox").text(data);
    				
    			},
    			error : function(err) {
    				alert('error');
    			}
    		})
    	})
    
    
    </script>

</body>
</html>