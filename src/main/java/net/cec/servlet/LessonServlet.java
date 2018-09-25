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

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.entities.Account;
import net.cec.entities.Lesson;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet(name = "LessonServlet", urlPatterns = { "/lesson/*" })
public class LessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(LessonServlet.class.getName());
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LessonServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Matcher matcher = Pattern.compile("/lesson/(\\d*)/?").matcher(request.getRequestURI());
	    matcher.find();
		int lessonId = Integer.parseInt(matcher.group(1));
		long accId = Long.parseLong(request.getParameter("id1"));
		Key<Lesson> keyLesson = Key.create(Lesson.class, accId);
		Lesson lesson = ofy().load().key(keyLesson).now();
		
		if(lesson!=null)
		{
			List<Integer> lessonList = lesson.getLesson();
			if(lessonList.contains(lessonId))
			{
				GcsService gcsService = GcsServiceFactory.createGcsService();
				String folder = "crazy-english-community.appspot.com";
				String prefix = "altp/lesson";
				String numberLesson = "";
				if(lessonId<10)
				{
					numberLesson = "0"+lessonId;
				}
				else
				{
					numberLesson  = lessonId+"";
				}
				prefix += numberLesson;
				ListOptions options = new ListOptions.Builder().setRecursive(true)
					  .setPrefix(prefix)
					  .build();
				GcsFileOptions fileOptions = new GcsFileOptions.Builder()
		                .acl("public-read").build(); 
				
				ListResult result = gcsService.list(folder, options);
				String imgUrl1 ="https://storage.googleapis.com/crazy-english-community.appspot.com/";
				String imgUrl2 = imgUrl1;
				String mp3Url = imgUrl1;
				String videoUrl = imgUrl1;
				
				while(result.hasNext())
				{
					ListItem  items = result.next();
					if(items.getName().endsWith("mp3"))
					{
						mp3Url += items.getName();
						log.warning("mp3: "+mp3Url);
					}
					if(items.getName().endsWith("mp4"))
					{
						videoUrl += items.getName();
						log.warning("video: "+videoUrl);
					}
					if(items.getName().endsWith("1.jpg"))
					{
						imgUrl1 += items.getName();
						log.warning("url1: "+imgUrl1);
					}
					if(items.getName().endsWith("2.jpg"))
					{
						imgUrl2 += items.getName();
						log.warning("url2: "+imgUrl2);
					}
					
					GcsFilename fileName = new GcsFilename(folder, items.getName());
					gcsService.update(fileName, fileOptions);
				}
				
				//https://storage.googleapis.com/crazy-english-community.appspot.com/altp/lesson01/How%20To%20Be%20A%20Good%20Learner.mp3
				log.warning("img1: "+imgUrl1+" - img2: "+imgUrl2+" - mp3: "+mp3Url+" - video: " + videoUrl );
				request.setAttribute("imgUrl1", imgUrl1);
				request.setAttribute("imgUrl2", imgUrl2);
				request.setAttribute("mp3Url", mp3Url);
				request.setAttribute("videoUrl", videoUrl);
			}
		}
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/lesson.jsp");
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
