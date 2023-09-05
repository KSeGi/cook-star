package DAO;

import java.sql.*;
import java.util.*;

import DTO.Info;

public class InfoDAO {
	private InfoDAO() {}
	private static InfoDAO instance = new InfoDAO();
	
	public static InfoDAO getInstance() {
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
	
	//전체 상품 보기, 페이지처리
		public List<Info> selectAll(int currentPage, int recoredsPerPage){
			List<Info> list = new ArrayList<Info>();
			String sql="select * from info inner join user on info.u_no = user.u_no order by i_no desc limit ?, ?";
			
			
			int start=currentPage*recoredsPerPage-recoredsPerPage;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, start);
				pstmt.setInt(2, recoredsPerPage);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Info i = new Info();
					i.setI_no(rs.getInt("i_no"));
					i.setI_nik(rs.getString("u_nik"));
					i.setI_title(rs.getString("i_title"));
					i.setI_hit(rs.getInt("i_hit"));
					i.setI_joindate(rs.getString("i_joindate"));
					list.add(i);		
				}
			}catch(Exception e) {
				System.out.println("selectAll 접속 중 오류 발생"+ e);
			}finally {
				InfoDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//전체 게시물 수를 가져올 메서드
		public int getNumber() {
			String sql="select count(i_no) from info";
			int num=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				rs.next();
				num=rs.getInt(1);
				
			}catch(Exception e) {
				System.out.println("getNumber 접속 중 오류 발생 : " +e);
			}finally {
				InfoDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		//디테일 보기
		public List<Info> iDetail(int i_no){
			List<Info> list = new ArrayList<Info>();
			String sql = "select*from info left outer join user on info.u_no = user.u_no where info.i_no =" +i_no+";";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Info i = new Info();
					i.setI_no(rs.getInt("i_no"));
					i.setI_nik(rs.getString("u_nik"));
					i.setI_id(rs.getString("u_id"));
					i.setI_title(rs.getString("i_title"));
					i.setI_text(rs.getString("i_text"));
					i.setI_img(rs.getString("i_img"));
					i.setI_hit(rs.getInt("i_hit"));
					i.setI_joindate(rs.getString("i_joindate"));
					list.add(i);		
				}
			}catch(Exception e) {
				System.out.println("iDetail 접속 중 오류 발생"+ e);
			}finally {
				InfoDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//게시물가져오기
		public Info getInfo(int i_no) {
			Info i = null;
			String sql = "select * from info where i_no=?";
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,i_no);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					i = new Info();
					i.setI_no(rs.getInt("i_no"));
					i.setI_title(rs.getString("i_title"));
					i.setI_text(rs.getString("i_text"));
					i.setI_img(rs.getString("i_img"));
					i.setI_joindate(rs.getString("i_joindate"));
				}
					
			}catch(Exception e) {
				System.out.println("getInfo 접속 중 오류 발생 : " +e);
			}finally {
				InfoDAO.close(conn, pstmt, rs);
			}
			return i;
		}
		//조회수 증진
		public int updateI_hit(int i_no) {
			int result = -1;
			String sql = "update info set i_hit=i_hit+1 where i_no=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn=getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,i_no);

				result=pstmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updateInfo 접속 중 오류 발생 : " +e);
			}finally {
				InfoDAO.close(conn, pstmt);
			}
			return result;
		}
		
		
		//게시글 작성하기
		public void insertInfo(Info i) {
			String sql = "insert into info (u_no, i_title, i_img, i_text, i_hit) values(?,?,?,?,0)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,i.getU_no());
				pstmt.setString(2,i.getI_title());
				pstmt.setString(3,i.getI_img());
				pstmt.setString(4, i.getI_text());
				pstmt.executeUpdate();	
				
			}catch(Exception e) {
				System.out.println("insertInfo 접속 중 오류 발생"+ e);
			}finally {
				InfoDAO.close(conn, pstmt);
			}

			
		}
		
		//최신 입력한 게시글 가져오기
		public int ino(Info i) {
			String sql="select max(i_no) from info where u_no=?";
			int ino=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,i.getU_no());
				rs=pstmt.executeQuery();
				rs.next();
				ino=rs.getInt(1);
					
			}catch(Exception e) {
				System.out.println("ino 접속 중 오류 발생 : " +e);
			}finally {
				InfoDAO.close(conn, pstmt, rs);
			}
			return ino;
		}
		
		//게시물 수정하기
		public int updateInfo(Info i) {
			int result = -1;
			String sql = "update info set i_title=?,i_img=?,i_text=? where i_no=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn=getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,i.getI_title());
				pstmt.setString(2,i.getI_img());
				pstmt.setString(3, i.getI_text());
				pstmt.setInt(4, i.getI_no());
				
				result=pstmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updateInfo 접속 중 오류 발생 : " +e);
			}finally {
				InfoDAO.close(conn, pstmt);
			}
			return result;
		}
		
		//게시판 삭제하기
		public int deleteInfo(int i_no) {
			int result = -1;
			String sql = "delete from info where i_no="+i_no+";";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				result=pstmt.executeUpdate();	
			
			}catch(Exception e) {
				System.out.println("InfoDAD.deleteInfo() 접속 중 오류 발생 : "+e);
			}finally {
				InfoDAO.close(conn, pstmt);
			}
			return result;
		}//deleteUser

		
		
		
}
