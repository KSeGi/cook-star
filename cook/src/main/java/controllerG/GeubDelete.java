package controllerG;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.GeubDAO;


@WebServlet("/GeubDelete")
public class GeubDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int g_no = Integer.parseInt(request.getParameter("g_no"));
		GeubDAO gdao= GeubDAO.getInstance();
		gdao.deleteGeub(g_no);
		response.sendRedirect("GeubMain?currentPage=1&g_list=0");
	}

	

}
