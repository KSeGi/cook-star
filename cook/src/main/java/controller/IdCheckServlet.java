
package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.UserDAO;


@WebServlet("/IdCheck")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("EUC-KR");
		
		String u_id = request.getParameter("u_id");		//join.jsp에서 받아온 값
		
		PrintWriter out = response.getWriter();
		UserDAO uDAO = UserDAO.getInstance();
		
		int idCheck = uDAO.checkId(u_id);
		
		//성공여부 확인 : 개발자
		if(idCheck == 0) {
			System.out.println("이미 존재하는 아이디입니다.");
		}else if(idCheck == 1) {
			System.out.println("사용 가능한 아이디입니다.");
		}
		
		out.write(idCheck + "");
		out.close();
	}

}
