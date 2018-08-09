package net.cec.servlet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.WebRequestor;
import com.restfb.logging.RestFBLogger;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

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

			String callbackURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/callback";
			

			FacebookClient client = new DefaultFacebookClient(Version.LATEST);

			AccessToken token = client.obtainUserAccessToken(Secret.FacebookAppId, Secret.FacebookSecretKey,
					callbackURL, code);

			Cookie c = new Cookie(Secret.TokenCookieName, token.getAccessToken());
			c.setDomain(request.getServerName());
			c.setPath("/");
			response.addCookie(c);
			
			response.sendRedirect("/");

		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	}

	public static void main(String[] args) throws IOException {

	}

}