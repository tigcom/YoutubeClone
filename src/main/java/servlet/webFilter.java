package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import DAO.CheckLogDAO;
import DAO.CheckLogDAOImpl;
import DAO.UserDAO;
import DAO.UserDAOImpl;
import DAO.VideoDAO;
import DAO.VideoDAOImpl;
import bean.Checklog;
import bean.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebFilter(urlPatterns = "/*")
public class webFilter implements Filter{	
	CheckLogDAO ckDAO = new CheckLogDAOImpl();
	UserDAO uDAO = new UserDAOImpl();
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		HttpSession ses = req.getSession();
		ServletContext ctx = req.getServletContext();
		String id = req.getParameter("id");
		VideoDAO vdDAO= new VideoDAOImpl();
		User us = null;
		if (id != null) {
            ArrayList<String> idlog = (ArrayList<String>) ctx.getAttribute("idlog");
            if (idlog != null && idlog.contains(id)) {
            	req.setAttribute("messss2", "Tài khoản này đã được đăng nhập ở nơi khác, hãy đăng xuất rồi thử lại sau !");
                req.getRequestDispatcher("/view/errorLog.jsp").forward(req, resp);
                return;
            }
        }
		String idLog = (String) ses.getAttribute("idLogin");
		if (idLog != null) {
		    us = uDAO.findById(idLog);
		}
		Checklog cklog = new Checklog();
		cklog.setDevice(req.getHeader("User-Agent"));
		cklog.setAccecsTime( new Date());
		cklog.setURI(req.getServletPath());
		if (us != null) {
		    cklog.setUserName(us.getFullname());
		    cklog.setUser(us);
		} else {
		    cklog.setUserName("Khách");
		}
		ckDAO.insert(cklog);
		
		if(req.getServletPath().contains("userview") ){
			if (us == null) {
				req.setAttribute("wrong", true);
				req.setAttribute("site","/view/listOfVideo.jsp");
				if (req.getServletPath().contains("userview"));
				req.setAttribute("messss", "Bạn chưa đăng nhập");
				req.setAttribute("listVD", vdDAO.findAll());
				req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
				return;
			}
		}else if(req.getServletPath().contains("admin") ){
			if (us == null) {
				req.setAttribute("wrong", true);
				req.setAttribute("site","/view/listOfVideo.jsp");
				if (req.getServletPath().contains("userview"));
				req.setAttribute("messss", "Bạn chưa đăng nhập");
				req.setAttribute("listVD", vdDAO.findAll());
				req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
				return;
			}else if (!us.getAdmin()) {
				System.out.println("filter admin");
				req.setAttribute("messss2", "Tính năng này chỉ hỗ trợ ADMIN bạn không đủ quyền truy cập !!!");
                req.getRequestDispatcher("/view/errorLog.jsp").forward(req, resp);
                return;
			}
		}
		
		chain.doFilter(request, response);	
	}

}
