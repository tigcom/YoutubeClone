package servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
@WebListener
public class SessionListener implements HttpSessionListener  {
	 @Override
	    public void sessionDestroyed(HttpSessionEvent se) {
		 	System.out.println("Đã hủy session");
	        HttpSession ses = se.getSession();
	        ServletContext ctx = ses.getServletContext();
	        String lastV = (String) ses.getAttribute("lastVid");
	        Integer countVold = (Integer) ctx.getAttribute(lastV); 
	        int oldcount =countVold-1;
    		ctx.setAttribute(lastV, oldcount);
	    }
}
