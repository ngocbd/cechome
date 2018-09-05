package net.cec.servlet;

import java.io.IOException;
import java.net.URL;
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
    String callbackURL = request.getScheme() + "://" + request.getServerName();
	if(request.getServerPort()!=80 && request.getServerPort() !=443)
	{
		callbackURL+= ":" + request.getServerPort();
	}
	
	callbackURL+= "/callback"; 
    String facebook = new String(Secret.FacebookOauthBaseURL+"client_id="+Secret.FacebookAppId+"&redirect_uri="+callbackURL+"&scope=user_link%2cpublic_profile%2Cemail");
    logger.log(Level.WARNING, "url: "+facebook);
    
    response.sendRedirect(facebook);
    
    
    /*
     	# Giới thiệu về cộng đồng 
		# Các hoạt động chính của CEC
		# Các cách tham gia cùng CEC 
		#Video  -> promote https://www.facebook.com/groups/cec.edu.vn/permalink/2117397505199961
		
		# video theo cuoc thi : ailatrieuphu
		#Video theo seri vi du noi tiếng anh trong 90ngay ( cai nay goi la hanh trinh chinh phuc tieng anh )
		# theo Event ( san tay o ho tay ) , Lịch Event sắp tới - có số điện thoại liên hệ với người tổ chức
		
		#LiveStream - Đã diễn ra (MIKE , mrNam , Em Trang IPA ... )  , Đang - các livestream đang diễn ra,  lịch live steam trong 7 ngày tới
		#CHữa bài 
		# Yêu câu chữa bài ( là các video cần được chữa nhưng chưa có ai chữa - mất tiền )
		# Các topic học zoom với người nước ngoài 1 on 1 , 
		
		Member list theo các tiêu chí ( Nhiều tiền nhất , nhiều đóng góp nhất , Tương tác nhiều nhất , Vừa thắng các giải )
     * */
    

  }
}