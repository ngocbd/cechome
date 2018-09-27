package net.cec.task.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import org.apache.commons.lang3.StringEscapeUtils;

import net.cec.utils.Secret;
import net.cec.utils.Utilities;
import net.cec.cronjob.GetPostListGroup;
import net.cec.entities.*;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.fcs.*;

/**
 * Servlet implementation class GetPostContent
 */
@WebServlet("/task/crawl/post")
public class GetPostContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(GetPostContent.class.getName()); 
	Utilities utilities = new Utilities();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostContent() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//id = id of posting
		String strId = request.getParameter("id");
		//posterid = fbId
		String posterId = request.getParameter("posterid");
		response.setContentType("text/html");
		
		log.warning("postID:"+strId);
		log.warning("author:Id:"+posterId);
		
		//token thang xeng dang bi loi ngu ngu //String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		
		String accessToken = Secret.FacebookPageAccessToken;
		FacebookClient fbClient = new DefaultFacebookClient(accessToken,
				Version.LATEST);
				
		Post post = fbClient
				.fetchObject(
						strId,
						Post.class,
						Parameter
								.with("fields",
										"attachments, message, created_time"));


		if(post != null)
		{
			String attachmentStr = "";
			
			Key<MemberPost> key = Key.create(MemberPost.class, post.getId());					
			MemberPost memberPost = ofy().load().key(key).now();
			if(memberPost==null)
			{
//				String id, String attachments, String type, String content, Long createDate, String featuredImage, Long lastupdate, String permalink, String picture, String posterId
				try {
					for(int i=0;i<post.getAttachments().getData().size();i++)
					{
	//					System.out.println("Attachments at "+(i+1)+": "+net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
						log.warning("description in attachment: "+post.getAttachments().getData().get(i).getDescription());
						log.warning("attachments in for loop: "+post.getAttachments().getData().get(i));
						if(!post.getAttachments().getData().get(i).equals(null))
						{
//							log.warning("habogay");
							try {
								post.getAttachments().getData().get(i).setDescription(post.getAttachments().getData().get(i).getDescription().replaceAll("\"", "'"));
							} catch (Exception e) {
								// TODO: handle exception
								log.warning("Error when get Description in the attachments: "+e.getMessage());
							}
							attachmentStr +=StringEscapeUtils.unescapeEcmaScript(net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
						}
					}
					log.warning("Attachments: "+attachmentStr);
				} catch (Exception e) {
					// TODO: handle exception
					log.warning("Error of the attachment: "+e.getMessage());
				}
				
				memberPost = new MemberPost();
				memberPost.setId(post.getId());
				memberPost.setAttachments(attachmentStr);
				memberPost.setType(post.getType());
				memberPost.setContent(post.getMessage());
				memberPost.setCreatedDate(post.getCreatedTime().getTime());
				memberPost.setLastUpdate(Calendar.getInstance().getTime().getTime());
				memberPost.setPermalink(post.getPermalinkUrl());
				memberPost.setPicture(post.getFullPicture());
				memberPost.setPosterId(posterId);
				ofy().save().entities(memberPost);	
				
				String str = (memberPost.getContent()).toLowerCase(); 
		 		Matcher matcherLesson = Pattern.compile("#lesson(\\d+)cec").matcher(str); 
		 		if (matcherLesson.find()) 
		 		{
		 			if(memberPost.getAttachments()!=null)
		 			{
		 				if(memberPost.getAttachments().getType().equals("video_inline")) 
		 				{
		 					int numberLesson = Integer.parseInt(utilities.getNumberFromString(str));
				 			log.warning("The New Lesson: "+numberLesson);
				 			log.warning("posterId: "+posterId);
//				 			ofy().load().type(RequestReview.class).filter("status ==",2)
				 			
				 			Query<Account> q = ofy().load().type(Account.class);
				 			q = q.filter("fbId", posterId);
				 			Account acc = q.first().now();
							        
							if(acc!=null)
							{
								log.warning("accId: "+acc.getId());
								Key<Lesson> keyLesson = Key.create(Lesson.class, acc.getId());
								Lesson lesson = ofy().load().key(keyLesson).now();
								
					 			if(lesson!=null)
					 			{
					 				if(!lesson.getLesson().contains(numberLesson)) {
					 					lesson.getLesson().add(numberLesson);
					 					Set<Integer> s = new LinkedHashSet<>(lesson.getLesson());
					 				}
					 			}
					 			else
					 			{
					 				lesson = new Lesson();
					 				lesson.setId(acc.getId());
					 				lesson.getLesson().add(numberLesson);
					 				
					 			}
					 		
					 			ofy().save().entities(lesson);
							}
		 				}
		 			}	
		 		} 

				Querify querify = Querify.getInstance("cec");
				querify.insert(memberPost);
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
	
	public static void main(String[] args) {
		//Querify.getInstance("cec").createTable(MemberPost.class);
		String strId = "2159714607634917";
		
		log.warning("postID:"+strId);
		
		
		String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		FacebookClient fbClient = new DefaultFacebookClient(Secret.FacebookPageAccessToken,
				Version.LATEST);
				
		Post post = fbClient
				.fetchObject(
						strId,
						Post.class,
						Parameter
								.with("fields",
										"attachments, message, created_time"));

		log.warning("post:"+post.getMessage());
		
	}

}
