package org.yongsae.board.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.Service.BoardDeleteService;
import org.yongsae.board.Service.BoardListService;
import org.yongsae.board.Service.BoardPageService;
import org.yongsae.board.Service.BoardReplyService;
import org.yongsae.board.Service.BoardReplyUIService;
import org.yongsae.board.Service.BoardRetrieveService;
import org.yongsae.board.Service.BoardSearchService;
import org.yongsae.board.Service.BoardUpdateService;
import org.yongsae.board.Service.BoardWriteService;
import org.yongsae.board.Service.UnknownService;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor
@WebServlet("*.do")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("service invoked.");
		
		try {
			
			String requestURI = request.getRequestURI();
			
			String command = "";
			
			String regex = "/[a-zA-Z0-9]+.do$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(requestURI);
			if(m.find()) {
				command = m.group();
			}	// if
			
			switch(command) { 
//				case "/list.do" : new BoardListService().process(request,response);
//											break;
				case "/list.do" : new BoardPageService().process(request,response);
											break;
				case "/writeui.do" : 	RequestDispatcher dis = request.getRequestDispatcher("/JSP/write.jsp");
													dis.forward(request, response);
													break;
				case "/write.do" : new BoardWriteService().process(request, response);
												response.sendRedirect("/list.do");
												break;
				case "/retrieve.do" : new BoardRetrieveService().process(request, response);
												RequestDispatcher dis3 = request.getRequestDispatcher("/JSP/retrieve.jsp");
												dis3.forward(request, response);
												break;
				case "/update.do" : new BoardUpdateService().process(request, response);
												response.sendRedirect("/list.do");
												break;
				case "/delete.do" : new BoardDeleteService(). process(request, response);
												response.sendRedirect("/list.do");
												break;
				case "/search.do" : new BoardSearchService().process(request, response);
												break;
				case "/replyui.do" : new BoardReplyUIService().process(request, response);
												RequestDispatcher rd = request.getRequestDispatcher("/JSP/reply.jsp");
												rd.forward(request, response);
				case "/reply.do" : new BoardReplyService(). process(request, response);
												response.sendRedirect("/list.do");
												break;
				default : new UnknownService().process(request, response);
			} // switch
			
			
		} catch(Exception e) {
			throw new ServletException(e);
		} // try catch
		
	} // service

} // end calss
