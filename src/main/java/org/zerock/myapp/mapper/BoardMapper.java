package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yongsae.board.domain.BoardVO;

public interface BoardMapper {
	
	public abstract List<BoardVO> selectAll();
	
} // end class
