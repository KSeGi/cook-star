package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.UserDAO;
import DTO.User;


@WebServlet("/Update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_id =  request.getParameter("u_id");
		UserDAO uDAO = UserDAO.getInstance();
		User u = uDAO.getUser(u_id);
		System.out.println(u_id);
		request.setAttribute("user", u);
		
		RequestDispatcher dis = request.getRequestDispatcher("user/update.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int u_no =  Integer.parseInt(request.getParameter("u_no"));
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		String u_name = request.getParameter("u_name");
		String u_nik = request.getParameter("u_nik");
		String u_tel = request.getParameter("u_tel");
		int u_grade = Integer.parseInt(request.getParameter("u_grade"));
		String u_joindate = request.getParameter("u_joindate");
		
		User u = new User();
		u.setU_no(u_no);
		u.setU_id(u_id);
		u.setU_pw(u_pw);
		u.setU_name(u_name);
		u.setU_nik(u_nik);
		u.setU_tel(u_tel);
		u.setU_grade(u_grade);
		u.setU_joindate(u_joindate);
		
		UserDAO uDAO = UserDAO.getInstance();
		uDAO.updateUser(u);
		
		
		String url = "index.jsp";
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user.getU_grade()==2) {
			url ="List";
		}else {
			session.invalidate();
		}

		response.sendRedirect(url);
	
	}

}
