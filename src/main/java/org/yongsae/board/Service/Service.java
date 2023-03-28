package org.yongsae.board.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {

	
	public static final String MODEL_KEY = "__MODEL__";
	
	public abstract void process(HttpServletRequest req, HttpServletResponse res) throws Exception;
	
} // end interface
