package net.cec.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import com.fcs.Querify;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.entities.Account;
import net.cec.entities.Editor;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;
import net.cec.entities.RequestReview;
import net.cec.mesenger.Hook;
import net.cec.utils.Secret;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Servlet implementation class SubmitPostServlet
 */
@WebServlet("/webhook")
public class WebHookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(WebHookServlet.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebHookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");

		

		Gson gson = new Gson();

		Hook hook = gson.fromJson(req.getReader(), Hook.class);
		
		String content = "";
		String senderId = "";
		
		try {
			log.warning("Hook: "+gson.toJson(hook));
			log.warning(hook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
			content = hook.getEntry().get(0).getMessaging().get(0).getMessage().getText();
			senderId = hook.getEntry().get(0).getMessaging().get(0).getSender().getId();
			log.warning("size of hook: "+hook.getEntry().size());
			log.warning("messengerId: "+senderId);
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
		String message = "";
		if(content == "test") 
		{ 
			message = "Hello";
		}	
		/*
		 * The Member wants to verify the FacebookId
		 * code: #verify [facebook id number]
		 */
		if(content.startsWith("#verify ")) 
		{ 
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/task/fbid/verify")
					.param("vcontent", content)
					.param("senderid", senderId)
					);
		}

		/*
		 * The Member wants to update the Posting in CEC fanpage
		 * code: #update https://www.facebook.com/groups/cec.edu.vn/permalink/[postId]
		 */
		if(content.startsWith("#update "))
		{
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/task/posting/update")
					.param("ucontent", content)
					.param("senderid", senderId)
					);
		}
		
		/*
		 * The Member wants the Editors request reviewing the Post in CEC fanpage
		 * code: #review https://www.facebook.com/groups/cec.edu.vn/permalink/[postId]
		 */
		if(content.startsWith("#review ")) 
		{
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/task/posting/review")
					.param("rcontent", content)
					.param("senderid", senderId)
					);
		}
		
		/*
		 * the admin is reviewing for a member's lesson
		 * code: #reviewing https://www.facebook.com/groups/cec.edu.vn/permalink/[postId]
		 */
		 if(content.startsWith("#reviewing ")) 
		 { 
			 Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/task/posting/reviewing")
						.param("ricontent", content)
						.param("senderid", senderId)
						); 
		 }
		
		 
		 /*
			 * the admin review for a member's lesson done.
			 * code: #done https://www.facebook.com/groups/cec.edu.vn/permalink/[postId]
		 */
		 if(content.startsWith("#done ")) 
		 { 
			 Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/task/posting/done")
						.param("dcontent", content)
						.param("senderid", senderId)
						); 
		 }

		 
		 /*
			 * The Member wants to receive the new lesson
			 * code: #newlesson https://www.facebook.com/groups/cec.edu.vn/permalink/[postId]
		 */
		 if(content.startsWith("#newlesson ")) 
		 {
			 Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/task/posting/newlesson")
						.param("nlcontent", content)
						.param("senderid", senderId)
						); 
		 }
		 
		
		
		resp.getWriter().print("OK");
		return;

	}

}
