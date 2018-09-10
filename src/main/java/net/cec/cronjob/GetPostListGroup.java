package net.cec.cronjob;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import com.googlecode.objectify.Key;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

import net.cec.utils.Utilities;
import net.cec.entities.*;

/**
 * Servlet implementation class GetPostListGroup. The class to get all posts in the list of the group.
 */
@WebServlet("/cron/crawl/links")
public class GetPostListGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(GetPostListGroup.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostListGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		FacebookClient fbClient = new DefaultFacebookClient(accessToken,
				Version.LATEST);
		
		//https://www.facebook.com/mon.mon.8997/videos/pcb.2129681200638258/950382381836554/?type=3&ifg=1
		//https://www.facebook.com/media/set/?set\u003dpcb.2129681200638258\u0026type\u003d1
		Connection<JsonObject> listPost = fbClient
				.fetchConnection(
						Utilities.groupId + "/feed",
						JsonObject.class,
						Parameter.with("limit", Utilities.limitPageFB),
						Parameter
								.with("fields",
										"id"));
//		response.getWriter().println("id: "+listPost.getData().get(0).toString());
		String links = "";
		MemberPost memberPost = null;
		try 
		{
			int count = 0;
			log.warning("size of listPost: "+listPost.getData().size());
			for(int i=0;i<listPost.getData().size();i++)
			{
				String postGroupId = listPost.getData().get(i).toString();
				log.warning("postGroupId: "+postGroupId);
				String postId = postGroupId.substring(postGroupId.indexOf("_")+1, postGroupId.length()-2);
				log.warning("postId: "+postId);
				String postIdInDb = "1784461175160264_"+postId;
//				System.out.println("Id "+(i+1)+" of Post: "+ postId);
				Key<MemberPost> key = Key.create(MemberPost.class, postIdInDb);
				memberPost = ofy().load().key(key).now();
				if(memberPost==null)
				{
					links += "https://www.facebook.com/groups/cec.edu.vn/permalink/"+postId+"/\n";
					count +=1;
				}
				
				//create string 100links
			}
			log.warning("sum of the link: "+count);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.warning("errer when get size of links: "+e.getMessage());
		}
		
		
//		System.out.println("all link: "+links);
		log.warning("All Posts links of the group : "+links);

		Jsoup.connect("http://httpsns.appspot.com/queue?name=cecurl")
		.ignoreContentType(true)
		.timeout(60 * 1000)
		.method(Method.POST)
		.ignoreHttpErrors(true)
		.requestBody(links)
		.execute();
			
		response.getWriter().println(links);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
