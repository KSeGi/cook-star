package controllerM;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.MealDAO;
import DTO.Meal;
import DTO.User;


@WebServlet("/MealInsert")
public class MealInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis= request.getRequestDispatcher("meal/m_insert.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		String m_title = multi.getParameter("m_title");
		String m_img = multi.getFilesystemName("m_img");
		String m_star = multi.getParameter("m_star");
		String m_text = multi.getParameter("m_text");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		Meal mdto = new Meal();
		mdto.setU_no(user.getU_no());
		mdto.setM_title(m_title);
		mdto.setM_img("/files/"+m_img);
		mdto.setM_star(m_star);
		mdto.setM_text(m_text);
		
		MealDAO mdao= MealDAO.getInstance();
		mdao.insertMeal(mdto);
		
		int mno=mdao.mno(mdto);
		response.sendRedirect("MealDetail?m_no="+mno);	
	}

}
