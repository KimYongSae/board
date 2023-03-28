package org.yongsae.board.Service;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.domain.BoardDTO;
import org.yongsae.board.persistence.BoardDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardReplyService implements Service {
	private BoardDAO dao;
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			
		this.dao = new BoardDAO();

		String num = request.getParameter("num");
		String title = request.getParameter( "title" );
		String author = request. getParameter( "author" );
		String content = request.getParameter( "content" );
		String repRoot = request.getParameter( "repRoot" );
		String repStep = request.getParameter( "repStep" );
		String repIndent = request.getParameter( "repIndent" );
		
		dao.reply(num, title, author, content, repRoot, repStep, repIndent);
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
