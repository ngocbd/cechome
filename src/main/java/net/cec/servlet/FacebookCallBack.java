package net.cec.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

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
			
//https://www.facebook.com/login.php?skip_api_login=1&api_key=1326545090735920&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.8%2Fdialog%2Foauth%3Fredirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8080%252Fcallback%26scope%3Dpublic_profile%252Cemail%252Cpublic_profile%252Cuser_link%26client_id%3D1326545090735920%26ret%3Dlogin%26logger_id%3D7af8ca88-5683-9f44-51fa-404b4be6e1a7&cancel_url=http%3A%2F%2Flocalhost%3A8080%2Fcallback%3Ferror%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%23_%3D_&display=page&locale=en_GB&logger_id=7af8ca88-5683-9f44-51fa-404b4be6e1a7
			FacebookClient client = new DefaultFacebookClient(Version.VERSION_3_1);

			AccessToken token = client.obtainUserAccessToken(Secret.FacebookAppId, Secret.FacebookSecretKey,
					callbackURL, code);
			System.out.println("AccessToken: "+token);
			client = new DefaultFacebookClient(token.getAccessToken(),Version.VERSION_3_1);
			System.out.println("Version: "+Version.VERSION_3_1);
			User user = client.fetchObject("/me", User.class, Parameter.with("fields",
					"email,name,link,id"));
			logger.warning("UserId: "+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());
			if(user != null)
			{
				System.out.println("UserId 1 : "+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());

				Key<Account> key = Key.create(Account.class, Long.parseLong(user.getId()));					
				Account account = ofy().load().key(key).now();
				logger.log(Level.WARNING, "User: "+user.toString());
				logger.log(Level.WARNING,"fb id: "+user.getLink());
				if(account==null)
				{
//					System.out.println("UserId 2: "+user.getId()+ ", Email: "+user.getEmail()+", Name: "+user.getName());
//					String id, String attachments, String type, String content, Long createDate, String featuredImage, Long lastupdate, String permalink, String picture, String posterId
					account = new Account();
					account.setId(Long.parseLong(user.getId()));
					account.setEmail(user.getEmail());
					account.setName(user.getName());
					account.setAccessToken(token.getAccessToken());
					account.setJoinTime(Calendar.getInstance().getTime().getTime());
					account.setLastLogin(Calendar.getInstance().getTime().getTime());	
					account.setPermission("user");
					//Dung tam truong userClass de set Link.
					account.setUserClass(user.getLink());
//					System.out.println("link1: "+user.getLink());	
				}
				else
				{
//					System.out.println("link2: "+user.getLink());
					account.setLastLogin(Calendar.getInstance().getTime().getTime());
					account.setAccessToken(token.getAccessToken());
					account.setUserClass(user.getLink());
				}
				logger.log(Level.WARNING,"fb Id 1: "+account.getFbId());
				if(account.getFbId()==null)
				{
					Jsoup.connect("http://httpsns.appspot.com/queue?name=cecverify")
					.ignoreContentType(true)
					.timeout(60 * 1000)
					.method(Method.POST)
					.ignoreHttpErrors(true)
					.requestBody(user.getLink())
					.execute();
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