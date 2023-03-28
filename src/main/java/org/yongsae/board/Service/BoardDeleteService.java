package org.yongsae.board.Service;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.persistence.BoardDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardDeleteService implements Service {
	private BoardDAO dao;
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			
		this.dao = new BoardDAO();
			
		
		String num = request.getParameter("num");
		
		dao.delete(num);
		
		
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
