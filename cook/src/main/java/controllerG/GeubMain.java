package controllerG;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.GeubDAO;
import DTO.Geub;

@WebServlet("/GeubMain")
public class GeubMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GeubDAO dao = GeubDAO.getInstance();
		int currentPage=Integer.parseInt(request.getParameter("currentPage"));
		int recordsPerPage=5;
		int g_list = Integer.parseInt(request.getParameter("g_list"));
		//0:전체보기
		//1:다인		//2:1인		//3:채식
		List<Geub> list = dao.selectAll(currentPage,recordsPerPage,g_list);

		int num = dao.getNumber(g_list);			//전체 게시물 수
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
		
		request.setAttribute("GeubMain", list);
		request.setAttribute("currentPage", currentPage);//현재 몇번 페이지를 보고 있는지
		request.setAttribute("nPage", nPage);				//총 페이지 개수
		request.setAttribute("startPage", startPage);		//시작 페이지
		request.setAttribute("endPage", endPage);			//끝 페이지
		request.setAttribute("pageBlock", pageBlock);		//페이지 개수
		request.setAttribute("g_list", g_list);
		
		RequestDispatcher dis = request.getRequestDispatcher("geub/g_main.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
