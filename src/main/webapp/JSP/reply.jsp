<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.yongsae.board.domain.BoardVO" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>답변글 쓰기 화면</title>

</head>

<body>
    <h1>답변글 쓰기 화면</h1>
    <form action="reply.do" method="post">
        <input type="hidden" name="num" value="${retrieve.num}">
        <input type="hidden" name="repRoot" value="${replyui.repRoot}"> 
        <input type="hidden" name="repStep" value="${replyui.repStep}"> 
        <input type="hidden" name="repIndent" value="${replyui.repIndent}">원글번호:${replyui.num}&nbsp;&nbsp;&nbsp;&nbsp; 조횟수:${replyui.readcnt}<br>
        제목
        <input type="text" name="title" value="${replyui.title}"><br>
        작성자<input type="text" name="author"><br>
        내용<textarea name="content" rows="10">${replyui.content}</textarea><br> <input type="submit" value="답변달기">
    </form>
    <a href="list.do">목록 보기</a>
</body>

</html>