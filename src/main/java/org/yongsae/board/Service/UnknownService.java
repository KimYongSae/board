package org.yongsae.board.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class UnknownService implements Service {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) {
		log.trace("process invoked.");
	}

}
