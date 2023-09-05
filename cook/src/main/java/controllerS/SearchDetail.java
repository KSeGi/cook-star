package controllerS;

import java.io.IOException;
import java.util.*;

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


@WebServlet("/SearchDetail")
public class SearchDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("EUC-KR");
		
		String searchH = request.getParameter("searchHome");
		String searchG = request.getParameter("searchGeub");
		String searchM = request.getParameter("searchMeal");
		String url="";
		if(searchH!=null) {
			HomeDAO hdao = HomeDAO.getInstance();
			List<Home> hlist = hdao.searchAll(searchH);
			request.setAttribute("SearchHome", hlist);
			url="search/searchHome.jsp";
		}
		if(searchG!=null) {
			GeubDAO gdao = GeubDAO.getInstance();
			List<Geub> glist = gdao.searchAll(searchG);
			request.setAttribute("SearchGeub", glist);
			url="search/searchGeub.jsp";
		}
		if(searchM!=null) {
			MealDAO mdao = MealDAO.getInstance();
			List<Meal> mlist = mdao.searchAll(searchM);
			request.setAttribute("SearchMeal", mlist);
			url="search/searchMeal.jsp";
		}
		RequestDispatcher dis = request.getRequestDispatcher(url);
		dis.forward(request, response);
	}

}
