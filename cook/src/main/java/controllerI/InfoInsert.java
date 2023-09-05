package controllerI;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.InfoDAO;
import DTO.Info;
import DTO.User;


@WebServlet("/InfoInsert")
public class InfoInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis= request.getRequestDispatcher("info/i_insert.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		String i_title = multi.getParameter("i_title");
		String i_img = multi.getFilesystemName("i_img");
		String i_text = multi.getParameter("i_text");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		Info idto = new Info();
		idto.setU_no(user.getU_no());
		idto.setI_title(i_title);
		idto.setI_img("/files/"+i_img);
		idto.setI_text(i_text);
		
		InfoDAO idao= InfoDAO.getInstance();
		idao.insertInfo(idto);
		
		int ino=idao.ino(idto);
		response.sendRedirect("InfoDetail?i_no="+ino);	
	}

}
