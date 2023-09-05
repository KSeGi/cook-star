package controllerC;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.CoDAO;
import DTO.Comment;
import DTO.User;


@WebServlet("/CoInsert")
public class CoInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String c_text = request.getParameter("c_text");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		CoDAO cdao = CoDAO.getInstance();
		Comment c = new Comment();
		if(request.getParameter("h_no")!=null) {
			int h_no =  Integer.parseInt(request.getParameter("h_no"));

			c.setU_no(user.getU_no());
			c.setC_text(c_text);
			c.setH_no(h_no);
			cdao.insertCo(c);
			response.sendRedirect("HomeDetail?h_no="+h_no);
		}
		if(request.getParameter("g_no")!=null) {
			int g_no =  Integer.parseInt(request.getParameter("g_no"));
			c.setU_no(user.getU_no());
			c.setC_text(c_text);
			c.setG_no(g_no);
			cdao.insertCoG(c);	
			response.sendRedirect("GeubDetail?g_no="+g_no);
		
		}
		if(request.getParameter("m_no")!=null) {
			int m_no =  Integer.parseInt(request.getParameter("m_no"));
			c.setU_no(user.getU_no());
			c.setC_text(c_text);
			c.setM_no(m_no);
			cdao.insertCoM(c);	
			response.sendRedirect("MealDetail?m_no="+m_no);
		
		}
		if(request.getParameter("i_no")!=null) {
			int i_no =  Integer.parseInt(request.getParameter("i_no"));
			c.setU_no(user.getU_no());
			c.setC_text(c_text);
			c.setI_no(i_no);
			cdao.insertCoI(c);	
			response.sendRedirect("InfoDetail?i_no="+i_no);
		
		}

	}
}
