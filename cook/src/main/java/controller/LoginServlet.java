package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.UserDAO;
import DTO.User;


@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("user/login.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url ="user/login.jsp";
		request.setCharacterEncoding("utf-8");
		
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		UserDAO uDAO = UserDAO.getInstance();
		int result = uDAO.userCheck(u_id, u_pw);
		if(result == 1) {
			User u = uDAO.getUser(u_id);
			HttpSession session = request.getSession();
			
			//데이터가 입력된 DTO를 세션에 입력
			session.setAttribute("user", u);
			request.setAttribute("message", "로그인에 성공했습니다.");
			url = "index.jsp";
			
		}else if(result==0) { //비밀번호 틀림
			request.setAttribute("message", "비밀번호가 틀렸습니다.");
		}else if(result==-1) { //존재하지 않는 회원
			request.setAttribute("message", "존재하지 않는 회원입니다.");

		}
		RequestDispatcher dis = request.getRequestDispatcher(url);
		dis.forward(request, response);
		
		
	}

}
