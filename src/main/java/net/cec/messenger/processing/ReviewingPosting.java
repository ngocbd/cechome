package net.cec.messenger.processing;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Logger;

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

import net.cec.api.SendMessage;
import net.cec.entities.Account;
import net.cec.entities.Editor;
import net.cec.entities.MemberPost;
import net.cec.entities.RequestReview;
import net.cec.utils.Utilities;

/**
 * Servlet implementation class ReviewPosting
 */
@WebServlet("/task/posting/reviewing")
public class ReviewingPosting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(ReviewingPosting.class.getName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewingPosting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//code: #reviewing https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post] 
		String mes = "";
		String content = request.getParameter("ricontent");
		String postId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
		Account account = utilities.getAccountByMessengerId(senderId); 
		String memberPostId = "1784461175160264_"+postId; 
		mes = "Bạn có 24 giờ để hoàn thành phần chữa bài này. Sau khi hoàn thành, hãy gửi mã với nội dung #sbtc https://www.facebook.com/groups/cec.edu.vn/permalink/" +postId; 
		log.warning("Start to get account name. MessengerId: "+senderId);
		
		Key<RequestReview> key = Key.create(RequestReview.class, memberPostId); 
		RequestReview requestReview = ofy().load().key(key).now(); 
		if(requestReview!=null)
		{
			requestReview.setEditorId(senderId);
			requestReview.setStatus(1);
			requestReview.setReviewDate(Calendar.getInstance().getTime().getTime());
			ofy().save().entities(requestReview).now(); 
			Query<Editor> q = ofy().load().type(Editor.class); 
		 	String reqMes = account.getName()+" đang chữa bài này: https://www.facebook.com/groups/cec.edu.vn/permalink/" +postId; 
		 	log.warning("size of editor: "+q.list().size()); 
		 	
		 	for(int i = 0;i<q.list().size();i++) 
		 	{
				 log.warning("editorId: "+q.list().get(i).getId()); 
				 long accId = Long.parseLong(q.list().get(i).getId()); 
				 Key<Account> accKey = Key.create(Account.class, accId); 
				 Account accountFromEditor = ofy().load().key(accKey).now();
				 log.warning("messengerId: "+accountFromEditor.getMessengerId());
				 if(accountFromEditor.getMessengerId()!=null) 
				 { 
					 this.sendMessage.sendMessenge(accountFromEditor.getMessengerId(), reqMes); 
				 }
		 	}
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
