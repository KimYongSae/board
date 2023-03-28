package org.yongsae.board.domain;


import lombok.Data;
@Data
public class BoardDTO {
	
	private Integer num;
	private String author;
	private String title;
	private String content;
	private Integer readcnt;
	private String writeday;
	private Integer repRoot;
	private Integer repStep;
	private Integer repIndent;

	
} // end class
