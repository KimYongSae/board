<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.yongsae.board.domain.PageTO" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
PageTO to=( PageTO)request.getAttribute( "page" ); 
int curPage=to.getCurPage(); 
int perPage=to.getPerPage(); 
int totalCount=to.getTotalCount(); 
int totalPage=totalCount / perPage; // 보여줄 페이지 번호개수 
if( totalCount % perPage !=0 ) totalPage++; 
for(int i =1; i<=totalPage; i++){ 
    if(curPage == i ){ 
        out.print( "<font size=10 color='red'>" +i +"</font>" ); 
    }else{
        out.print( "<a href='list.do?curPage="+i+"'>"+i +"</a>&nbsp;" );
    }
}
%>