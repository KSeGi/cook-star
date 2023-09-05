package controllerS;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.GeubDAO;
import DAO.HomeDAO;
import DAO.MealDAO;
import DTO.Geub;
import DTO.Home;
import DTO.Meal;


@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("EUC-KR");
		
		String search = request.getParameter("search");
		
		HomeDAO hdao = HomeDAO.getInstance();
		List<Home> hlist = hdao.searchAll(search);
		request.setAttribute("SearchHome", hlist);
		
		GeubDAO gdao = GeubDAO.getInstance();
		List<Geub> glist = gdao.searchAll(search);
		request.setAttribute("SearchGeub", glist);
		
		MealDAO mdao = MealDAO.getInstance();
		List<Meal> mlist = mdao.searchAll(search);
		request.setAttribute("SearchMeal", mlist);
		
		RequestDispatcher dis = request.getRequestDispatcher("search/searchMain.jsp");
		dis.forward(request, response);
		
		
		
		
	}

}
