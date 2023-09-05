package controllerG;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.GeubDAO;
import DTO.Geub;
import DTO.User;


@WebServlet("/GeubInsert")
public class GeubInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis= request.getRequestDispatcher("geub/g_insert.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		String g_list = multi.getParameter("g_list");
		String g_title =multi.getParameter("g_title");
		String g_img = multi.getFilesystemName("g_img");
		String g_star = multi.getParameter("g_star");
		String g_text = multi.getParameter("g_text");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		Geub gdto = new Geub();
		gdto.setU_no(user.getU_no());
		gdto.setG_list(g_list);
		gdto.setG_title(g_title);
		gdto.setG_img("/files/"+g_img);
		gdto.setG_star(g_star);
		gdto.setG_text(g_text);
		
		GeubDAO gdao= GeubDAO.getInstance();
		gdao.insertGeub(gdto);
		
		int gno=gdao.gno(gdto);
		response.sendRedirect("GeubDetail?g_no="+gno);	
	}

}
