package controllerH;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import DAO.HomeDAO;
import DTO.Home;


@WebServlet("/HomeUpdate")
public class HomeUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int h_no = Integer.parseInt(request.getParameter("h_no"));
		HomeDAO hdao= HomeDAO.getInstance();
		Home h = hdao.getHome(h_no);
		request.setAttribute("home", h);
		RequestDispatcher dis = request.getRequestDispatcher("home/h_update.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ServletContext context = getServletContext();
		String path= context.getRealPath("/files");
		String encType="utf-8";
		int sizeLimit = 20*1024*1024;		//20mb제한
		
		MultipartRequest multi = new MultipartRequest(request, path, sizeLimit, encType, new DefaultFileRenamePolicy());
		int h_no =  Integer.parseInt(request.getParameter("h_no"));
		String h_list = multi.getParameter("h_list");
		String h_title =multi.getParameter("h_title");
		String h_img = multi.getFilesystemName("h_img");
		String hImg = multi.getParameter("hImg");
		String h_star = multi.getParameter("h_star");
		String h_text = multi.getParameter("h_text");
		
		Home h = new Home();
		h.setH_no(h_no);
		h.setH_list(h_list);
		h.setH_title(h_title);
		if(h_img==null&&hImg!=null) {
			h_img=hImg;
			h.setH_img(h_img);
		}else {
			h.setH_img("/files/"+h_img);
		}
		h.setH_star(h_star);
		h.setH_text(h_text);
		
		HomeDAO hdao= HomeDAO.getInstance();
		hdao.updateHome(h);
		
		response.sendRedirect("HomeDetail?h_no="+h_no);	
	}

}
