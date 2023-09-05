package controllerC;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.CoDAO;

@WebServlet("/CoUpdate")
public class CoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("EUC-KR");
		
		int c_no = Integer.parseInt(request.getParameter("c_no"));
		String c_text = request.getParameter("c_text");
		CoDAO cdao = CoDAO.getInstance();
		cdao.updateCo(c_text, c_no);
	}

}
