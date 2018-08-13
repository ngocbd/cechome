package net.cec.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import static com.googlecode.objectify.ObjectifyService.ofy;
import net.cec.entities.Account;
import net.cec.utils.Secret;

@WebServlet(name = "FacebookCallBack", urlPatterns = { "/callback" })
public class FacebookCallBack extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(FacebookCallBack.class.getName());

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = request.getParameter("code");

		if (code == null || code.isEmpty()) {
			response.sendRedirect("/login");
		} else {
			String callbackURL = request.getScheme() + "://" + request.getServerName();
			if(request.getServerPort()!=80 && request.getServerPort() !=443)
			{
				callbackURL+= ":" + request.getServerPort();
			}
			
			callbackURL+= "/callback";
			

			FacebookClient client = new DefaultFacebookClient(Version.LATEST);

			AccessToken token = client.obtainUserAccessToken(Secret.FacebookAppId, Secret.FacebookSecretKey,
					callbackURL, code);
			client = new DefaultFacebookClient(token.getAccessToken(),Version.LATEST);
			User user = client.fetchObject("/me", User.class, Parameter.with("fields",
					"email,name"));
			logger.warning("UserId: "+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());
			if(user != null)
			{
				System.out.println("UserId 1 : "+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());

				Key<Account> key = Key.create(Account.class, user.getId());					
				Account account = ofy().load().key(key).now();
				if(account==null)
				{
					System.out.println("UserId: 2"+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());
//					String id, String attachments, String type, String content, Long createDate, String featuredImage, Long lastupdate, String permalink, String picture, String posterId
					account = new Account();
					account.setId(Long.parseLong(user.getId()));
					account.setEmail(user.getEmail());
					account.setName(user.getName());
					account.setAccessToken(token.getAccessToken());
					account.setJoinTime(Calendar.getInstance().getTime().getTime());
					account.setLastLogin(Calendar.getInstance().getTime().getTime());	
					
				}
				else
				{
					account.setLastLogin(Calendar.getInstance().getTime().getTime());
					account.setAccessToken(token.getAccessToken());
				}
				ofy().save().entities(account);	
			}
			
			Cookie c = new Cookie(Secret.TokenCookieName, token.getAccessToken());
			c.setDomain(request.getServerName());
			c.setPath("/");
			response.addCookie(c);
			
			response.sendRedirect("/");

		}
//		System.out.println("Habogay stayed here.");
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	}

	public static void main(String[] args) throws IOException {

	}

}