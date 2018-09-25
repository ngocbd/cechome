package net.cec.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
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
import com.restfb.types.User;

import net.cec.entities.Account;
import net.cec.entities.Lesson;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;
import net.cec.entities.RequestReview;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet(name = "AllLessonServlet", urlPatterns = { "/alllesson/*" })
public class AllLessonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(AllLessonServlet.class.getName());
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AllLessonServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long accId = 0;
		
		if(request.getParameter("id")==null&&request.getParameter("id")!="")
		{
			User user = (User)request.getAttribute("User");
			log.warning("accId: "+user.getId());
			accId = Long.parseLong(user.getId());
		}
		else
		{
			accId = Long.parseLong(request.getParameter("id"));
		}
		
		Key<Lesson> keyLesson = Key.create(Lesson.class, accId);
		Lesson lesson = ofy().load().key(keyLesson).now();
		
		try {
			log.warning("lesson : "+lesson.getLesson().size());
			GcsService gcsService = GcsServiceFactory.createGcsService();
			String folder = "crazy-english-community.appspot.com";
			String prefix = "altp/lesson";
			List<SimpleEntry>lessonNameLists = new ArrayList<>();
			
			ListOptions options = new ListOptions.Builder().setRecursive(true)
					  .setPrefix(prefix)
					  .build();
			GcsFileOptions fileOptions = new GcsFileOptions.Builder()
	                .acl("public-read").build(); 
			
			ListResult result = gcsService.list(folder, options);
			String imgUrl1 ="";
			
			while(result.hasNext())
			{
				ListItem  items = result.next();
				
				if(items.getName().endsWith("1.jpg"))
				{
					imgUrl1 = items.getName().substring(items.getName().lastIndexOf("/")+1,items.getName().indexOf("1.jpg"));
					int numberLesson = 0;
					List<Integer> lessonList = lesson.getLesson();
					Collections.sort(lessonList);
					
					
//					get number of the lesson in img src. Then, compare the numberLesson with number lesson in datastore. It's the same number, add to the lesson list to display.
					 
			 		Matcher matcherLesson = Pattern.compile("altp/lesson(\\d+)/").matcher(items.getName()); 
			 		if (matcherLesson.find()) 
			 		{
			 			numberLesson = Integer.parseInt(matcherLesson.group(1));
			 		}
//					numberLesson = Integer.parseInt(imgUrl1.substring(imgUrl1.indexOf("false:altp/lesson")+17,(imgUrl1.indexOf("false:altp/lesson")+17)+2).trim());
					log.warning("content: "+imgUrl1+"\n numberLesson in the img src: "+numberLesson);
					
					if(lesson.getLesson().contains(numberLesson))
					{ 
						SimpleEntry<Integer, String> aaa = new SimpleEntry(numberLesson, imgUrl1);
						log.warning("number lesson: "+numberLesson);
						lessonNameLists.add(aaa);
						
					}
						
				}

				GcsFilename fileName = new GcsFilename(folder, items.getName());
				gcsService.update(fileName, fileOptions);
			}
			
			
			
			//https://storage.googleapis.com/crazy-english-community.appspot.com/altp/lesson01/How%20To%20Be%20A%20Good%20Learner.mp3
			
			request.setAttribute("lessonNameLists", lessonNameLists);
			request.setAttribute("id1", accId);
		} catch (Exception e) {
			// TODO: handle exception
			log.warning("Error: "+e.getMessage());
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/allLesson.jsp");
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
