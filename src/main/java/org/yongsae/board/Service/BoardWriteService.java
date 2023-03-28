package org.yongsae.board.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.persistence.BoardDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardWriteService implements Service {
	private BoardDAO dao;
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String content = request.getParameter("content");
			
			this.dao = new BoardDAO();
			dao.write(author, title, content);
			
			
			
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
