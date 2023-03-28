package org.yongsae.board.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.yongsae.board.domain.BoardVO;
import org.yongsae.board.domain.PageTO;
import org.yongsae.board.persistence.BoardDAO;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@NoArgsConstructor
@Log4j2
public class BoardPageService implements Service {
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.trace("process invoked.");
		
		try {
			
			int curPage = 1;
			if(request.getParameter("curPage") != null) {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}
			
		BoardDAO dao = new BoardDAO();
		PageTO list = dao.page(curPage);
		request.setAttribute("BOARD_LIST", list.getList()	);
		
		request.setAttribute("page", list);
			
		
		RequestDispatcher rd = request.getRequestDispatcher("/JSP/listPage.jsp");
		rd.forward(request, response);
			
		} catch (Exception e ) {
			throw new ServletException(e);
		}
	}

}
