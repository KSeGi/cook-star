package controllerW;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.WishDAO;
import DTO.User;


@WebServlet("/WishInsert")
public class WishInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int result=0;
		WishDAO wdao = WishDAO.getInstance();
		if(request.getParameter("h_no")!=null) {
			int h_no =  Integer.parseInt(request.getParameter("h_no"));
			result=1;
			wdao.wishLike(user.getU_no(), h_no, result);
		}
		if(request.getParameter("g_no")!=null) {
			int g_no =  Integer.parseInt(request.getParameter("g_no"));
			result=2;
			wdao.wishLike(user.getU_no(), g_no, result);
		}
		if(request.getParameter("m_no")!=null) {
			int m_no =  Integer.parseInt(request.getParameter("m_no"));
			result=3;
			wdao.wishLike(user.getU_no(), m_no, result);
		}
		
	}

}
