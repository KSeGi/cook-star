package controllerI;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.InfoDAO;
import DTO.Info;


@WebServlet("/InfoUpdate")
public class InfoUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_no = Integer.parseInt(request.getParameter("i_no"));
		InfoDAO mdao= InfoDAO.getInstance();
		Info i = mdao.getInfo(i_no);
		request.setAttribute("info", i);
		RequestDispatcher dis = request.getRequestDispatcher("info/i_update.jsp");
		dis.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		int i_no =  Integer.parseInt(request.getParameter("i_no"));
		String i_title = multi.getParameter("i_title");
		String i_img = multi.getFilesystemName("i_img");
		String iImg = multi.getParameter("iImg");
		String i_text = multi.getParameter("i_text");
		
		Info i = new Info();
		i.setI_no(i_no);
		i.setI_title(i_title);
		if(i_img==null&&iImg!=null) {
			i_img=iImg;
			i.setI_img(i_img);
		}else {
			i.setI_img("/files/"+i_img);
		}
		i.setI_text(i_text);
		
		InfoDAO mdao= InfoDAO.getInstance();
		mdao.updateInfo(i);
		
		response.sendRedirect("InfoDetail?i_no="+i_no);	
	}

}
