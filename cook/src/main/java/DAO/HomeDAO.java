package DAO;

import java.sql.*;
import java.util.*;

import DTO.Home;

public class HomeDAO {
	private HomeDAO() {}
	private static HomeDAO instance = new HomeDAO();
	
	public static HomeDAO getInstance() {
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
	public List<Home> selectAll(int currentPage, int recoredsPerPage, int h_list){
		List<Home> list = new ArrayList<Home>();
		String sql="";
		switch(h_list){
		case 0:		//전체보기
			sql += "select * from home inner join user on home.u_no = user.u_no order by h_no desc limit ?, ?";
			break;
		case 1:		//다인
			sql += "select * from home inner join user on home.u_no = user.u_no  where h_list=1 order by h_no desc limit ?, ? ";
			break;
		case 2:		//1인
			sql += "select * from home inner join user on home.u_no = user.u_no  where h_list=2 order by h_no desc limit ?, ? ";
			break;
		case 3:		//채식
			sql += "select * from home inner join user on home.u_no = user.u_no  where h_list=3 order by h_no desc limit ?, ? ";
			break;
		
		}
		
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
				Home h = new Home();
				h.setH_no(rs.getInt("h_no"));
				h.setNik(rs.getString("u_nik"));
				h.setH_list(rs.getString("h_list"));
				h.setH_star(rs.getString("h_star"));
				h.setH_title(rs.getString("h_title"));
				h.setH_hit(rs.getInt("h_hit"));
				h.setH_joindate(rs.getString("h_joindate"));
				list.add(h);		
			}
		}catch(Exception e) {
			System.out.println("selectAll 접속 중 오류 발생"+ e);
		}finally {
			HomeDAO.close(conn, pstmt, rs);
		}
		return list;
	}
	
	//전체 게시물 수를 가져올 메서드
	public int getNumber(int h_list) {
		String sql="";
		switch(h_list){
			case 0:		//전체보기
				sql += "select count(h_no) from home";
				break;
			case 1:		//다인
				sql += "select count(h_no) from home where h_list=1";
				break;
			case 2:		//1인
				sql += "select count(h_no) from home where h_list=2";
				break;
			case 3:		//채식
				sql += "select count(h_no) from home where h_list=3";
				break;
			}
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
			HomeDAO.close(conn, pstmt, rs);
		}
		return num;
	}
	
	

	//디테일 보기
	public List<Home> hDetail(int h_no){
		List<Home> list = new ArrayList<Home>();
		String sql = "select*from home left outer join user on home.u_no = user.u_no where home.h_no =" +h_no+";";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Home h = new Home();
				h.setH_no(rs.getInt("h_no"));
				h.setNik(rs.getString("u_nik"));
				h.setH_id(rs.getString("u_id"));
				h.setH_star(rs.getString("h_star"));
				h.setH_list(rs.getString("h_list"));
				h.setH_title(rs.getString("h_title"));
				h.setH_text(rs.getString("h_text"));
				h.setH_img(rs.getString("h_img"));
				h.setH_hit(rs.getInt("h_hit"));
				h.setH_joindate(rs.getString("h_joindate"));
				list.add(h);		
			}
		}catch(Exception e) {
			System.out.println("hDetail 접속 중 오류 발생"+ e);
		}finally {
			HomeDAO.close(conn, pstmt, rs);
		}
		return list;
	}
	//게시판 삭제하기
	public int deleteHome(int h_no) {
		int result = -1;
		String sql = "delete from home where h_no="+h_no+";";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			result=pstmt.executeUpdate();	
		
		}catch(Exception e) {
			System.out.println("HomeDAD.deleteHome() 접속 중 오류 발생 : "+e);
		}finally {
			HomeDAO.close(conn, pstmt);
		}
		return result;
	}//deleteUser
	
	//게시글 작성하기
	public void insertHome(Home h) {
		String sql = "insert into home (u_no,h_list, h_title, h_img, h_star, h_text, h_hit) values(?,?,?,?,?,?,0)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,h.getU_no());
			pstmt.setString(2,h.getH_list());
			pstmt.setString(3,h.getH_title());
			pstmt.setString(4,h.getH_img());
			String value = h.getH_star();
			if(value==null) {value="0";}
			switch(value){
				case "0":
					pstmt.setString(5,"☆☆☆☆☆");
					break;
				case "1":
					pstmt.setString(5,"★☆☆☆☆");
					break;
				case "2":
					pstmt.setString(5,"★★☆☆☆");
					break;
				case "3":
					pstmt.setString(5,"★★★☆☆");
					break;
				case "4":
					pstmt.setString(5,"★★★★☆");
					break;
				case "5":
					pstmt.setString(5,"★★★★★");
					break;
			}
			pstmt.setString(6, h.getH_text());
			pstmt.executeUpdate();	
			
		}catch(Exception e) {
			System.out.println("insertHome 접속 중 오류 발생"+ e);
		}finally {
			HomeDAO.close(conn, pstmt);
		}
		
	}
	
	//최신 입력한 게시글 가져오기
	public int hno(Home h) {
		String sql="select max(h_no) from home where u_no=?";
		int hno=0;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,h.getU_no());
			rs=pstmt.executeQuery();
			rs.next();
			hno=rs.getInt(1);
				
		}catch(Exception e) {
			System.out.println("hno 접속 중 오류 발생 : " +e);
		}finally {
			HomeDAO.close(conn, pstmt, rs);
		}
		return hno;
	}
	
	//게시물가져오기
	public Home getHome(int h_no) {
		Home h = null;
		String sql = "select * from home where h_no=?";
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,h_no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				h = new Home();
				h.setH_no(rs.getInt("h_no"));
				h.setH_star(rs.getString("h_star"));
				h.setH_list(rs.getString("h_list"));
				h.setH_title(rs.getString("h_title"));
				h.setH_text(rs.getString("h_text"));
				h.setH_img(rs.getString("h_img"));
				h.setH_joindate(rs.getString("h_joindate"));
			}
				
		}catch(Exception e) {
			System.out.println("getHome 접속 중 오류 발생 : " +e);
		}finally {
			HomeDAO.close(conn, pstmt, rs);
		}
		return h;
	}
	//게시물 수정하기
	public int updateHome(Home h) {
		int result = -1;
		String sql = "update home set h_list=?,h_title=?,h_img=?,h_star=?,h_text=? where h_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,h.getH_list());
			pstmt.setString(2,h.getH_title());
			pstmt.setString(3,h.getH_img());
			String value = h.getH_star();
			if(value==null) {value="0";}
			switch(value){
				case "0":
					pstmt.setString(4,"☆☆☆☆☆");
					break;
				case "1":
					pstmt.setString(4,"★☆☆☆☆");
					break;
				case "2":
					pstmt.setString(4,"★★☆☆☆");
					break;
				case "3":
					pstmt.setString(4,"★★★☆☆");
					break;
				case "4":
					pstmt.setString(4,"★★★★☆");
					break;
				case "5":
					pstmt.setString(4,"★★★★★");
					break;
			}
			pstmt.setString(5, h.getH_text());
			pstmt.setInt(6, h.getH_no());
			
			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("updateHome 접속 중 오류 발생 : " +e);
		}finally {
			HomeDAO.close(conn, pstmt);
		}
		return result;
	}
	//조회수 증진
	public int updateH_hit(int h_no) {
		int result = -1;
		String sql = "update home set h_hit=h_hit+1 where h_no=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,h_no);

			result=pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("updateHome 접속 중 오류 발생 : " +e);
		}finally {
			HomeDAO.close(conn, pstmt);
		}
		return result;
	}
	//my page 관리
		public List<Home> selectlist(int u_no){
			List<Home> list = new ArrayList<Home>();
			String sql="select * from home inner join user on home.u_no = user.u_no where user.u_no = ? order by h_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, u_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Home h = new Home();
					h.setH_no(rs.getInt("h_no"));
					h.setNik(rs.getString("u_nik"));
					h.setH_list(rs.getString("h_list"));
					h.setH_star(rs.getString("h_star"));
					h.setH_title(rs.getString("h_title"));
					h.setH_hit(rs.getInt("h_hit"));
					h.setH_joindate(rs.getString("h_joindate"));
					list.add(h);
				}
			}catch(Exception e) {
				System.out.println("selectlist 접속 중 오류 발생"+ e);
			}finally {
				HomeDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//my wish 관리
			public List<Home> selecwish(int u_no){
				List<Home> list = new ArrayList<Home>();
				String sql="select * from home inner join wish on home.h_no=wish.h_no  inner join user on user.u_no = home.u_no where wish.u_no=? order by w_no desc";
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
					
				try {
					conn = getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, u_no);
					rs = pstmt.executeQuery();
					while(rs.next()) {
						Home h = new Home();
						h.setH_no(rs.getInt("h_no"));
						h.setNik(rs.getString("u_nik"));
						h.setH_list(rs.getString("h_list"));
						h.setH_star(rs.getString("h_star"));
						h.setH_title(rs.getString("h_title"));
						h.setH_hit(rs.getInt("h_hit"));
						h.setH_joindate(rs.getString("h_joindate"));
						list.add(h);
					}
				}catch(Exception e) {
					System.out.println("selecwish 접속 중 오류 발생"+ e);
				}finally {
					HomeDAO.close(conn, pstmt, rs);
				}
				return list;
			}
			
			
		//Search
			public List<Home> searchAll(String search){
				List<Home> list = new ArrayList<Home>();
				String sql="select * from home inner join user on home.u_no=user.u_no where home.h_title like ? order by home.h_no desc";
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
					
				try {
					conn = getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+search+"%");
					rs = pstmt.executeQuery();
					while(rs.next()) {
						Home h = new Home();
						h.setH_no(rs.getInt("h_no"));
						h.setNik(rs.getString("u_nik"));
						h.setH_list(rs.getString("h_list"));
						h.setH_star(rs.getString("h_star"));
						h.setH_title(rs.getString("h_title"));
						h.setH_hit(rs.getInt("h_hit"));
						h.setH_joindate(rs.getString("h_joindate"));
						list.add(h);
					}
				}catch(Exception e) {
					System.out.println("searchAll 접속 중 오류 발생"+ e);
				}finally {
					HomeDAO.close(conn, pstmt, rs);
				}
				return list;
			}
				
					
}

