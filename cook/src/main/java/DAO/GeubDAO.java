package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DTO.Geub;

public class GeubDAO {
	private GeubDAO() {}
	private static GeubDAO instance = new GeubDAO();
	
	public static GeubDAO getInstance() {
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
		public List<Geub> selectAll(int currentPage, int recoredsPerPage, int g_list){
			List<Geub> list = new ArrayList<Geub>();
			String sql="";
			switch(g_list){
			case 0:		//전체보기
				sql += "select * from geubsig inner join user on geubsig.u_no = user.u_no order by g_no desc limit ?, ?";
				break;
			case 1:		//학교
				sql += "select * from geubsig inner join user on geubsig.u_no = user.u_no  where g_list=1 order by g_no desc limit ?, ? ";
				break;
			case 2:		//직장
				sql += "select * from geubsig inner join user on geubsig.u_no = user.u_no  where g_list=2 order by g_no desc limit ?, ? ";
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
					Geub g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_nik(rs.getString("u_nik"));
					g.setG_list(rs.getString("g_list"));
					g.setG_star(rs.getString("g_star"));
					g.setG_title(rs.getString("g_title"));
					g.setG_hit(rs.getInt("g_hit"));
					g.setG_joindate(rs.getString("g_joindate"));
					list.add(g);		
				}
			}catch(Exception e) {
				System.out.println("selectAll 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//전체 게시물 수를 가져올 메서드
		public int getNumber(int g_list) {
			String sql="";
			switch(g_list){
				case 0:		//전체보기
					sql += "select count(g_no) from geubsig";
					break;
				case 1:		//학교
					sql += "select count(g_no) from geubsig where g_list=1";
					break;
				case 2:		//급식
					sql += "select count(g_no) from geubsig where g_list=2";
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
				GeubDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		
		//디테일 보기
		public List<Geub> gDetail(int g_no){
			List<Geub> list = new ArrayList<Geub>();
			String sql = "select*from geubsig left outer join user on geubsig.u_no = user.u_no where geubsig.g_no =" +g_no+";";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Geub g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_nik(rs.getString("u_nik"));
					g.setG_id(rs.getString("u_id"));
					g.setG_star(rs.getString("g_star"));
					g.setG_list(rs.getString("g_list"));
					g.setG_title(rs.getString("g_title"));
					g.setG_text(rs.getString("g_text"));
					g.setG_img(rs.getString("g_img"));
					g.setG_hit(rs.getInt("g_hit"));
					g.setG_joindate(rs.getString("g_joindate"));
					list.add(g);		
				}
			}catch(Exception e) {
				System.out.println("gDetail 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//게시물가져오기
		public Geub getGeub(int g_no) {
			Geub g = null;
			String sql = "select * from geubsig where g_no=?";
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,g_no);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_star(rs.getString("g_star"));
					g.setG_list(rs.getString("g_list"));
					g.setG_title(rs.getString("g_title"));
					g.setG_text(rs.getString("g_text"));
					g.setG_img(rs.getString("g_img"));
					g.setG_joindate(rs.getString("g_joindate"));
				}
					
			}catch(Exception e) {
				System.out.println("getGeub 접속 중 오류 발생 : " +e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return g;
		}
		//조회수 증진
		public int updateG_hit(int g_no) {
			int result = -1;
			String sql = "update geubsig set g_hit=g_hit+1 where g_no=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn=getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,g_no);

				result=pstmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updategeub 접속 중 오류 발생 : " +e);
			}finally {
				GeubDAO.close(conn, pstmt);
			}
			return result;
		}
		
		
		//게시글 작성하기
		public void insertGeub(Geub g) {
			String sql = "insert into geubsig (u_no,g_list, g_title, g_img, g_star, g_text, g_hit) values(?,?,?,?,?,?,0)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,g.getU_no());
				pstmt.setString(2,g.getG_list());
				pstmt.setString(3,g.getG_title());
				pstmt.setString(4,g.getG_img());
				String value = g.getG_star();
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
				pstmt.setString(6, g.getG_text());
				pstmt.executeUpdate();	
				
			}catch(Exception e) {
				System.out.println("insertGeub 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt);
			}

			
		}
		
		//최신 입력한 게시글 가져오기
		public int gno(Geub g) {
			String sql="select max(g_no) from geubsig where u_no=?";
			int gno=0;
			Connection conn =null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn=getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1,g.getU_no());
				rs=pstmt.executeQuery();
				rs.next();
				gno=rs.getInt(1);
					
			}catch(Exception e) {
				System.out.println("gno 접속 중 오류 발생 : " +e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return gno;
		}
		
		//게시물 수정하기
		public int updateGeub(Geub g) {
			int result = -1;
			String sql = "update geubsig set g_list=?,g_title=?,g_img=?,g_star=?,g_text=? where g_no=?";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn=getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,g.getG_list());
				pstmt.setString(2,g.getG_title());
				pstmt.setString(3,g.getG_img());
				String value = g.getG_star();
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
				pstmt.setString(5, g.getG_text());
				pstmt.setInt(6, g.getG_no());
				
				result=pstmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("updateGeub 접속 중 오류 발생 : " +e);
			}finally {
				GeubDAO.close(conn, pstmt);
			}
			return result;
		}
		
		//게시판 삭제하기
		public int deleteGeub(int g_no) {
			int result = -1;
			String sql = "delete from geubsig where g_no="+g_no+";";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				result=pstmt.executeUpdate();	
			
			}catch(Exception e) {
				System.out.println("GeubDAD.deleteGeub() 접속 중 오류 발생 : "+e);
			}finally {
				GeubDAO.close(conn, pstmt);
			}
			return result;
		}
		//my page 관리
		public List<Geub> selectlist(int u_no){
			List<Geub> list = new ArrayList<Geub>();
			String sql="select * from geubsig inner join user on geubsig.u_no = user.u_no where user.u_no = ? order by g_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, u_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Geub g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_nik(rs.getString("u_nik"));
					g.setG_list(rs.getString("g_list"));
					g.setG_star(rs.getString("g_star"));
					g.setG_title(rs.getString("g_title"));
					g.setG_hit(rs.getInt("g_hit"));
					g.setG_joindate(rs.getString("g_joindate"));
					list.add(g);		
				}
			}catch(Exception e) {
				System.out.println("selectlist 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		//my wish 관리
		public List<Geub> selecwish(int u_no){
			List<Geub> list = new ArrayList<Geub>();
			String sql="select * from geubsig inner join wish on geubsig.g_no=wish.g_no  inner join user on user.u_no = geubsig.u_no where wish.u_no=? order by w_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, u_no);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Geub g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_nik(rs.getString("u_nik"));
					g.setG_list(rs.getString("g_list"));
					g.setG_star(rs.getString("g_star"));
					g.setG_title(rs.getString("g_title"));
					g.setG_hit(rs.getInt("g_hit"));
					g.setG_joindate(rs.getString("g_joindate"));
					list.add(g);		
				}
			}catch(Exception e) {
				System.out.println("selectlist 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		//search
		public List<Geub> searchAll(String search){
			List<Geub> list = new ArrayList<Geub>();
			String sql="select * from geubsig inner join user on geubsig.u_no=user.u_no where geubsig.g_title like ? order by geubsig.g_no desc";
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+search+"%");
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Geub g = new Geub();
					g.setG_no(rs.getInt("g_no"));
					g.setG_nik(rs.getString("u_nik"));
					g.setG_list(rs.getString("g_list"));
					g.setG_star(rs.getString("g_star"));
					g.setG_title(rs.getString("g_title"));
					g.setG_hit(rs.getInt("g_hit"));
					g.setG_joindate(rs.getString("g_joindate"));
					list.add(g);		
				}
			}catch(Exception e) {
				System.out.println("selectlist 접속 중 오류 발생"+ e);
			}finally {
				GeubDAO.close(conn, pstmt, rs);
			}
			return list;
		}
	
		
}
