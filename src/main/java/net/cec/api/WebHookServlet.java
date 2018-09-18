package net.cec.api;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;

import net.cec.mesenger.Hook;

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
		 
		
		 /*
			 * The Member wants to the balance
			 * code: #balance
		 */
		 if(content.startsWith("#balance")) 
		 {
			 Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/task/posting/balance")
						.param("bcontent", content)
						.param("senderid", senderId)
						); 
		 }
		
		resp.getWriter().print("OK");
		return;

	}

}
