package net.cec.messenger.processing;

import static com.googlecode.objectify.ObjectifyService.ofy;

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

import net.cec.api.SendMessage;
import net.cec.entities.Account;
import net.cec.entities.MemberPost;
import net.cec.utils.Utilities;

/**
 * Servlet implementation class UpdatePosting
 */
@WebServlet("/task/posting/update")
public class UpdatePosting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(UpdatePosting.class.getName());     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePosting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String mes = "";
		String content = request.getParameter("ucontent");
		String postId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
		String memberPostId = "1784461175160264_"+postId; 
		MemberPost memberPostFromKey = ofy().load().type(MemberPost.class).id(memberPostId).now();
		log.warning("The MemberPost Id: " + memberPostFromKey.getId());
		if(memberPostFromKey!=null) 
		{ 
			String strId = memberPostFromKey.getId();
			String posterId = memberPostFromKey.getPosterId(); 
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/task/update/post").param("id",strId).param("posterid", posterId) );
			mes = "Chúng tôi sẽ cập nhật bài viết của bạn trong vài giây tới"; 
		 }
		this.sendMessage.sendMessenge(senderId, mes);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}