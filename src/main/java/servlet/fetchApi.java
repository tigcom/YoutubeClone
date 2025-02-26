package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
@MultipartConfig
@WebServlet({"/ajax/fetchUpload","/ajax/restapi"})
public class fetchApi extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getMethod();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String fmt = "{\"method\":\"%s\", \"servlet-path\":\"%s\", \"path-info\":\"%s\"}";
		String json = String.format(fmt, method, servletPath, pathInfo);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");
		resp.getWriter().print(json);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getMethod();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String fmt = "{\"method\":\"%s\", \"servlet-path\":\"%s\", \"path-info\":\"%s\"}";
		String json = String.format(fmt, method, servletPath, pathInfo);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");
		resp.getWriter().print(json);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getMethod();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String fmt = "{\"method\":\"%s\", \"servlet-path\":\"%s\", \"path-info\":\"%s\"}";
		String json = String.format(fmt, method, servletPath, pathInfo);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");
		resp.getWriter().print(json);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String method = req.getMethod();
		String servletPath = req.getServletPath();
		String pathInfo = req.getPathInfo();
		String fmt = "{\"method\":\"%s\", \"servlet-path\":\"%s\", \"path-info\":\"%s\"}";
		String json = String.format(fmt, method, servletPath, pathInfo);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");
		resp.getWriter().print(json);
	}
}
