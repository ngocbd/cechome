package net.cec.servlet;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

import net.cec.utils.Secret;

@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(LoginServlet.class.getName());
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String go = request.getParameter("go") ;
    
    
    String referrer = request.getHeader("referer"); 
    if(go==null)
    {
    	if(referrer!=null)
    	go=referrer;
    	else
    	go="https://cec.net.vn/";
    }
    
    go=URLEncoder.encode(go);
   
    logger.warning("go:"+go);
    String callbackURL = request.getScheme() + "://" + request.getServerName();
	if(request.getServerPort()!=80 && request.getServerPort() !=443)
	{
		callbackURL+= ":" + request.getServerPort();
	}
	
	callbackURL+= "/callback?go="+go; 
	logger.warning("callbackURL:"+callbackURL);
    String facebook = new String(Secret.FacebookOauthBaseURL+"client_id="+Secret.FacebookAppId+"&redirect_uri="+callbackURL+"&scope=user_link%2cpublic_profile%2Cemail");
    logger.log(Level.WARNING, "url: "+facebook);
    
    response.sendRedirect(facebook);
   
  }
}