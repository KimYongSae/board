package org.yongsae.board.domain;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PageTO {

	ArrayList<BoardVO> list;
	Integer curPage;
	Integer perPage = 5;
	Integer totalCount;
	
	
	
}
