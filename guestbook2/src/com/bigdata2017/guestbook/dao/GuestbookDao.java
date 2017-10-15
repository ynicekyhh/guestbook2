package com.bigdata2017.guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2017.guestbook.vo.GuestbookVo;

public class GuestbookDao {

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		String url = null;
		
		try {
			//1. JDBC 드라이버 로딩(JDBC 클래스 로딩)
			Class.forName( "oracle.jdbc.driver.OracleDriver" );

			//2. Connection 가져오기
			/* "jdbc:oracle:thin:" 이건 정해져 있는 포맷 */
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection( url, "webdb", "webdb" );
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패 : " + e);
		}
		
		return conn;
	}
	

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = null;
		
		try {
			conn = getConnection();
			
			//3. Statement 객체 생성
			stmt = conn.createStatement();
			
			//4. 바인딩
			//5. SQL 문 실행
			sql = 
					"  select rownum," + 
					"		no," + 
					"		name," + 
					"		content," + 
					"		\"date\"" + 
					"   from (" + 
					"		  select no," + 
					"				 name," + 
					"				 content," + 
					"				 to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') as \"date\"" + 
					"			from guestbook" + 
					"		order by reg_date desc" + 
					"   	)";
			rs = stmt.executeQuery(sql);
			
			//6. 결과 가져오기
			while( rs.next() ) {
				GuestbookVo vo = new GuestbookVo();
				vo.setRownum( rs.getLong("rownum") );
				vo.setNo( rs.getLong("no") );
				vo.setName( rs.getString("name") );
				vo.setContent( rs.getString("content") );
				vo.setDate( rs.getString("date") );
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("연결실패: " + e);
		} finally {
			try {
				if( rs != null ) {
					rs.close();
				}
				if( stmt != null ) {
					stmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("연결 끊기 실패: " + e);
			}
		}
		
		return list;
	}
	
	public int insert( GuestbookVo vo ) {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = null;
		
		try {
			conn = getConnection();
			
			sql = 
					" insert" + 
					"   into guestbook" + 
					" values ( seq_guestbook.nextval, ?, ?, ?, sysdate )";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getPassword() );
			pstmt.setString( 3, vo.getContent() );
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("연결실패: " + e);
		} finally {
			try {
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("연결 끊기 실패 : " + e);
			}
		}
		
		return count;
	}
	
	public int delete(Long no, String password) {
		int count = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = null;
		
		try {
			conn = getConnection();
			
			sql = 
					"delete " + 
					"  from guestbook" + 
					" where no = ?" + 
					"   and password = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong( 1, no);
			pstmt.setString( 2, password);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("연결실패: " + e);
		}
		
		return count;
	}
}
