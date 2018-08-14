package net.cec.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.entities.Account;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet(name = "ProfileServlet", urlPatterns = { "/m/*" })
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		//url=http://localhost:8080/m/100007532462525
		
		Matcher matcher = Pattern.compile("/m/(\\d*)/?").matcher(request.getRequestURI());
		 int count = 0;
	    matcher.find();
		String memberId = matcher.group(1);
		
		
//		System.out.println("member id: "+memberId);
		response.setContentType("text/html");
		Key<Member> key = Key.create(Member.class, memberId);
		Member member = ofy().load().key(key).now();

		Query<MemberPost> q = ofy().load().type(MemberPost.class);
		q = q.filter("posterId", memberId);
		List<MemberPost> posts = q.list();

		request.setAttribute("member", member);
		request.setAttribute("posts", posts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/profile.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
