package net.cec.messenger.processing;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
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
		
	 	Post post = null;
		
		try {
			log.warning("postId: "+postId);
			
			post = fbClient .fetchObject( postId, Post.class, Parameter.with("fields", "message, created_time"));
			log.warning("post: "+post.getId());
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
	 	if(post != null) 
		{
	 		String contentPost = post.getMessage();
	 		List<String> linkListOrigin = utilities.extractUrls(contentPost);
	 		List<String> linkList = new ArrayList<String>();
	 		for(int i=0;i<linkListOrigin.size();i++)
	 		{
	 			Matcher matcherLesson = Pattern.compile("https://www.facebook.com/groups/cec.edu.vn/permalink/").matcher(linkListOrigin.get(i)); 
		 		if (matcherLesson.find())
		 		{
		 			linkList.add(linkListOrigin.get(i)); 
		 		}
		 		else
		 		{
		 			Matcher matcherLessonReal = Pattern.compile("https://www.facebook.com/groups/cec.edu.vn/permalink/").matcher(utilities.urlRedirect(linkListOrigin.get(i))); 
		 			if(matcherLessonReal.find())
		 			{
		 				linkList.add(utilities.urlRedirect(linkListOrigin.get(i)));
		 			}
		 		}
	 		}
	 		log.warning("contentPost: "+contentPost+"\nSize of Link: "+linkList.size());
	 		String memberPostId = "1784461175160264_"+postId; 
			MemberPost memberPostFromKey = null; 
			Key<MemberPost> memberPostkey = Key.create(MemberPost.class, memberPostId); 
			try 
			{ 
				memberPostFromKey = ofy().load().key(memberPostkey).safe(); 
			}
			catch(NotFoundException nfe) 
			{
				String links = "https://www.facebook.com/groups/cec.edu.vn/permalink/"+postId+"/\n";
				Jsoup.connect("http://httpsns.appspot.com/queue?name=cecurl")
				.ignoreContentType(true)
				.timeout(60 * 1000)
				.method(Method.POST)
				.ignoreHttpErrors(true)
				.requestBody(links)
				.execute();
				log.warning("The link: " + links); 
			 }
			Account account = utilities.getAccountByMessengerId(senderId);
			int defaultPrice = 10;
			int sum = 0;
	 		for(int i=0;i<linkList.size();i++)
	 		{
	 			log.warning("Link "+(i+1)+": "+linkList.get(i));
	 			String link = linkList.get(i); 
	 			String reviewPostId = utilities.getNumberFromString(link);
	 			Key<RequestReview> key = Key.create(RequestReview.class, "1784461175160264_"+reviewPostId); 
	 			RequestReview requestReview = ofy().load().key(key).now(); 
	 			if(requestReview!=null)
	 			{
	 				log.warning("request Review Status: "+requestReview.getStatus());
	 				if(requestReview.getStatus()==1)
	 				{
	 					log.warning("request Review Status 1: "+requestReview.getStatus());
	 					log.warning("EditorId: "+requestReview.getEditorId()+"\nSenderId: "+senderId);
	 					if(requestReview.getEditorId().equals(senderId))
		 				{	
	 						log.warning("request review status 3: "+requestReview.getStatus());
			 				requestReview.setStatus(2);
			 				requestReview.setReviewDate(post.getCreatedTime().getTime());
		 					int money = account.getMoney()+defaultPrice;
			 				account.setMoney(money);
			 				sum+=10;
			 				Account reviewRequestAccount = utilities.getAccountByMessengerId(requestReview.getRequesterId());
			 				int reviewRequestMoney = reviewRequestAccount.getMoney()-defaultPrice;
			 				reviewRequestAccount.setMoney(reviewRequestMoney);
			 				//da sua lai logic. Chi tru tien member khi editor da sua xong. Chua test.
			 				ofy().save().entities(account,requestReview,reviewRequestAccount);
			 				log.warning("request Review Status 2: "+requestReview.getStatus());
		 				}
		 				
		 			 	String responeMessage = "Bài của bạn đã được chữa. Link: https://www.facebook.com/groups/cec.edu.vn/permalink/"+reviewPostId;
		 			 	this.sendMessage.sendMessenge(requestReview.getRequesterId(), responeMessage);
	 				}
	 				
	 			}
	 		}
	 		String contentendingEditorS = "Bạn nhận được "+sum+" cec từ bài bạn đã chữa.";
	 		this.sendMessage.sendMessenge(senderId, contentendingEditorS);
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
