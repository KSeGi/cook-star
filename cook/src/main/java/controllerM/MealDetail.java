package controllerM;

import java.io.IOException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.CoDAO;
import DAO.MealDAO;
import DAO.WishDAO;
import DTO.Comment;
import DTO.Meal;
import DTO.User;


@WebServlet("/MealDetail")
public class MealDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MealDAO mdao = MealDAO.getInstance();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		//상세내역 보이는 기능
		int m_no = Integer.parseInt(request.getParameter("m_no"));
		List<Meal> list = mdao.mDetail(m_no);
		Meal m = mdao.getMeal(m_no);
		request.setAttribute("meal", m);
		request.setAttribute("MealDetail", list);
		
		//찜 보이는 기능
		if(user!=null) {
			WishDAO wdao = WishDAO.getInstance();
			int result=3;
			int w_no = wdao.selectWish(user.getU_no(),m_no,result);
			request.setAttribute("w_no", w_no);
		}
		
		
		
		//댓글 보이는 기능
		CoDAO cdao = CoDAO.getInstance(); 
		List<Comment> clist = cdao.selectCoM(m_no);
		int num = cdao.getNumM(m_no);
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
					if(cookie.getValue().contains("M"+request.getParameter("m_no"))) {
						cookie.setMaxAge(60*60*24);
						//System.out.println("번호도 있음 증가 X");
					}else {
						cookie.setValue(cookie.getValue()+"_"+"M"+request.getParameter("m_no"));
						response.addCookie(cookie);
						cookie.setMaxAge(60*60*24);
						mdao.updateM_hit(m_no);
						
						//쿠키에 페이지 번호가 없으면 추가해주고 카운트 늘리기.
					}
					
				}
			}
			if(visitor==0) {
				Cookie cookieM = new Cookie(u_id,"M"+request.getParameter("m_no"));
				mdao.updateM_hit(m_no);
				cookieM.setMaxAge(60*60*24);
				response.addCookie(cookieM);
				//중복되는 아이디가 없으면 조회수 증가해주기.
			}
		}

		
		RequestDispatcher dis = request.getRequestDispatcher("meal/m_detail.jsp");
		dis.forward(request, response);
	}

	
}
