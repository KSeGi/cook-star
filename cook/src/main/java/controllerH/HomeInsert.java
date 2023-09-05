package controllerH;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DTO.Home;
import DTO.User;
import DAO.HomeDAO;

@WebServlet("/HomeInsert")
public class HomeInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis= request.getRequestDispatcher("home/h_insert.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		String h_list = multi.getParameter("h_list");
		String h_title =multi.getParameter("h_title");
		String h_img = multi.getFilesystemName("h_img");
		String h_star = multi.getParameter("h_star");
		String h_text = multi.getParameter("h_text");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		Home hdto = new Home();
		hdto.setU_no(user.getU_no());
		hdto.setH_list(h_list);
		hdto.setH_title(h_title);
		hdto.setH_img("/files/"+h_img);
		hdto.setH_star(h_star);
		hdto.setH_text(h_text);
		
		HomeDAO hdao= HomeDAO.getInstance();
		hdao.insertHome(hdto);
		
		int hno=hdao.hno(hdto);
		response.sendRedirect("HomeDetail?h_no="+hno);	
	}

}
