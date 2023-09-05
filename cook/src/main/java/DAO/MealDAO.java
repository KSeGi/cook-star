package DAO;

import java.sql.*;
import java.util.*;

import DTO.Meal;

public class MealDAO {

	private MealDAO() {}
	private static MealDAO instance = new MealDAO();
	
	public static MealDAO getInstance() {
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
		public List<Meal> selectAll(int currentPage, int recoredsPerPage){
			List<Meal> list = new ArrayList<Meal>();
			String sql="select * from mealkit inner join user on mealkit.u_no = user.u_no order by m_no desc limit ?, ?";
			
			
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
					Meal m = new Meal();
					m.setM_no(rs.getInt("m_no"));
					m.setM_nik(rs.getString("u_nik"));
					m.setM_star(rs.getString("m_star"));
					m.setM_title(rs.getString("m_title"));
					m.setM_hit(rs.getInt("m_hit"));
					m.setM_joindate(rs.getString("m_joindate"));
					list.add(m);		
				}
			}catch(Exception e) {
				System.out.println("selectAll 접속 중 오류 발생"+ e);
			}finally {
				MealDAO.close(conn, pstmt, rs);
			}
			return list;
		}
		
		//전체 게시물 수를 가져올 메서드
		public int getNumber() {
			String sql="select count(m_no) from mealkit";
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
				MealDAO.close(conn, pstmt, rs);
			}
			return num;
		}
		//디테일 보기
				public List<Meal> mDetail(int m_no){
					List<Meal> list = new ArrayList<Meal>();
					String sql = "select*from mealkit left outer join user on mealkit.u_no = user.u_no where mealkit.m_no =" +m_no+";";
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						rs = pstmt.executeQuery();
						while(rs.next()) {
							Meal m = new Meal();
							m.setM_no(rs.getInt("m_no"));
							m.setM_nik(rs.getString("u_nik"));
							m.setM_id(rs.getString("u_id"));
							m.setM_star(rs.getString("m_star"));
							m.setM_title(rs.getString("m_title"));
							m.setM_text(rs.getString("m_text"));
							m.setM_img(rs.getString("m_img"));
							m.setM_hit(rs.getInt("m_hit"));
							m.setM_joindate(rs.getString("m_joindate"));
							list.add(m);		
						}
					}catch(Exception e) {
						System.out.println("mDetail 접속 중 오류 발생"+ e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return list;
				}
				
				//게시물가져오기
				public Meal getMeal(int m_no) {
					Meal m = null;
					String sql = "select * from mealkit where m_no=?";
					Connection conn =null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try {
						conn=getConnection();
						pstmt=conn.prepareStatement(sql);
						pstmt.setInt(1,m_no);
						rs=pstmt.executeQuery();
						if(rs.next()) {
							m = new Meal();
							m.setM_no(rs.getInt("m_no"));
							m.setM_star(rs.getString("m_star"));
							m.setM_title(rs.getString("m_title"));
							m.setM_text(rs.getString("m_text"));
							m.setM_img(rs.getString("m_img"));
							m.setM_joindate(rs.getString("m_joindate"));
						}
							
					}catch(Exception e) {
						System.out.println("getMeal 접속 중 오류 발생 : " +e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return m;
				}
				//조회수 증진
				public int updateM_hit(int m_no) {
					int result = -1;
					String sql = "update mealkit set m_hit=m_hit+1 where m_no=?";
					Connection conn = null;
					PreparedStatement pstmt = null;
					try {
						conn=getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1,m_no);

						result=pstmt.executeUpdate();
						
					}catch(Exception e) {
						System.out.println("updateMeal 접속 중 오류 발생 : " +e);
					}finally {
						MealDAO.close(conn, pstmt);
					}
					return result;
				}
				
				
				//게시글 작성하기
				public void insertMeal(Meal m) {
					String sql = "insert into mealkit (u_no, m_title, m_img, m_star, m_text, m_hit) values(?,?,?,?,?,0)";
					Connection conn = null;
					PreparedStatement pstmt = null;
					
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1,m.getU_no());
						pstmt.setString(2,m.getM_title());
						pstmt.setString(3,m.getM_img());
						String value = m.getM_star();
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
						pstmt.setString(5, m.getM_text());
						pstmt.executeUpdate();	
						
					}catch(Exception e) {
						System.out.println("insertMeal 접속 중 오류 발생"+ e);
					}finally {
						MealDAO.close(conn, pstmt);
					}

					
				}
				
				//최신 입력한 게시글 가져오기
				public int mno(Meal m) {
					String sql="select max(m_no) from mealkit where u_no=?";
					int mno=0;
					Connection conn =null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					try {
						conn=getConnection();
						pstmt=conn.prepareStatement(sql);
						pstmt.setInt(1,m.getU_no());
						rs=pstmt.executeQuery();
						rs.next();
						mno=rs.getInt(1);
							
					}catch(Exception e) {
						System.out.println("mno 접속 중 오류 발생 : " +e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return mno;
				}
				
				//게시물 수정하기
				public int updateMeal(Meal m) {
					int result = -1;
					String sql = "update mealkit set m_title=?,m_img=?,m_star=?,m_text=? where m_no=?";
					Connection conn = null;
					PreparedStatement pstmt = null;
					try {
						conn=getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1,m.getM_title());
						pstmt.setString(2,m.getM_img());
						String value = m.getM_star();
						if(value==null) {value="0";}
						switch(value){
							case "0":
								pstmt.setString(3,"☆☆☆☆☆");
								break;
							case "1":
								pstmt.setString(3,"★☆☆☆☆");
								break;
							case "2":
								pstmt.setString(3,"★★☆☆☆");
								break;
							case "3":
								pstmt.setString(3,"★★★☆☆");
								break;
							case "4":
								pstmt.setString(3,"★★★★☆");
								break;
							case "5":
								pstmt.setString(3,"★★★★★");
								break;
						}
						pstmt.setString(4, m.getM_text());
						pstmt.setInt(5, m.getM_no());
						
						result=pstmt.executeUpdate();
						
					}catch(Exception e) {
						System.out.println("updateMeal 접속 중 오류 발생 : " +e);
					}finally {
						MealDAO.close(conn, pstmt);
					}
					return result;
				}
				
				//게시판 삭제하기
				public int deleteMeal(int m_no) {
					int result = -1;
					String sql = "delete from mealkit where m_no="+m_no+";";
					Connection conn = null;
					PreparedStatement pstmt = null;
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						result=pstmt.executeUpdate();	
					
					}catch(Exception e) {
						System.out.println("MealDAD.deleteMeal() 접속 중 오류 발생 : "+e);
					}finally {
						MealDAO.close(conn, pstmt);
					}
					return result;
				}
				//my page 관리
				public List<Meal> selectlist(int u_no){
					List<Meal> list = new ArrayList<Meal>();
					String sql="select * from mealkit inner join user on mealkit.u_no = user.u_no where user.u_no = ? order by m_no desc";
					
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, u_no);
						rs = pstmt.executeQuery();
						while(rs.next()) {
							Meal m = new Meal();
							m.setM_no(rs.getInt("m_no"));
							m.setM_nik(rs.getString("u_nik"));
							m.setM_star(rs.getString("m_star"));
							m.setM_title(rs.getString("m_title"));
							m.setM_hit(rs.getInt("m_hit"));
							m.setM_joindate(rs.getString("m_joindate"));
							list.add(m);		
						}
					}catch(Exception e) {
						System.out.println("selectlist 접속 중 오류 발생"+ e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return list;
				}
				//my page 관리
				public List<Meal> selecwish(int u_no){
					List<Meal> list = new ArrayList<Meal>();
					String sql="select * from mealkit inner join wish on mealkit.m_no=wish.m_no  inner join user on user.u_no = mealkit.u_no where wish.u_no=? order by w_no desc";			
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, u_no);
						rs = pstmt.executeQuery();
						while(rs.next()) {
							Meal m = new Meal();
							m.setM_no(rs.getInt("m_no"));
							m.setM_nik(rs.getString("u_nik"));
							m.setM_star(rs.getString("m_star"));
							m.setM_title(rs.getString("m_title"));
							m.setM_hit(rs.getInt("m_hit"));
							m.setM_joindate(rs.getString("m_joindate"));
							list.add(m);		
						}
					}catch(Exception e) {
						System.out.println("selectlist 접속 중 오류 발생"+ e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return list;
				}
				//search
				public List<Meal> searchAll(String search){
					List<Meal> list = new ArrayList<Meal>();
					String sql="select * from mealkit inner join user on mealkit.u_no=user.u_no where mealkit.m_title like ? order by mealkit.m_no desc";
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					
					try {
						conn = getConnection();
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, "%"+search+"%");
						rs = pstmt.executeQuery();
						while(rs.next()) {
							Meal m = new Meal();
							m.setM_no(rs.getInt("m_no"));
							m.setM_nik(rs.getString("u_nik"));
							m.setM_star(rs.getString("m_star"));
							m.setM_title(rs.getString("m_title"));
							m.setM_hit(rs.getInt("m_hit"));
							m.setM_joindate(rs.getString("m_joindate"));
							list.add(m);		
						}
					}catch(Exception e) {
						System.out.println("selectlist 접속 중 오류 발생"+ e);
					}finally {
						MealDAO.close(conn, pstmt, rs);
					}
					return list;
				}

	
}
