package controllerG;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CoDAO;
import DAO.GeubDAO;
import DAO.WishDAO;
import DTO.Comment;
import DTO.Geub;
import DTO.User;


@WebServlet("/GeubDetail")
public class GeubDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GeubDAO gdao = GeubDAO.getInstance();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		//상세내역 보이는 기능
		int g_no = Integer.parseInt(request.getParameter("g_no"));
		List<Geub> list = gdao.gDetail(g_no);
		Geub g = gdao.getGeub(g_no);
		request.setAttribute("geub", g);
		request.setAttribute("GeubDetail", list);
		
		//찜 보이는 기능
		if(user!=null) {
			WishDAO wdao = WishDAO.getInstance();
			int result=2;
			int w_no = wdao.selectWish(user.getU_no(),g_no,result);
			request.setAttribute("w_no", w_no);
		}
		
		//댓글 보이는 기능
		CoDAO cdao = CoDAO.getInstance(); 
		List<Comment> clist = cdao.selectCoG(g_no);
		int num = cdao.getNumG(g_no);
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
					if(cookie.getValue().contains("G"+request.getParameter("g_no"))) {
						cookie.setMaxAge(60*60*24);
						//System.out.println("번호도 있음 증가 X");
					}else {
						cookie.setValue(cookie.getValue()+"_"+"G"+request.getParameter("g_no"));
						response.addCookie(cookie);
						cookie.setMaxAge(60*60*24);
						gdao.updateG_hit(g_no);
						
						//쿠키에 페이지 번호가 없으면 추가해주고 카운트 늘리기.
					}
					
				}
			}
			if(visitor==0) {
				Cookie cookieG = new Cookie(u_id,"G"+request.getParameter("g_no"));
				gdao.updateG_hit(g_no);
				cookieG.setMaxAge(60*60*24);
				response.addCookie(cookieG);
				//중복되는 아이디가 없으면 조회수 증가해주기.
			}
		}

		
		RequestDispatcher dis = request.getRequestDispatcher("geub/g_detail.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
