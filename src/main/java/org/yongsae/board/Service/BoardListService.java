package org.yongsae.board.Service;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.domain.BoardVO;
import org.yongsae.board.persistence.BoardDAO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardListService implements Service {
	private BoardDAO dao;
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			
		this.dao = new BoardDAO();
		log.info("\t+ this.dao: {}", this.dao);
			
		@Cleanup("clear")
		List<BoardVO> boardList = this.dao.list();
		
		request.setAttribute("BOARD_LIST", boardList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/list.jsp");
		rd.forward(request, response);
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
