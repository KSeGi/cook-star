package DAO;

import java.sql.*;


public class WishDAO {
	private WishDAO() {}
	private static WishDAO instance = new WishDAO();
	
	public static WishDAO getInstance() {
		return instance;
	}
	public Connection getConnection() throws Exception{
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/cook";
		String id = "root";
		String pw ="1234";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection(url,id,pw);
		return conn;
	}
	
	//데이터베이스 연결해제 메서드
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e){
			System.out.println("연결 해제 중 오류 발생 : "+e);
		}
	}
	public static void close(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
		}catch(Exception e) {
			System.out.println("연결 해제 중 오류 발생 : "+e);
		}
	}
	
	//찜하기
	
	public void wishLike(int u_no, int no, int result) {
		
		String sql="";
		switch(result) {
		case 1:		//home
			sql += "insert into wish(u_no,h_no)values(?,?)";
			break;
		case 2:		//geubsig
			sql += "insert into wish(u_no,g_no)values(?,?)";
			break;
		case 3:		//mealkit
			sql += "insert into wish(u_no,m_no)values(?,?)";
			break;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,u_no);
			pstmt.setInt(2,no);
			pstmt.executeUpdate();	
			
		}catch(Exception e) {
			System.out.println("wishLike 접속 중 오류 발생"+ e);
		}finally {
			WishDAO.close(conn, pstmt);
		}
	}
	
	public int selectWish(int u_no,int no, int result) {
		String sql="";
		switch(result) {
		case 1:		//home
			sql += "select w_no from wish where u_no=? and h_no=?";
			break;
		case 2:		//geubsig
			sql += "select w_no from wish where u_no=? and g_no=?";
			break;
		case 3:		//mealkit
			sql += "select w_no from wish where u_no=? and m_no=?";
			break;
		}
		int w_no=0;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,u_no);
			pstmt.setInt(2,no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				w_no=rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("selectWish 접속 중 오류 발생 : " +e);
		}finally {
			WishDAO.close(conn, pstmt, rs);
		}
		
		return w_no;
	}
	
	//찜 삭제
	public int deleteWish(int w_no) {
		int result = 0;
		String sql = "delete from wish where w_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,w_no);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("deleteWish 접속 중 오류 발생"+ e);
		}finally {
			CoDAO.close(conn, pstmt);
		}
		return result;
	}
	
}
