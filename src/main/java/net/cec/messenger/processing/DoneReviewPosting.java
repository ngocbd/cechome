package net.cec.messenger.processing;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.api.SendMessage;
import net.cec.entities.Account;
import net.cec.entities.Editor;
import net.cec.entities.MemberPost;
import net.cec.entities.RequestReview;
import net.cec.utils.Utilities;

/**
 * Servlet implementation class ReviewPosting
 */
@WebServlet("/task/posting/done")
public class DoneReviewPosting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(DoneReviewPosting.class.getName());  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoneReviewPosting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//code: #done https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post]
		
		String content = request.getParameter("dcontent");
		String postId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
		
		String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
	 	FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		log.warning("postId: "+postId);
	 	Post post = fbClient .fetchObject( postId, Post.class, Parameter.with("fields", "message, created_time"));
		log.warning("post: "+post.getId());
	 	if(post != null) 
		{
	 		String contentPost = post.getMessage();
	 		List<String> linkList = utilities.extractUrls(contentPost);
	 		log.warning("contentPost: "+contentPost+"\nSize of Link: "+linkList.size());
	 		
	 		
	 		for(int i=0;i<linkList.size();i++)
	 		{
	 			log.warning("Link "+(i+1)+": "+linkList.get(i));
	 			String link = "";
	 			Matcher matcherLesson = Pattern.compile("https://www.facebook.com/groups/cec.edu.vn/permalink/").matcher(linkList.get(i)); 
		 		if (matcherLesson.find())
		 		{
		 			link = linkList.get(i); 
		 		}
		 		else
		 		{
		 			link = utilities.urlRedirect(linkList.get(i));  
		 		}
//	 			log.warning("url of review Posting: "+utilities.urlRedirect(linkList.get(i)));
	 			String reviewPostId = utilities.getNumberFromString(link);
	 			Key<RequestReview> key = Key.create(RequestReview.class, "1784461175160264_"+reviewPostId); 
	 			RequestReview requestReview = ofy().load().key(key).now(); 
	 			if(requestReview!=null)
	 			{
	 				requestReview.setEditorId(senderId);
	 				requestReview.setStatus(2);
	 				requestReview.setReviewDate(post.getCreatedTime().getTime());
	 				ofy().save().entities(requestReview).now();  
	 			 	String responeMessage = "Bài của bạn đã được chữa. Link: https://www.facebook.com/groups/cec.edu.vn/permalink/"+reviewPostId;
//	 				String responeMessage = "Bài của bạn đã được chữa";
	 			 	this.sendMessage.sendMessenge(requestReview.getRequesterId(), responeMessage);
	 			}
	 		}
	 	} 

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
