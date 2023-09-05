package controllerMy;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.GeubDAO;
import DAO.HomeDAO;
import DAO.MealDAO;
import DTO.Geub;
import DTO.Home;
import DTO.Meal;
import DTO.User;

@WebServlet("/Mywish")
public class Mywish extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		HomeDAO hdao = HomeDAO.getInstance();
		List<Home> hlist = hdao.selecwish(user.getU_no());
		request.setAttribute("HomeList", hlist);
		
		GeubDAO gdao = GeubDAO.getInstance();
		List<Geub> glist = gdao.selecwish(user.getU_no());
		request.setAttribute("GeubList", glist);
		
		MealDAO mdao = MealDAO.getInstance();
		List<Meal> mlist = mdao.selecwish(user.getU_no());
		request.setAttribute("MealList", mlist);
		
		RequestDispatcher dis = request.getRequestDispatcher("mypage/mywish.jsp");
		dis.forward(request, response);
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
