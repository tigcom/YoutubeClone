package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import DAO.CheckLogDAO;
import DAO.CheckLogDAOImpl;
import DAO.CommentDAO;
import DAO.CommentDAOImpl;
import DAO.FavoriteDAO;
import DAO.FavoriteDAOImpl;
import DAO.HistoryDAO;
import DAO.HistoryDAOImpl;
import DAO.SubscribeDAO;
import DAO.SubscribeDAOImpl;
import DAO.UserDAO;
import DAO.UserDAOImpl;
import DAO.VideoDAO;
import DAO.VideoDAOImpl;
import bean.Comment;
import bean.Favorite;
import bean.History;
import bean.Subscribe;
import bean.User;
import bean.Video;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import util.XMail; 
@MultipartConfig
@WebServlet({"/index/video","/index/video/login",
			"/index/video/search","/index/video/trend","/index/video/lab3",
			"/index/video/searchUser","/index/video/logout", "/index/video/chitiet/*", 
			"/index/video/newVideo", "/index/video/oldVideo","/index/video/user/*",
			
			"/userview/editUser/*","/index/userview/visted","/index/userview/liked",
			"/index/userview/chitiet/unSub","/index/userview/chitiet/insertSub",
			"/index/userview/chitiet/cmt/update","/index/userview/chitiet/cmt/edit",
			"/index/userview/chitiet/cmt/delete/*","/index/userview/chitiet/comment",
			"/index/userview/chitiet/disLike/*","/index/userview/chitiet/like/*",
			"/index/video/userview/protifle/*",
			
			"/admin/checklogView","/admin/tableUser","/admin/newUser","/admin/noti","/admin/checklog",
			"/admin/updateUser","/admin/delUser/*","/admin/editUser/*","/admin/inserUser",
			"/admin/createVideo","/admin/insertVideo","/admin/editVideo","/admin/deleteVideo",
			"/admin/hideVideo","/admin/showVideo","/admin/updateVideo"})
public class video extends HttpServlet {
	VideoDAO vdDAO= new VideoDAOImpl();
	UserDAO uDAO = new UserDAOImpl();
	FavoriteDAO fDAO= new FavoriteDAOImpl();
	HistoryDAO hDAO= new HistoryDAOImpl();
	CommentDAO cDAO = new CommentDAOImpl();
	CheckLogDAO cklDAO = new CheckLogDAOImpl();
	SubscribeDAO sDAO = new SubscribeDAOImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession ses = req.getSession();
		ServletContext ctx = getServletContext();
		this.liveViews(req,ctx,ses);
		this.checkNoti(req, ctx, ses);
		String id = (String) ses.getAttribute("idLogin");
		User u = new User();
		if (id != null) {
			 u = uDAO.findById(id);
			 req.setAttribute("uLog", u);
		}
		Video v = new Video();
		if (req.getServletPath().endsWith("video")) {
			req.setAttribute("site","/view/listOfVideo.jsp");
			req.setAttribute("listVD", vdDAO.findAll());
		}else if (req.getServletPath().endsWith("chitiet")) {
			 String lastV = (String) ses.getAttribute("lastVid");
			 Integer countVnew = (Integer) ctx.getAttribute(req.getPathInfo().substring(1));
		 	 if (lastV == null) {
		 		if (countVnew == null || countVnew == 0) { 
		 			System.out.println("Chưa có view, chưa có video cũ");
		        	countVnew = 1; 
		        	ctx.setAttribute(req.getPathInfo().substring(1), countVnew);
		        }else {
		        	System.out.println("Đã có view, chưa có video cũ");
		        	countVnew ++; 
		        	ctx.setAttribute(req.getPathInfo().substring(1), countVnew);
		        }
		 	 }else if (lastV != null) { 
		 		Integer countVold = (Integer) ctx.getAttribute(lastV); 
		 		if  (!lastV.equals(req.getPathInfo().substring(1))) {
		 			int oldcount =countVold-1;
		 			System.out.println("rời trang :"+oldcount);
	        		ctx.setAttribute(lastV, oldcount);
	        		if (countVnew == null || countVnew == 0) { 
			        	System.out.println("2");
			        	countVnew = 1; 
			        }else {
			        	countVnew++;
			        }
		        	ctx.setAttribute(req.getPathInfo().substring(1), countVnew);
		 		}
		 	}	
		 	if (u.getFullname() != null) {
		 		History hh = new History();
		 		hh.setDateVisti(new Date());
				hh.setUser(u);
				hh.setVideo(vdDAO.findById(req.getPathInfo().substring(1)));
				if (!hDAO.findCheck(u, vdDAO.findById(req.getPathInfo().substring(1)))) {
					hDAO.insert(hh);
				}else {
					hDAO.deleteBy2(u, vdDAO.findById(req.getPathInfo().substring(1)));
					hDAO.insert(hh);
				}
				u.getSubscribList().forEach(temp -> {
					System.out.println(temp.getUserAuth().getId());
					if (temp.getUserAuth().getId().equals(vdDAO.findById(req.getPathInfo().substring(1)).getAuthor().getId())) {
						req.setAttribute("subscribed", true);
						
					}
				});
		 	}
		        ses.setAttribute("lastVid", req.getPathInfo().substring(1));
		        ctx.setAttribute("viewCount", countVnew);
			this.chiTiet(req,req.getPathInfo().substring(1),u);
		}else if (req.getServletPath().endsWith("like")) {
			v = vdDAO.findById(req.getPathInfo().substring(1));
			Favorite f = new Favorite();
			f.setLikeDate(new Date());
			f.setUser(u);
			f.setVideo(v);
			fDAO.insert(f);
			req.setAttribute("listOfF", vdDAO.findById(req.getPathInfo().substring(1)).getFavorites());
			req.setAttribute("chitiet", true);
			req.setAttribute("ListOut", vdDAO.findOut(req.getPathInfo().substring(1)));
			req.setAttribute("tempVd",v); 
			req.setAttribute("Liked", true);
			req.setAttribute("site","/view/chitTiet.jsp");
		}else if (req.getServletPath().endsWith("disLike")) {
			v = vdDAO.findById(req.getPathInfo().substring(1));
			fDAO.deleteBy2(u, v);
			req.setAttribute("listOfF", vdDAO.findById(req.getPathInfo().substring(1)).getFavorites());
			req.setAttribute("chitiet", true);
			req.setAttribute("ListOut", vdDAO.findOut(req.getPathInfo().substring(1)));
			req.setAttribute("tempVd",v);
			req.setAttribute("Liked", false);
			req.setAttribute("site","/view/chitTiet.jsp");
		}else if (req.getServletPath().endsWith("logout")) {
			String idtemp = (String) ses.getAttribute("idLogin");
			User userlog = uDAO.findById(idtemp);
			ArrayList<String> idlog =  new ArrayList<>();
			if ( ctx.getAttribute("idlog")!= null) {
				idlog = (ArrayList<String>) ctx.getAttribute("idlog");
				for (int i = 0 ; i < idlog.size(); i++) {
					if (idlog.get(i).equals(userlog.getEmail()))
						idlog.remove(i);
				}
			} 
			ses.removeAttribute("idLogin");
			ctx.setAttribute("idlog",idlog);
			req.setAttribute("site","/view/listOfVideo.jsp");
			req.setAttribute("listVD", vdDAO.findAll());
		}else if (req.getServletPath().endsWith("trend")) {
			req.setAttribute("site","/view/trending.jsp");
			req.setAttribute("ListSort", vdDAO.ListSort());
		}else if (req.getServletPath().endsWith("liked")) {
			req.setAttribute("site","/view/liked.jsp");
			req.setAttribute("ListLiked", u.getFavorites());
		}else if (req.getServletPath().endsWith("visted")) {
			req.setAttribute("site","/view/visted.jsp");
			req.setAttribute("visteds", u.getHistory());
		}else if (req.getServletPath().endsWith("lab3")) {
			req.setAttribute("site","/view/Lab3.jsp");
			req.setAttribute("listVD", vdDAO.findAll());
		}else if (req.getServletPath().endsWith("tableUser")) {
			req.setAttribute("site","/view/tableUser.jsp");
			req.setAttribute("listUser", uDAO.findAll());
		}else if (req.getServletPath().endsWith("newVideo")) {
			req.setAttribute("site","/view/newVideo.jsp");
			req.setAttribute("checkNO",true);
			req.setAttribute("listVD", vdDAO.ListSortNew());
		}else if (req.getServletPath().endsWith("oldVideo")) {
			
			req.setAttribute("checkNO",false);
			req.setAttribute("listVD", vdDAO.ListSortOld());
			req.setAttribute("site","/view/newVideo.jsp");
		}else if (req.getServletPath().endsWith("delete")) {
			cDAO.delete(Integer.parseInt(req.getParameter("cmtId")));
			 this.chiTiet(req,req.getParameter("videoId"), u);
		}else if (req.getServletPath().endsWith("edit")) {
			req.setAttribute("edit",true);
			 this.chiTiet(req,req.getParameter("videoId"), u);
		}else if (req.getServletPath().endsWith("protifle")) {
			String idu = req.getPathInfo().substring(1);
			req.setAttribute("chanel", uDAO.findById(idu));
			req.setAttribute("site","/view/chiTietUser.jsp");
		}
		else if (req.getServletPath().endsWith("user")) {
			String idu = req.getPathInfo().substring(1);
			req.setAttribute("chanel", uDAO.findById(idu));
			req.setAttribute("site","/view/chiTietUser.jsp");
		}
		else if (req.getServletPath().endsWith("checklog")) {
			req.setAttribute("checklogs", cklDAO.findAll());
			req.setAttribute("site","/view/checkLog.jsp");
		}
		else if (req.getServletPath().endsWith("newUser")) {
			User empty = new User();
			String idu = null;
			do {
				idu ="user" + String.format("%09d", (int)(Math.random() * 1000000000));
				System.out.println(idu);
			}while (uDAO.findById(idu) != null);
			empty.setId(idu);
			req.setAttribute("emptyUser", empty);
			req.setAttribute("site","/view/editUser.jsp");
		}else if (req.getServletPath().endsWith("editUser")) {
			req.setAttribute("editU",true);
			req.setAttribute("emptyUser", uDAO.findById(req.getPathInfo().substring(1)));
			req.setAttribute("site","/view/editUser.jsp");
		}else if (req.getServletPath().endsWith("delUser")) {
			
			uDAO.delete(req.getPathInfo().substring(1));
			
			req.setAttribute("site","/view/tableUser.jsp");
			
			req.setAttribute("listUser", uDAO.findAll());
			
		}else if (req.getServletPath().endsWith("createVideo")) {
			Video emptyV = new Video();
			String idv = null;
			do {
				idv ="vid" + String.format("%09d", (int)(Math.random() * 1000000000));
			}while (vdDAO.findById(idv) != null);
			emptyV.setId(idv);
			req.setAttribute("creVid", emptyV);
			req.setAttribute("editV", false);
			req.setAttribute("site","/view/createVid.jsp");
		}else if (req.getServletPath().endsWith("deleteVideo")) {
			req.setAttribute("checkDel",vdDAO.delete(req.getParameter("videoId")));
			req.setAttribute("chanel", uDAO.findById(u.getId()));
			req.setAttribute("site","/view/chiTietUser.jsp");
		}else if (req.getServletPath().endsWith("hideVideo")) {
			Video vdtem = vdDAO.findById(req.getParameter("videoId"));
			vdtem.setActive(false);
			vdDAO.update(vdtem);
			req.setAttribute("chanel", uDAO.findById(u.getId()));
			req.setAttribute("site","/view/chiTietUser.jsp");
		}else if (req.getServletPath().endsWith("showVideo")) {
			Video vdtem = vdDAO.findById(req.getParameter("videoId"));
			vdtem.setActive(true);
			vdDAO.update(vdtem);
			req.setAttribute("chanel", uDAO.findById(u.getId()));
			req.setAttribute("site","/view/chiTietUser.jsp");
		}else if (req.getServletPath().endsWith("editVideo")) {
			Video emptyV = vdDAO.findById(req.getParameter("videoId"));
			req.setAttribute("creVid", emptyV);
			req.setAttribute("editV", true);
			req.setAttribute("site","/view/createVid.jsp");
		}
		
		req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
	}
	private void liveViews(HttpServletRequest req, ServletContext ctx,HttpSession ses) {
		if(!req.getServletPath().contains("chitiet")) {
			String lastVid = (String) ses.getAttribute("lastVid");
			int coutV = 0;
			if(lastVid != null) {
				coutV = (int) ctx.getAttribute(lastVid);
				System.out.println("bị trừuuuuuuuuuuuu");
				coutV-- ;
				ses.removeAttribute("lastVid");
				ctx.setAttribute(lastVid, coutV);
				ctx.setAttribute("viewCount", coutV);
			}
		}
		
	}
	private void checkNoti(HttpServletRequest req, ServletContext ctx, HttpSession ses) {
	    String tbNew = (String) ctx.getAttribute("thongBaoNew");
	    String tbOld = (String) ctx.getAttribute("thongBaoOld");
	    String noidung = null;
	    if (tbNew != null) {
	        if (tbNew.equals(tbOld)) {
	            System.out.println("1");
	            boolean notied = false;
	    	    if (ses.getAttribute("notied") != null) {
	    	        notied = (boolean) ses.getAttribute("notied");
	    	    }
	    	    if (!notied) {
    		        System.out.println("3");
    		        req.setAttribute("checkNoti", true);  
    		        ctx.setAttribute("thongBaoOld", tbNew); 
    		        ses.setAttribute("notied", true); 
    		    } else {
    		        System.out.println("4");
    		        req.setAttribute("checkNoti", false);  
    		    }
	        } 
	        else if (!tbNew.equals(tbOld)) {
	            System.out.println("2");
	            req.setAttribute("checkNoti", true);  
		        ctx.setAttribute("thongBaoOld", tbNew); 
		        ses.setAttribute("notied", true); 
	        }
	    }else {
	    	ses.setAttribute("notied", false); 
	    }
	    
	    
	}

	private void chiTiet(HttpServletRequest req, String id, User u) {
		List<Comment> listC =  cDAO.findByVideoId(id);
		Video vd = vdDAO.findById(id);
		if (u.getFullname() != null) {
			for(Favorite f :u.getFavorites()) {
				if (f.getVideo().getId().equals(vd.getId())) {
					req.setAttribute("Liked", true);
				}
			}
			for (int i=0; i < listC.size();i++) {
				if (listC.get(i).getUser().getId().equals(u.getId())) {
					req.setAttribute("cmtEd", true);
					Comment da = listC.get(i);
					listC.remove(listC.get(i));
					listC.add(0, da);
					break;
				}
			}
		}
		req.setAttribute("ListCmt",listC);
		req.setAttribute("listOfF", vdDAO.findById(id).getFavorites());
		req.setAttribute("chitiet", true);
		
		req.setAttribute("ListOut", vdDAO.findOut(id));
		req.setAttribute("tempVd",vd);
		req.setAttribute("site","/view/chitTiet.jsp");
		vd.setViews(vd.getViews() +1);
		vdDAO.update(vd);
		
	}
	public void sendMail(Video v,User auth) {
		List<String> list = new ArrayList<>();
		auth.getSubscribers().forEach(sub ->{
			list.add(sub.getUserDk().getEmail());
			System.out.println(sub.getUserDk().getEmail());
		});
		String conten = v.getTitle() +"\n"+ v.getDescription()+ "\n Xem chi tiết tại đường dẫn sau: http://localhost:8088/PS42856_ASM_Lab/index/video/chitiet/"+v.getId(); 
		XMail.sendSimpleText("Video mới YoutubeFake",conten ,null, null, list);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession ses = req.getSession();
		ServletContext ctx = getServletContext();
		this.liveViews(req,ctx,ses);
		this.checkNoti(req, ctx, ses);
		String id = (String) ses.getAttribute("idLogin");
		User u = new User();
		if (id != null) {
			 u = uDAO.findById(id);
			 req.setAttribute("uLog", u);
		}
		if (req.getServletPath().endsWith("login")) {
			
			String mail = req.getParameter("id");
			String pass = req.getParameter("password");
			u = uDAO.findByLog(mail);
			if (u == null) {
				req.setAttribute("feedBackMail", "Sai email");
				req.setAttribute("pass", pass);
				req.setAttribute("wrong", true);
			}else {
				if (u.getPassword().equals(pass)) {
					 ses.setAttribute("idLogin", u.getId());
					 req.setAttribute("uLog", u);
					 System.out.println("loggin succes");
				}else {
					req.setAttribute("feedBackPass", "Sai mật khẩu");
					req.setAttribute("mail", mail);
					req.setAttribute("wrong", true);
				}
			}
			ArrayList<String> idlog =  new ArrayList<>();
			if ( ctx.getAttribute("idlog")!= null) {
				idlog = (ArrayList<String>) ctx.getAttribute("idlog");
			}
				idlog.add(u != null ? u.getEmail(): null);
		
			ctx.setAttribute("idlog",idlog);
			req.setAttribute("site","/view/listOfVideo.jsp");
			req.setAttribute("listVD", vdDAO.findAll());
		}else if (req.getServletPath().endsWith("search")) {
			String key = req.getParameter("search");
			System.out.println(key);
			req.setAttribute("keySearch", key);
			if ( vdDAO.findSearch(key).size()>0) {
				req.setAttribute("listVD", vdDAO.findSearch(key));
				req.setAttribute("size",vdDAO.findSearch(key).size());
			}else {
				req.setAttribute("listVD", vdDAO.findAll());
				req.setAttribute("size","Không tìm thấy");
			}
			
			req.setAttribute("site","/view/search.jsp");
		}else if (req.getServletPath().endsWith("searchUser")) {
			String key = req.getParameter("searchU");
			req.setAttribute("site","/view/tableUser.jsp");
			if (uDAO.seatchUsers(key).size()>0) {
				req.setAttribute("key","Từ khóa tìm kiếm: "+key);
				req.setAttribute("listUser", uDAO.seatchUsers(key));
			}else {
				req.setAttribute("key","Không tìm thấy người dùng");
				req.setAttribute("listUser", uDAO.findAll());
			}
		}
		else if (req.getServletPath().endsWith("comment")) {
			String cmt = req.getParameter("cmt");
			String idV = req.getParameter("idVideo");
			String idU = (String) ses.getAttribute("idLogin");
			cDAO.insert(new Comment(0, cmt,new Date(),vdDAO.findById(idV),uDAO.findById(idU)));
			this.chiTiet(req, idV, uDAO.findById(idU));
		}else if (req.getServletPath().endsWith("update")) {
			String idU = (String) ses.getAttribute("idLogin");
			String idV = req.getParameter("videoId");
			int idC =Integer.parseInt(req.getParameter("cmtId"));
			Comment cmt = cDAO.findById(idC);
			cmt.setContent(req.getParameter("cmt"));
			cDAO.update(cmt);
			this.chiTiet(req, idV, uDAO.findById(idU));
		}else if (req.getServletPath().endsWith("noti")) {
			String noti = req.getParameter("noti");
			ctx.setAttribute("thongBaoNew", noti);
			req.setAttribute("site","/view/listOfVideo.jsp");
			req.setAttribute("listVD", vdDAO.findAll());
		}else if (req.getServletPath().endsWith("checklogView")) {
			String start = (String)req.getParameter("startDate");
			String end = (String)req.getParameter("endDate");
			System.out.println(start+end);
			req.setAttribute("site","/view/checkLog.jsp");
			req.setAttribute("checklogs",cklDAO.findByDate(start,end));
		}else if (req.getServletPath().endsWith("insertSub")) {
			String userDk =  req.getParameter("userDk");
			String userAuth = req.getParameter("userAuth");
			String idVd =  req.getParameter("videoId");
			if (userDk != null) {
				Subscribe sb = new Subscribe();
				sb.setUserAuth(uDAO.findById(userAuth));
				sb.setUserDk(uDAO.findById(userDk));
				sDAO.insert(sb);
			}
			req.setAttribute("subscribed", true);
			this.chiTiet(req, idVd, uDAO.findById(userAuth));
		}
		else if (req.getServletPath().endsWith("unSub")) {
			String userDk =  req.getParameter("userDk");
			String userAuth = req.getParameter("userAuth");
			String idVd =  req.getParameter("videoId");
			sDAO.deleteBy2(uDAO.findById(userAuth), uDAO.findById(userDk));
			req.setAttribute("subscribed", false);
			this.chiTiet(req, idVd, uDAO.findById(userAuth));
		}
		else if (req.getServletPath().endsWith("inserUser")) {
			User nUser = new User();
			Part part = req.getPart("image");
			try {
				BeanUtils.populate(nUser, req.getParameterMap());
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(part.getSubmittedFileName().isEmpty()){
				nUser.setImage(req.getParameter("imgCu"));
			}else {
				part.write(req.getServletContext().getRealPath("/imgs")+"/"+ part.getSubmittedFileName());
				System.out.println(req.getServletContext().getRealPath("/imgs")+"/"+ part.getSubmittedFileName());
				nUser.setImage(part.getSubmittedFileName());
			}
			try {
				uDAO.insert(nUser);
				System.out.println(nUser.toString());
				 ObjectMapper objectMapper = new ObjectMapper();
				    String jsonResponse = objectMapper.writeValueAsString(uDAO.findById(nUser.getId()));
				    resp.setContentType("application/json");
				    resp.getWriter().write(jsonResponse);
				    return;
			} catch (ConstraintViolationException e) {
			    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
			    resp.setContentType("application/json");
			    resp.getWriter().write("{\"error\": \"User already exists with the provided ID. Please choose a different ID.\"}");
			}
		}
		else if (req.getServletPath().endsWith("updateUser")) {
			User nUser = new User();
			Part part = req.getPart("image");
			try {
				BeanUtils.populate(nUser, req.getParameterMap());
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(part.getSubmittedFileName().isEmpty()){
				nUser.setImage(req.getParameter("imgCu"));
			}else {
				part.write(req.getServletContext().getRealPath("/imgs")+"/"+ part.getSubmittedFileName());
				System.out.println(req.getServletContext().getRealPath("/imgs")+"/"+ part.getSubmittedFileName());
				nUser.setImage(part.getSubmittedFileName());
			}
			uDAO.update(nUser);
			ObjectMapper objectMapper = new ObjectMapper();
		    String jsonResponse = objectMapper.writeValueAsString(uDAO.findById(nUser.getId()));
		    resp.setContentType("application/json");
		    resp.getWriter().write(jsonResponse);
		    return;
		}else if (req.getServletPath().endsWith("insertVideo")) {
			Video nVid = new Video();
			try {
				BeanUtils.populate(nVid, req.getParameterMap());
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nVid.setDescription(req.getParameter("description"));
			nVid.setAuthor(u);
			nVid.setDateUpload(new Date());
			vdDAO.insert(nVid);
			sendMail(vdDAO.findById(nVid.getId()),u);
			req.setAttribute("editV",true);
			req.setAttribute("creVid",vdDAO.findById(nVid.getId()));
			req.setAttribute("site","/view/createVid.jsp");
		}else if (req.getServletPath().endsWith("updateVideo")) {
			Video nVid = new Video();
			try {
				BeanUtils.populate(nVid, req.getParameterMap());
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nVid.setDescription(req.getParameter("description"));
			nVid.setDateUpload(new Date());
			nVid.setAuthor(u);
			vdDAO.update(nVid);
			req.setAttribute("editV",true);
			req.setAttribute("creVid",vdDAO.findById(nVid.getId()));
			req.setAttribute("site","/view/createVid.jsp");
		}
		
		req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
	}
	public static void main(String[] args) {
//		findMail("@example.com");
//		findYear();
//		findName();
//		findLike();
//		findLike2();
		
//		findTitleByKeyWord("a");
//		findByPoster("u001");
//		CommentDAO cDAO = new CommentDAOImpl();
//		cDAO.findByVideoId("vid002").forEach(fruit -> System.out.println(fruit.getUser().getFullname()));
	}
	public static void findMail(String mail) {
		UserDAO uDAO = new UserDAOImpl();
		System.out.println(uDAO.findMail(mail));
		for ( User u : uDAO.findMail(mail)) {
			System.out.println(u.getEmail());
		}
	}
	public static void findYear() {
		FavoriteDAO fDAO= new FavoriteDAOImpl();
		for ( User u : fDAO.ListU()) {
			System.out.println(u.getFullname());
		}
	}public static void findName() {
		VideoDAO vdDAO= new VideoDAOImpl();
		for ( Video v : vdDAO.findSearch("a")) {
			System.out.println(v.getTitle());
		}
	}
	public static void findLike() {
		VideoDAO vdDAO= new VideoDAOImpl();
		for ( Video v : vdDAO.ListLike()) {
			System.out.println(v.getTitle());
		}
	}
	public static void findLike2() {
		FavoriteDAO fDAO= new FavoriteDAOImpl();
		for ( Video v : fDAO.ListV()) {
			System.out.println(v.getFavorites().size());
		}
	}
	
	
	public static void findTitleByKeyWord(String key) {
		VideoDAO vdDAO= new VideoDAOImpl();
		for ( Video v : vdDAO.findTitleByKeyWord(key)) {
			System.out.println(v.getTitle());
		}
	}
	public static void findByPoster(String id) {
		VideoDAO vdDAO= new VideoDAOImpl();
		for ( Video v : vdDAO.findByPoster(id)) {
			System.out.println(v.getTitle());
		}
	}
	public static void FindVideoById(String id) {
		VideoDAO vdDAO= new VideoDAOImpl();
		for ( Video v : vdDAO.FindVideoById(id)) {
			System.out.println(v.getTitle());
		}
	}
	public static void findAllSort() {
		FavoriteDAO fDAO= new FavoriteDAOImpl();
		for ( Favorite f : fDAO.findAllSort()) {
			System.out.println(f.getLikeDate());
		}
	}
	public static void findByUserId(String id) {
		FavoriteDAO fDAO= new FavoriteDAOImpl();
		for ( Favorite f : fDAO.findByUserId(id)) {
			System.out.println(f.getVideo().getTitle());
		}
	}
}