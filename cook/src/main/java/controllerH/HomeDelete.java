package controllerH;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.HomeDAO;


@WebServlet("/HomeDelete")
public class HomeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int h_no = Integer.parseInt(request.getParameter("h_no"));
		HomeDAO hdao= HomeDAO.getInstance();
		hdao.deleteHome(h_no);
		response.sendRedirect("HomeMain?currentPage=1&h_list=0");
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	}

}
