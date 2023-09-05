package controllerM;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MealDAO;


@WebServlet("/MealDelete")
public class MealDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int m_no = Integer.parseInt(request.getParameter("m_no"));
		MealDAO mdao= MealDAO.getInstance();
		mdao.deleteMeal(m_no);
		response.sendRedirect("MealMain?currentPage=1");
	}

	

}
