package net.cec.task.handler;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fcs.Querify;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.entities.Account;
import net.cec.entities.MemberPost;

@WebServlet("/task/update/post")
public class UpdatePostContent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePostContent() {
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
		String strId = request.getParameter("id");
		String posterId = request.getParameter("posterid");
		response.setContentType("text/html");
		String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);

		Post post = fbClient.fetchObject(strId, Post.class,
				Parameter.with("fields", "attachments, message, created_time"));
		

		String attachmentStr = "";
		for (int i = 0; i < post.getAttachments().getData().size(); i++) {
			// System.out.println("Attachments at "+(i+1)+":
			// "+net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
			attachmentStr += StringEscapeUtils
					.unescapeEcmaScript(net.cec.utils.Utilities.GSON.toJson(post.getAttachments().getData().get(i)));
		}
//		System.out.println("hbg: " + attachmentStr);
		
		if (post != null) {
			Key<MemberPost> key = Key.create(MemberPost.class, post.getId());
			MemberPost memberPost = ofy().load().key(key).now();
			if (memberPost != null) {
				// String id, String attachments, String type, String content, Long createDate,
				// String featuredImage, Long lastupdate, String permalink, String picture,
				// String posterId
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
				// querify.insert(objs);
				querify.insert(memberPost);
			}
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