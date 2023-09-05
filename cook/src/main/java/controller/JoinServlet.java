package controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import DAO.UserDAO;
import DTO.User;


@WebServlet("/Join")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("user/join.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String u_id = request.getParameter("u_id");
		String u_pw = request.getParameter("u_pw");
		String u_name = request.getParameter("u_name");
		String u_nik = request.getParameter("u_nik");
		String first = request.getParameter("first");
		String second = request.getParameter("second");
		String third = request.getParameter("third");
		
		User u = new User();
		u.setU_id(u_id);
		u.setU_pw(u_pw);
		u.setU_name(u_name);
		u.setU_nik(u_nik);
		u.setFirst(first);
		u.setSecond(second);
		u.setThird(third);
		
		UserDAO uDAO = UserDAO.getInstance();
		int result = uDAO.insertUser(u);
		//-1이면 접속 중 오류발생, 1이면 insert가 정상동작
		
		HttpSession session = request.getSession();
		if(result==1) {
			session.setAttribute("u_id", u.getU_id());
			request.setAttribute("message", "회원가입에 성공하였습니다.");
			System.out.println("회원가입 성공");
		}else {
			request.setAttribute("message", "회원가입 중 오류가 발생하였습니다.");
			System.out.println("회원가입 오류");
		}
		RequestDispatcher dis = request.getRequestDispatcher("user/login.jsp");
		dis.forward(request, response);
		}

}
