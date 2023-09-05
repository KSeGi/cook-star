package controllerH;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import DTO.Home;
import DTO.User;
import DAO.HomeDAO;
import DAO.WishDAO;
import DAO.CoDAO;
import DTO.Comment;

@WebServlet("/HomeDetail")
public class HomeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomeDAO hdao = HomeDAO.getInstance();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//상세내역 보이는 기능
		int h_no = Integer.parseInt(request.getParameter("h_no"));
		List<Home> list = hdao.hDetail(h_no);
		Home h = hdao.getHome(h_no);
		request.setAttribute("home", h);
		request.setAttribute("HomeDetail", list);
		
		//찜 보이는 기능
		if(user!=null) {
			WishDAO wdao = WishDAO.getInstance();
			int result=1;
			int w_no = wdao.selectWish(user.getU_no(),h_no,result);
			request.setAttribute("w_no", w_no);
		}
		
		//댓글 보이는 기능
		CoDAO cdao = CoDAO.getInstance(); 
		List<Comment> clist = cdao.selectCo(h_no);
		int num = cdao.getNum(h_no);
		request.setAttribute("Comment", clist);
		request.setAttribute("num", num);
		
		//조회수 높이는 기능
		if(user!=null) {
			String u_id=user.getU_id();
			Cookie[] cookies = request.getCookies();
			int visitor=0;
			
			for(Cookie cookie : cookies) {
				//System.out.println(cookie.getName());
				if(cookie.getName().equals(u_id)){
					visitor=1;
					//System.out.println("등록id 있음");
					if(cookie.getValue().contains("H"+request.getParameter("h_no"))) {
						cookie.setMaxAge(60*60*24);
						//System.out.println("번호도 있음 증가 X");
					}else {
						cookie.setValue(cookie.getValue()+"_"+"H"+request.getParameter("h_no"));
						response.addCookie(cookie);
						cookie.setMaxAge(60*60*24);
						hdao.updateH_hit(h_no);
						
						//쿠키에 페이지 번호가 없으면 추가해주고 카운트 늘리기.
					}
					
				}
			}
			if(visitor==0) {
				Cookie cookie1 = new Cookie(u_id,"H"+request.getParameter("h_no"));
				hdao.updateH_hit(h_no);
				cookie1.setMaxAge(60*60*24);
				response.addCookie(cookie1);
				//중복되는 아이디가 없으면 조회수 증가해주기.
			}
		}

		
		RequestDispatcher dis = request.getRequestDispatcher("home/h_detail.jsp");
		dis.forward(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
