package net.cec.cronjob;

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

import net.cec.utils.Utilities;
import net.cec.entities.*;
import static com.googlecode.objectify.ObjectifyService.ofy;
/**
 * Servlet implementation class GetPostContent
 */
@WebServlet("/GetPostContent")
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
		Date haftYearAgo = new Date(System.currentTimeMillis()
				- (1000L * 60L * 60L * 24L * 180L));
		boolean noUpdate = false;
		
		
		Post post = fbClient
				.fetchObject(
						strId,
						Post.class,
						Parameter
								.with("fields",
										"admin_creator,application,backdated_time,call_to_action,caption,child_attachments,comments_mirroring_domain,coordinates,created_time,description,event,from,full_picture,expanded_height,expanded_width,feed_targeting,height,icon,id,link,message,name,object_id,parent_id,permalink_url,picture,place,privacy,promotion_status,properties,scheduled_publish_time,shares,source,status_type,story,story_tags,subscribed,target,targeting,timeline_visibility,type,updated_time,via,width,with_tags,attachments{description,media,target,title,type,url,description_tags},comments.limit(100000).summary(true){from,message,comment_count,created_time,id,like_count},reactions.limit(100000).summary(true){id,link,name,type,username},sharedposts.summary(true){actions,from}"));
//		String id = post.getId();
//		System.out.println("Id: "+post.getId());
//		System.out.println("Attachments: "+post.getAttachments().toString());
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
				memberPost.setAttachments(post.getAttachments().toString());
				memberPost.setType(post.getType());
				memberPost.setContent(post.getMessage());
				memberPost.setCreatedDate(post.getCreatedTime().getTime());
				memberPost.setLastUpdate(Calendar.getInstance().getTime().getTime());
				memberPost.setPermalink(post.getPermalinkUrl());
				memberPost.setPicture(post.getFullPicture());
				memberPost.setPosterId(posterId);
				
				ofy().save().entities(memberPost);	
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
