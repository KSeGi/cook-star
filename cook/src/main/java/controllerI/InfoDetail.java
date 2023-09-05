package controllerI;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.CoDAO;
import DAO.InfoDAO;
import DTO.Comment;
import DTO.Info;
import DTO.User;


@WebServlet("/InfoDetail")
public class InfoDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
InfoDAO idao = InfoDAO.getInstance();
		
		//상세내역 보이는 기능
		int i_no = Integer.parseInt(request.getParameter("i_no"));
		List<Info> list = idao.iDetail(i_no);
		Info i = idao.getInfo(i_no);
		request.setAttribute("info", i);
		request.setAttribute("InfoDetail", list);
		
		//댓글 보이는 기능
		CoDAO cdao = CoDAO.getInstance(); 
		List<Comment> clist = cdao.selectCoI(i_no);
		int num = cdao.getNumI(i_no);
		request.setAttribute("Comment", clist);
		request.setAttribute("num", num);
		
		//조회수 높이는 기능
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		if(user!=null) {
			String u_id=user.getU_id();
			Cookie[] cookies = request.getCookies();
			int visitor=0;
			
			for(Cookie cookie : cookies) {
				//System.out.println(cookie.getName());
				if(cookie.getName().equals(u_id)){
					visitor=1;
					//System.out.println("등록id 있음");
					if(cookie.getValue().contains("I"+request.getParameter("i_no"))) {
						cookie.setMaxAge(60*60*24);
						//System.out.println("번호도 있음 증가 X");
					}else {
						cookie.setValue(cookie.getValue()+"_"+"I"+request.getParameter("i_no"));
						response.addCookie(cookie);
						cookie.setMaxAge(60*60*24);
						idao.updateI_hit(i_no);
						
						//쿠키에 페이지 번호가 없으면 추가해주고 카운트 늘리기.
					}
					
				}
			}
			if(visitor==0) {
				Cookie cookieI = new Cookie(u_id,"I"+request.getParameter("i_no"));
				idao.updateI_hit(i_no);
				cookieI.setMaxAge(60*60*24);
				response.addCookie(cookieI);
				//중복되는 아이디가 없으면 조회수 증가해주기.
			}
		}

		
		RequestDispatcher dis = request.getRequestDispatcher("info/i_detail.jsp");
		dis.forward(request, response);
	}
	

}
