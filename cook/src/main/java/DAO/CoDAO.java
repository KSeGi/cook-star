package DAO;

import java.sql.*;
import java.util.*;
import DTO.Comment;

public class CoDAO {
	private CoDAO() {}
	private static CoDAO instance = new CoDAO();
	
	public static CoDAO getInstance() {
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
	
	//댓글 가져오기
	public List<Comment> selectCo(int h_no){
		List<Comment> list = new ArrayList<Comment>();
		String sql = "select * from comment inner join user on comment.u_no = user.u_no  where h_no=? order by c_no desc";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,h_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Comment c = new Comment();
				c.setC_no(rs.getInt("c_no"));
				c.setH_no(rs.getInt("h_no"));
				c.setU_no(rs.getInt("u_no"));
				c.setU_nik(rs.getString("u_nik"));
				c.setC_text(rs.getString("c_text"));
				c.setC_joindate(rs.getString("c_joindate"));
				
				list.add(c);		
			}
		}catch(Exception e) {
			System.out.println("selectCo 접속 중 오류 발생"+ e);
		}finally {
			CoDAO.close(conn, pstmt, rs);
		}
		
		return list;
	}
	//댓글 수
	public int getNum(int h_no) {
		String sql="select count(c_no) from comment where h_no=?";
		int num=0;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,h_no);
			rs = pstmt.executeQuery();
			rs.next();
			num=rs.getInt(1);
		}catch(Exception e) {
			System.out.println("getNum 접속 중 오류 발생"+ e);
		}finally {
			CoDAO.close(conn, pstmt, rs);
		}
		return num;
	}
	//댓글 작성하기
	public void insertCo(Comment c) {
		String sql = "insert into comment(u_no,h_no,c_text)values(?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,c.getU_no());
			pstmt.setInt(2,c.getH_no());
			pstmt.setString(3,c.getC_text());
			pstmt.executeUpdate();	
			
		}catch(Exception e) {
			System.out.println("insertCo 접속 중 오류 발생"+ e);
		}finally {
			CoDAO.close(conn, pstmt);
		}
	}
	
	//댓글 삭제하기
	public int deleteCo(int c_no) {
		int result = 0;
		String sql = "delete from comment where c_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,c_no);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("deleteCo 접속 중 오류 발생"+ e);
		}finally {
			CoDAO.close(conn, pstmt);
		}
		return result;
	}
	
	//댓글 수정하기
		public int updateCo(String c_text, int c_no) {
			int result = 0;
			String sql = "update comment set c_text=? where c_no=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,c_text);
				pstmt.setInt(2,c_no);
				result = pstmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updateCo 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt);
			}
			return result;
		}
	
		
		//급식
		//댓글 가져오기
		public List<Comment> selectCoG(int g_no){
			List<Comment> list = new ArrayList<Comment>();
			String sql = "select * from comment inner join user on comment.u_no = user.u_no  where g_no=? order by c_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,g_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Comment c = new Comment();
					c.setC_no(rs.getInt("c_no"));
					c.setH_no(rs.getInt("g_no"));
					c.setU_no(rs.getInt("u_no"));
					c.setU_nik(rs.getString("u_nik"));
					c.setC_text(rs.getString("c_text"));
					c.setC_joindate(rs.getString("c_joindate"));
					
					list.add(c);		
				}
			}catch(Exception e) {
				System.out.println("selectCoG 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			
			return list;
		}
		//댓글 수
		public int getNumG(int g_no) {
			String sql="select count(c_no) from comment where g_no=?";
			int num=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,g_no);
				rs = pstmt.executeQuery();
				rs.next();
				num=rs.getInt(1);
			}catch(Exception e) {
				System.out.println("getNumG 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		//댓글 작성하기
		public void insertCoG(Comment c) {
			String sql = "insert into comment(u_no,g_no,c_text)values(?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,c.getU_no());
				pstmt.setInt(2,c.getG_no());
				pstmt.setString(3,c.getC_text());
				pstmt.executeUpdate();	
				
			}catch(Exception e) {
				System.out.println("insertCo 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt);
			}
		}
	
		
		//밀키트
		//댓글 가져오기
		public List<Comment> selectCoM(int m_no){
			List<Comment> list = new ArrayList<Comment>();
			String sql = "select * from comment inner join user on comment.u_no = user.u_no  where m_no=? order by c_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,m_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Comment c = new Comment();
					c.setC_no(rs.getInt("c_no"));
					c.setH_no(rs.getInt("m_no"));
					c.setU_no(rs.getInt("u_no"));
					c.setU_nik(rs.getString("u_nik"));
					c.setC_text(rs.getString("c_text"));
					c.setC_joindate(rs.getString("c_joindate"));
					
					list.add(c);		
				}
			}catch(Exception e) {
				System.out.println("selectCoM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			
			return list;
		}
		//댓글 수
		public int getNumM(int m_no) {
			String sql="select count(c_no) from comment where m_no=?";
			int num=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,m_no);
				rs = pstmt.executeQuery();
				rs.next();
				num=rs.getInt(1);
			}catch(Exception e) {
				System.out.println("getNumM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		//댓글 작성하기
		public void insertCoM(Comment c) {
			String sql = "insert into comment(u_no,m_no,c_text)values(?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,c.getU_no());
				pstmt.setInt(2,c.getM_no());
				pstmt.setString(3,c.getC_text());
				pstmt.executeUpdate();	
				
			}catch(Exception e) {
				System.out.println("insertCoM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt);
			}
		}
	
		
		//공지사항
		//댓글 가져오기
		public List<Comment> selectCoI(int i_no){
			List<Comment> list = new ArrayList<Comment>();
			String sql = "select * from comment inner join user on comment.u_no = user.u_no  where i_no=? order by c_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,i_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Comment c = new Comment();
					c.setC_no(rs.getInt("c_no"));
					c.setH_no(rs.getInt("i_no"));
					c.setU_no(rs.getInt("u_no"));
					c.setU_nik(rs.getString("u_nik"));
					c.setC_text(rs.getString("c_text"));
					c.setC_joindate(rs.getString("c_joindate"));
					
					list.add(c);		
				}
			}catch(Exception e) {
				System.out.println("selectCoM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			
			return list;
		}
		//댓글 수
		public int getNumI(int i_no) {
			String sql="select count(c_no) from comment where i_no=?";
			int num=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,i_no);
				rs = pstmt.executeQuery();
				rs.next();
				num=rs.getInt(1);
			}catch(Exception e) {
				System.out.println("getNumM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		//댓글 작성하기
		public void insertCoI(Comment c) {
			String sql = "insert into comment(u_no,i_no,c_text)values(?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,c.getU_no());
				pstmt.setInt(2,c.getI_no());
				pstmt.setString(3,c.getC_text());
				pstmt.executeUpdate();	
				
			}catch(Exception e) {
				System.out.println("insertCoM 접속 중 오류 발생"+ e);
			}finally {
				CoDAO.close(conn, pstmt);
			}
		}//my page 관리
		public List<Comment> selectlist(int u_no){
			List<Comment> list = new ArrayList<Comment>();
			String sql="select *  from ( select comment.c_no, comment.h_no, comment.g_no, comment.m_no, comment.c_text, comment.c_joindate, home.h_no as";
			sql+="no, home.h_title as title from comment inner join home on comment.h_no =";
			sql+="home.h_no where comment.u_no=? union all select comment.c_no, comment.h_no, comment.g_no, comment.m_no, comment.c_text, comment.c_joindate, mealkit.m_no as";
			sql+="no, mealkit.m_title as title   from comment inner join mealkit on comment.m_no =";
			sql+="mealkit.m_no where comment.u_no=? union all select comment.c_no, comment.h_no, comment.g_no, comment.m_no, comment.c_text, comment.c_joindate, geubsig.g_no as";
			sql+="no, geubsig.g_title as title  from comment inner join geubsig on comment.g_no =";
			sql+="geubsig.g_no where comment.u_no=? ) as unionResult order by c_no desc";
					
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,u_no);
				pstmt.setInt(2,u_no);
				pstmt.setInt(3,u_no);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Comment c = new Comment();
					c.setC_no(rs.getInt("c_no"));
					c.setH_no(rs.getInt("h_no"));
					c.setG_no(rs.getInt("g_no"));
					c.setM_no(rs.getInt("m_no"));
					c.setC_text(rs.getString("c_text"));
					c.setTitle(rs.getString("title"));
					c.setC_joindate(rs.getString("c_joindate"));
					list.add(c);		
				}
			}catch(Exception e) {
				System.out.println("selectlist 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
	
		
		
		
}
