package org.yongsae.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import org.yongsae.board.domain.BoardDTO;
import org.yongsae.board.domain.BoardVO;
import org.yongsae.board.domain.PageTO;

import lombok.Cleanup;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
public class BoardDAO {	
	
	private DataSource dataSource;
	
	public BoardDAO() throws NamingException {
		log.trace("Default constructor invoked.");
		
		String prefix = "java:comp/env/";
		String name = "jdbc/OracleCloudATPWithDriverSpy";
		
		
		Context ctx = new InitialContext();
		log.info("\t+ ctx:{}", ctx);
		
		Object obj = ctx.lookup(prefix+name);
		log.info("\t+ obj: {}",obj);
		
		
		Objects.requireNonNull(obj, "DAO_NULL");
		this.dataSource = (DataSource) obj;
		
		
	} // Default Constructor
	
	public List<BoardVO> list() throws SQLException{
		log.trace("selectAll() invoked.");
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		final String sql = "SELECT num , author, title, content ,	to_char( writeday , 'YYYY/MM/DD') "
								+ "writeday , readcnt , repRoot, repStep, repIndent FROM board "
								+ "order by repRoot desc , repStep asc";
		
		@Cleanup
		Connection conn = this.dataSource.getConnection();
		
		@Cleanup
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		@Cleanup
		ResultSet rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			Integer num = rs.getInt( "num" );
			String author = rs.getString( "author" ); 
			String title = rs.getString( "title" );
			String content = rs.getString( "content" ); 
			Integer readcnt = rs.getInt( "readcnt" );
			String writeday = rs.getString( "writeday" );
			Integer repRoot = rs.getInt( "repRoot");
			Integer repStep = rs.getInt( "repStep" );
			Integer repIndent = rs.getInt( "repIndent" );

			BoardVO vo = new BoardVO(num, author, title, content, readcnt, writeday, repRoot, repStep, repIndent);

			
			
			list.add(vo);
		} // while
		
		return list;
		
	} // list
	
	public void write( String _author, String _title, String _content) throws ServletException {
		
		try {
		final String sql =
		"INSERT INTO board (num, author, title, content, repRoot, repStep, repIndent) values (board_seq.NEXTVAL,?,?,?, board_seq.CURRVAL, 0, 0)";
	@Cleanup
	Connection conn = this.dataSource.getConnection();
	
	@Cleanup
	PreparedStatement pstmt = conn.prepareStatement(sql);
	
	pstmt.setString( 1, _author ); 
	pstmt.setString( 2, _title ); 
	pstmt.setString( 3, _content );
	int n = pstmt.executeUpdate();
	
		} catch (Exception e) {
			
			throw new ServletException(e);
			
		}
		
	}// write
	
	public void readCount( String _num) throws ServletException {
		
		
		try {
			
			final String sql = "UPDATE board SET readcnt = readcnt + 1 WHERE num="+ _num;
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			int n = pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	} // readCount
	
	public BoardVO retrieve(String _num) throws ServletException {
		
		readCount(_num);
		
		BoardVO vo = null;
		
		try {
		
			String sql = "SELECT * FROM board WHERE num = ?";
			
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(_num));
			
			@Cleanup
			ResultSet rs = pstmt.executeQuery();
			rs= pstmt.executeQuery();
		
			if( rs.next()){
				Integer num = rs.getInt( "num" );
				String title = rs.getString( "title" );
				String author = rs.getString( "author" ); 
				String content = rs.getString( "content" ); 
				String writeday = rs.getString( "writeday" ); 
				Integer readcnt = rs.getInt( "readcnt" );
				Integer repRoot = rs.getInt( "repRoot");
				Integer repStep = rs.getInt( "repStep" );
				Integer repIndent = rs.getInt( "repIndent" );

				vo = new BoardVO(num, author, title, content, readcnt, writeday, repRoot, repStep, repIndent);


			} // end if
			
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
		return vo;
		
	} // retrieve
	
	public void update(String _num, String _title, String _author, String _content) throws ServletException {
		
		
		try {
			
			String sql = "UPDATE board SET title = ? , author = ? , content = ? WHERE num = ?";
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString ( 1 , _title );
			pstmt.setString( 2, _author ); 
			pstmt.setString( 3 , _content );
			pstmt.setInt( 4 , Integer.parseInt( _num ) );
			
			int n = pstmt.executeUpdate();
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
		
		
	} // update
	
	
	public void delete( String _num) throws ServletException {
		
		try {
			
			String sql = "DELETE FROM board WHERE num= ?";
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(_num));
			
			int n = pstmt.executeUpdate();
			
			
			
		} catch(Exception e ) {
			throw new ServletException(e);
		}
		
		
	} // delete
	
	
	
	public List<BoardVO> search(String _searchName, String _searchValue) throws ServletException{
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			
			String sql =
			"SELECT num , author, title, content ,	to_char( writeday , 'YYYY/MM/DD') "
			+ "writeday , readcnt , repRoot, repStep, repIndent FROM board ";
			
			
			if(_searchName.equals("title")) {
				sql += " WHERE title LIKE ?";
			} else {
				sql += " WHERE author LIKE ?";
			}
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+_searchValue+"%");
			
			@Cleanup
			ResultSet rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				Integer num = rs.getInt( "num" );
				String author = rs.getString( "author" ); 
				String title = rs.getString( "title" );
				String content = rs.getString( "content" ); 
				Integer readcnt = rs.getInt( "readcnt" );
				String writeday = rs.getString( "writeday" );
				Integer repRoot = rs.getInt( "repRoot");
				Integer repStep = rs.getInt( "repStep" );
				Integer repIndent = rs.getInt( "repIndent" );

				BoardVO vo = new BoardVO(num, author, title, content, readcnt, writeday, repRoot, repStep, repIndent);
				list.add(vo);
			}
			
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		return list;
		
	} // search
	
	
	public BoardDTO replyui(String _num) throws ServletException {
		
		BoardDTO data = new BoardDTO();
		
		try {
			String sql = "SELECT * FROM board WHERE num = ? ";
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(_num));
			
			@Cleanup
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				data.setNum( rs.getInt( "num" )); 
				data.setTitle( rs.getString( "title" )); 
				data.setAuthor( rs.getString( "author" )); 
				data.setContent( rs.getString( "content" )); 
				data.setWriteday( rs.getString( "writeday" )); 
				data.setReadcnt( rs.getInt( "readcnt" )); 
				data.setRepRoot( rs.getInt( "repRoot" )); 
				data.setRepStep( rs.getInt( "repStep" )); 
				data.setRepIndent( rs.getInt( "repIndent" ));
			}
			
			
			
		}catch (Exception e) {
			throw new ServletException(e);
		}
		
		return data;
	} // replyui
	
	public void makeReply( String _root, String _step) throws ServletException {
		
		
		try {
			
			String sql = "UPDATE board SET repStep = repStep + 1 WHERE repRoot = ? AND repStep > ? ";
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(_root));
			pstmt.setInt(2, Integer.parseInt(_step));
			
			int n = pstmt.executeUpdate();
			
			
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
		
		
		
	} // makeReply
	
	
	public void reply(String _num, String _title, String _author, String _content, String _repRoot, String _repStep, String _repIndent	) throws ServletException {
		
		
		makeReply(_repRoot, _repStep);
		try {
					
					String sql = 
							"INSERT INTO board (num, author, title, content, repRoot, repStep, repIndent) values (board_seq.NEXTVAL,?,?,?,?,?,?)";
 
					
					@Cleanup
					Connection conn = this.dataSource.getConnection();
					
					@Cleanup
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, _author);
					pstmt.setString(2, _title);
					pstmt.setString(3, _content);
					pstmt.setInt(4, Integer.parseInt(_repRoot));
					pstmt.setInt(5, Integer.parseInt(_repStep)+1);
					pstmt.setInt(6, Integer.parseInt(_repIndent)+1);
					
					int n = pstmt.executeUpdate();
					
					
					
				} catch(Exception e) {
					throw new ServletException(e);
				}
				
		
		
	} // reply
	
	
	public int totalCount() throws ServletException {
		int count = 0;
		
		try {
			
			String sql = 
					"SELECT count(*) FROM board";
			
			@Cleanup
			Connection conn = this.dataSource.getConnection();
			
			@Cleanup
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			@Cleanup
			ResultSet rs = pstmt.executeQuery();
			
			if( rs.next()){
				count = rs.getInt( 1 );
				}
		} catch(Exception e ) {
			throw new ServletException(e);
		}
		return count;
	} // totalCount
	
	public PageTO page(int curPage) throws ServletException {
		PageTO to = new PageTO();
		int totalCount = totalCount();
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			
String sql = "Select num, author, title, content,to_char( writeday , 'YYYY/MM/DD') writeday ,"
		+ " readcnt , repRoot, repStep, repIndent FROM board order by repRoot desc , repStep asc";
			
			
			
		@Cleanup
		Connection conn = this.dataSource.getConnection();
		
		@Cleanup
		PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY );
		
		@Cleanup
		ResultSet rs = pstmt.executeQuery();
		
		int perPage = to.getPerPage();
		
		int skip = (curPage - 1) * perPage;
		if(skip>0){
			rs.absolute( skip );
		}
		
		for(int i = 0; i < perPage && rs.next(); i++) {
			Integer num = rs.getInt( "num" );
			String author = rs.getString( "author" ); 
			String title = rs.getString( "title" );
			String content = rs.getString( "content" ); 
			Integer readcnt = rs.getInt( "readcnt" );
			String writeday = rs.getString( "writeday" );
			Integer repRoot = rs.getInt( "repRoot");
			Integer repStep = rs.getInt( "repStep" );
			Integer repIndent = rs.getInt( "repIndent" );
			
			BoardVO vo = new BoardVO(num, author, title, content, readcnt, writeday, repRoot, repStep, repIndent);
			
			list.add(vo);
		}
		
		to.setList(list);
		to.setTotalCount(totalCount);
		to.setCurPage(curPage);
		
		
		
		
		} catch( Exception e) {
			throw new ServletException(e);
		}
		
		
		return to;
	}
	
	
	
	
	
	
} // end class










