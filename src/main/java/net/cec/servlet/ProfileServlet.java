package net.cec.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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
	static Logger log = Logger.getLogger(ProfileServlet.class.getName());
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
		log.warning("memberId: "+memberId);
							
		Account account = ofy().load().type(Account.class).filter("fbId",memberId).first().now();
		String intro = ""; 
		int level = 0, dedication = 0, streak = 0;
		boolean fbExist = false;
		log.warning("Gia tri cua fbExist: "+fbExist);
		if(account != null)
		{
			log.warning("streak: "+account.getDaily());
			intro = account.getIntroduce();
			level = account.getLevel();
			dedication = account.getDedication();
			streak = account.getDaily();
			fbExist = true;
			log.warning("Gia tri cua fbExist sau khi check acc khac null: "+fbExist);
		}
		
		Query<MemberPost> q = ofy().load().type(MemberPost.class);
		q = q.filter("posterId", memberId);
		List<MemberPost> posts1 = q.list();
		if(posts1.size()>0)
		{
			for(int i=0;i<posts1.size();i++)
			{
				MemberPost  memberPost = posts1.get(i);
				String display = "";
				
				if(posts1.get(i).getAttachments()!=null)
				{
					if(posts1.get(i).getAttachments().getType().equals("video_inline"))
					{
						display = "<div class=\"fb-video\" data-href=\""+posts1.get(i).getAttachments().getUrl()+"\" data-width=\"500\" data-show-text=\"false\">";
					}
					if(posts1.get(i).getAttachments().getType().equals("photo"))
					{
						display = "<img  src=\""+posts1.get(i).getAttachments().getMedia().getImage().getSrc()+"\">";
					}
				}
				else
				{
					//display = posts1.get(i).getContent(); Doan nay Tit lam 1 cai anh giong nhu fb, co 1 cai khung, trong do la noi dung cua status.
					display = "<img  src=\"https://storage.googleapis.com/crazy-english-community.appspot.com/images/kukixinh.JPG\">";
				}
				memberPost.setPicture(display);
			}
		}
		

		request.setAttribute("member", member);
		request.setAttribute("posts", posts1);
		request.setAttribute("intro", intro);
		request.setAttribute("level", level);
		request.setAttribute("dedication", dedication);
		request.setAttribute("streak", streak);
		request.setAttribute("fbExist", fbExist);
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
