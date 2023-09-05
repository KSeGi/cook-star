package controllerM;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.MealDAO;
import DTO.Meal;


@WebServlet("/MealUpdate")
public class MealUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int m_no = Integer.parseInt(request.getParameter("m_no"));
		MealDAO mdao= MealDAO.getInstance();
		Meal m = mdao.getMeal(m_no);
		request.setAttribute("meal", m);
		RequestDispatcher dis = request.getRequestDispatcher("meal/m_update.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		int m_no =  Integer.parseInt(request.getParameter("m_no"));
		String m_title = multi.getParameter("m_title");
		String m_img = multi.getFilesystemName("m_img");
		String mImg = multi.getParameter("mImg");
		String m_star = multi.getParameter("m_star");
		String m_text = multi.getParameter("m_text");
		
		Meal m = new Meal();
		m.setM_no(m_no);
		m.setM_title(m_title);
		if(m_img==null&&mImg!=null) {
			m_img=mImg;
			m.setM_img(m_img);
		}else {
			m.setM_img("/files/"+m_img);
		}
		m.setM_star(m_star);
		m.setM_text(m_text);
		
		MealDAO mdao= MealDAO.getInstance();
		mdao.updateMeal(m);
		
		response.sendRedirect("MealDetail?m_no="+m_no);	
	}

}
