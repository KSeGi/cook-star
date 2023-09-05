package controllerI;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.InfoDAO;


@WebServlet("/InfoDelete")
public class InfoDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_no = Integer.parseInt(request.getParameter("i_no"));
		InfoDAO idao= InfoDAO.getInstance();
		idao.deleteInfo(i_no);
		response.sendRedirect("InfoMain?currentPage=1");
	}


}
