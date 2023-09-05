package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


import DAO.UserDAO;
import DTO.User;


@WebServlet("/Delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String u_no = request.getParameter("u_no");
		UserDAO uDAO = UserDAO.getInstance();
		uDAO.deleteUser(u_no);
	
		
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
