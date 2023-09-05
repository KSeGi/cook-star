package DAO;

import java.sql.*;
import java.util.*;

import DTO.User;

public class UserDAO {
	private UserDAO() {
		
	}
	private static UserDAO instance = new UserDAO();
	
	public static UserDAO getInstance() {
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
	
	//사용자 인증 (로그인)
	public int userCheck(String u_id, String u_pw) {
		//-1 : 아이디 없음.
		//0 : 비밀번호 틀림.
		//1 : 비밀번호 맞음.
		int result = -1;
		String sql = "select u_pw from user where u_id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, u_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("u_pw").equals(u_pw) && rs.getString("u_pw")!=null) {
					result = 1;	//비밀번호 맞음. 로그인 성공
				}else {
					result=0;	//비밀번호 틀림. 회원가입 오류로 null이 비밀번호에 입력된 경우.
				}
			}else {
				result=-1;		//아이디가 존재하지 않는 경우.
			}

		}catch(Exception e) {
			System.out.println("DAO.userCheck() 내부 접속 중 오류 발생 : "+ e);
		}finally {
			UserDAO.close(conn, pstmt, rs);
		}
				
		return result;
	}
	
	//사용자 정보 조회
	public User getUser(String u_id) {
		User u = null;
		String sql = "select * from user where u_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, u_id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				u=new User();
				u.setU_no(rs.getInt("u_no"));
				u.setU_id(rs.getString("u_id"));
				u.setU_pw(rs.getString("u_pw"));
				u.setU_name(rs.getString("u_name"));
				u.setU_nik(rs.getString("u_nik"));
				u.setU_tel(rs.getString("u_tel"));
				u.setU_grade(rs.getInt("u_grade"));
				u.setU_joindate(rs.getString("u_joindate"));
	
			}
		}catch(Exception e) {
			System.out.println("DAO.getUser() 접속 중 오류 발생 : "+ e);
		}finally{
			UserDAO.close(conn, pstmt, rs);
		}
		return u;
	}
	
	//회원가입 메서드
	public int insertUser(User u) {
		int result=-1;
		String sql= "insert into user(u_id,u_pw,u_name,u_nik,u_tel)values(?,?,?,?,?);";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, u.getU_id());
			pstmt.setString(2, u.getU_pw());
			pstmt.setString(3, u.getU_name());
			pstmt.setString(4, u.getU_nik());
			pstmt.setString(5, u.getU_tel());
			result = pstmt.executeUpdate();
			//변경된 row수가 return값이므로 1만 반환한다.
		}catch(Exception e) {
			System.out.println("UserDAO.insertUser() 접속 중 오류 발생 :"+ e);
		}finally {
			UserDAO.close(conn, pstmt);
		}
		return result;
	}//insertUser
	
	
	//아이디 중복검사
	public int checkId(String id) {
		int idCheck = 0;
		String sql = "select u_id from user where u_id = ?";	//입력값이 테이블에 있는지 확인
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				idCheck = 0;		//이미 존재하는 경우, id 사용 불가능	
			}else {
				idCheck = 1;		//존재하지 않는 경우, id 사용 가능
			}
		
		}catch(Exception e) {
			System.out.println("UserDAO.checkId() 접속 중 오류 발생 :"+ e);
		}finally {
			UserDAO.close(conn, pstmt, rs);
		}
		return idCheck;
	}//checkId
	
	//회원전체 조회
	public List<User> getList(){
		List<User> list = new ArrayList<User>();
		
		String sql = "select * from user";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setU_no(rs.getInt("u_no"));
				u.setU_id(rs.getString("u_id"));
				u.setU_pw(rs.getString("u_pw"));
				u.setU_name(rs.getString("u_name"));
				u.setU_nik(rs.getString("u_nik"));
				u.setU_tel(rs.getString("u_tel"));
				u.setU_grade(rs.getInt("u_grade"));
				u.setU_joindate(rs.getString("u_joindate"));
				list.add(u);
			}
		}catch(Exception e) {
			System.out.println("UserDAD.getList() 접속 중 오류 발생 : "+e);
		}finally {
			UserDAO.close(conn, pstmt, rs);
		}
		return list;

	}//getList
	
	
	//회원삭제
	public int deleteUser(String u) {
		int result = -1;
		
		String sql = "delete from user where u_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u);
			result=pstmt.executeUpdate();	
		
		}catch(Exception e) {
			System.out.println("UserDAD.deleteUser() 접속 중 오류 발생 : "+e);
		}finally {
			UserDAO.close(conn, pstmt);
		}
		return result;
	}//deleteUser
	
	//회원 수정
	public int updateUser(User u) {
		int result =-1;
		String sql = "update user set u_pw=?,u_name=?,u_nik=?,u_tel=?,u_grade=? where u_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getU_pw());
			pstmt.setString(2, u.getU_name());
			pstmt.setString(3, u.getU_nik());
			pstmt.setString(4, u.getU_tel());
			pstmt.setInt(5, u.getU_grade());
			pstmt.setInt(6, u.getU_no());
			
			result=pstmt.executeUpdate();	
			
		}catch(Exception e) {
			System.out.println("UserDAO.updateUser() 접속 중 오류 발생 : "+e);
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			}catch(Exception ex) {
				UserDAO.close(conn, pstmt);
			}
		}
		return result;
		
	}
	
}//UserDAO
