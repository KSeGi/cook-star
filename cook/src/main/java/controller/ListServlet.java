package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import java.util.*;
import DTO.User;
import DAO.UserDAO;


@WebServlet("/List")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user==null || user.getU_grade()==1){
			response.sendRedirect("index.jsp");
		}else {
		
		UserDAO uDAO = UserDAO.getInstance();
		List<User> data = uDAO.getList();
		request.setAttribute("list", data);
		RequestDispatcher dis = request.getRequestDispatcher("user/list.jsp");
		dis.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
