package net.cec.task.handler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import org.apache.commons.lang3.StringEscapeUtils;
import net.cec.utils.Utilities;
import net.cec.entities.*;
import static com.googlecode.objectify.ObjectifyService.ofy;
import com.fcs.*;
/**
 * Servlet implementation class GetPostContent
 */
@WebServlet("/task/crawl/post")
public class GetPostContent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		String strId = request.getParameter("id");
		String posterId = request.getParameter("posterid");
		response.setContentType("text/html");
		String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		FacebookClient fbClient = new DefaultFacebookClient(accessToken,
				Version.LATEST);
				
		Post post = fbClient
				.fetchObject(
						strId,
						Post.class,
						Parameter
								.with("fields",
										"attachments, message, created_time"));
//		String id = post.getId();
//		System.out.println("Id: "+post.getId());
		/*
		Attachments[
		            data=[
		                StoryAttachment[
		                                description=null 
		                                id=null 
		                                media=Media[
		                                            id=null 
		                                            image=Image[
		                                                        height=720 
		                                                        id=null 
		                                                        metadata=null 
		                                                        src=https://scontent.xx.fbcdn.net/v/t15.0-10/s720x720/37570363_10210381507932658_5026467077674762240_n.jpg?_nc_cat=0&oh=c7c908fd56e3ce12b1cbb3d190f988d1&oe=5BFE26C2 
	                                                        	type=null 
	                                                        	width=405
		                                                        ] 
                                        			metadata=null 
                                        			type=null
                                        			] 
                    					metadata=null 
                    					subAttachments=null 
                    					target=Target[
                    					              id=10210381505412595 
                    					              metadata=null 
                    					              type=null 
                    					              url=https://www.facebook.com/saobien.huyen/videos/10210381505412595/
                    					             ] 
					            	    title=null 
					            	    type=video_
					            	    inline url=https://www.facebook.com/saobien.huyen/videos/10210381505412595/
					            	    ]
            	    	]
	    			]
	    */
		
		String attachmentStr = "";
		for(int i=0;i<post.getAttachments().getData().size();i++)
		{
//			System.out.println("Attachments at "+(i+1)+": "+net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
			attachmentStr +=StringEscapeUtils.unescapeEcmaScript(net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
		}
		System.out.println("hbg: "+attachmentStr);
//		System.out.println("Type: "+post.getType());
//		System.out.println("Content: "+post.getMessage());
//		System.out.println("createDate: "+post.getCreatedTime().getTime());
//		System.out.println("Permalink: "+post.getPermalinkUrl());
//		System.out.println("Picture: "+post.getFullPicture());
		
		if(post != null)
		{

			Key<MemberPost> key = Key.create(MemberPost.class, post.getId());					
			MemberPost memberPost = ofy().load().key(key).now();
			if(memberPost==null)
			{
//				String id, String attachments, String type, String content, Long createDate, String featuredImage, Long lastupdate, String permalink, String picture, String posterId
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
				
				Querify querify = new Querify("cec");
//				querify.insert(objs);
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
		Querify.getInstance().createTable(MemberPost.class);
		
	}

}
