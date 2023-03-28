package org.yongsae.board.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.domain.BoardVO;
import org.yongsae.board.persistence.BoardDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardRetrieveService implements Service {
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			
			String num = request.getParameter("num"); 
			BoardDAO dao = new BoardDAO(); 
			BoardVO data = dao.retrieve( num );
			
			request.setAttribute( "retrieve" , data );
			
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
