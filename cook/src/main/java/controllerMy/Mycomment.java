package controllerMy;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.CoDAO;
import DTO.Comment;
import DTO.User;


@WebServlet("/Mycomment")
public class Mycomment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		CoDAO cdao = CoDAO.getInstance();
		List<Comment> clist = cdao.selectlist(user.getU_no());
		request.setAttribute("Comment", clist);

		
		RequestDispatcher dis = request.getRequestDispatcher("mypage/mycomment.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
