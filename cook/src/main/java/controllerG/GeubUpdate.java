package controllerG;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.GeubDAO;
import DTO.Geub;


@WebServlet("/GeubUpdate")
public class GeubUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int g_no = Integer.parseInt(request.getParameter("g_no"));
		GeubDAO gdao= GeubDAO.getInstance();
		Geub g = gdao.getGeub(g_no);
		request.setAttribute("geub", g);
		RequestDispatcher dis = request.getRequestDispatcher("geub/g_update.jsp");
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		int g_no =  Integer.parseInt(request.getParameter("g_no"));
		String g_list = multi.getParameter("g_list");
		String g_title =multi.getParameter("g_title");
		String g_img = multi.getFilesystemName("g_img");
		String gImg = multi.getParameter("gImg");
		String g_star = multi.getParameter("g_star");
		String g_text = multi.getParameter("g_text");
		
		Geub g = new Geub();
		g.setG_no(g_no);
		g.setG_list(g_list);
		g.setG_title(g_title);
		if(g_img==null&&gImg!=null) {
			g_img=gImg;
			g.setG_img(g_img);
		}else {
			g.setG_img("/files/"+g_img);
		}
		g.setG_star(g_star);
		g.setG_text(g_text);
		
		GeubDAO gdao= GeubDAO.getInstance();
		gdao.updateGeub(g);
		
		response.sendRedirect("GeubDetail?g_no="+g_no);	
	}

}
