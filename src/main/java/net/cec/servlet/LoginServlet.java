package net.cec.servlet;

import java.io.IOException;
import java.net.URL;

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

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String callbackURL = request.getScheme() + "://" + request.getServerName();
	if(request.getServerPort()!=80 && request.getServerPort() !=443)
	{
		callbackURL+= ":" + request.getServerPort();
	}
	
	callbackURL+= "/callback"; 
    String facebook = new String(Secret.FacebookOauthBaseURL+"client_id="+Secret.FacebookAppId+"&redirect_uri="+callbackURL+"&scope=public_profile%2Cemail%2Cpublic_profile");
    
    
    response.sendRedirect(facebook);
    
    
    
    

  }
}