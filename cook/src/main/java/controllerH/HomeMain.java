package controllerH;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import DTO.Home;
import DAO.HomeDAO;

@WebServlet("/HomeMain")
public class HomeMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HomeDAO dao = HomeDAO.getInstance();
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		int recordsPerPage=5;
		int h_list = Integer.parseInt(request.getParameter("h_list"));
		//0:전체보기
		//1:다인		//2:1인		//3:채식
		List<Home> list = dao.selectAll(currentPage,recordsPerPage,h_list);

		int num = dao.getNumber(h_list);			//전체 게시물 수
		int nPage=num/recordsPerPage;	 	//전체 페이지 수
		if(num%recordsPerPage>0) {
			nPage++;
		}
		
		int pageBlock = 10;
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		int endPage = startPage + pageBlock -1;
		if(endPage > nPage) {
			endPage = nPage;
		}
		
		request.setAttribute("HomeMain", list);
		request.setAttribute("currentPage", currentPage);//현재 몇번 페이지를 보고 있는지
		request.setAttribute("nPage", nPage);				//총 페이지 개수
		request.setAttribute("startPage", startPage);		//시작 페이지
		request.setAttribute("endPage", endPage);			//끝 페이지
		request.setAttribute("pageBlock", pageBlock);		//페이지 개수
		request.setAttribute("h_list", h_list);
		
		RequestDispatcher dis = request.getRequestDispatcher("home/h_board.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
