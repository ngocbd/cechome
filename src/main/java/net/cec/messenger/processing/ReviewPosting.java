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
@WebServlet("/task/posting/review")
public class ReviewPosting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(ReviewPosting.class.getName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewPosting() {
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
		String content = request.getParameter("rcontent");
		String postId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
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
			mes = "Chúng tôi chưa có bài viết này của bạn. Vui lòng đợi trong giây lát rồi gửi lại lệnh";
		 
		 }
		
		if(memberPostFromKey!=null)
		{
			Key<RequestReview> key = Key.create(RequestReview.class, memberPostFromKey.getId()); 
			RequestReview requestReview = ofy().load().key(key).now(); 
			int defaultPrice= 10;
			Account account = utilities.getAccountByMessengerId(senderId);
			if(account.getMoney()>=defaultPrice)
			{
				if(requestReview==null) 
				{
					log.warning("Request Review null");
					requestReview = new RequestReview();
				 	requestReview.setPostid(memberPostFromKey.getId());
				 	
				 	if(account.getMessengerId()!=null) 
				 	{
				 		requestReview.setRequesterMessengerId(account.getMessengerId()); 
				 		requestReview.setRequesterAccountId(account.getId()+"");
				 	}
				 
				 	requestReview.setCreatedDate(Calendar.getInstance().getTime().getTime());
				 	requestReview.setStatus(0); 
				 	requestReview.setPrice(10);
				 
				 	ofy().save().entities(requestReview,account); 
				 	mes = "Chúng tôi đã nhận được yêu cầu sửa bài của bạn. Chúng tôi sẽ cập nhật bài viết của bạn trong vài giây"; 
				 	Query<Editor> q = ofy().load().type(Editor.class); 
				 	String reqMes = "Bài yêu cầu được chữa. Nếu bạn chữa bài này,hãy gửi lại một mã với nội dung: #reviewing https://www.facebook.com/groups/cec.edu.vn/permalink/" +postId; 
				 	log.warning("size of editor: "+q.list().size()); 
//				 	String messIdList = "List of messengerId: \n"; 
				 	try 
				 	{
				 		for(int i = 0;i<q.list().size();i++) 
					 	{
							 log.warning("editorId: "+q.list().get(i).getId()); 
							 long accId = Long.parseLong(q.list().get(i).getId()); 
							 Key<Account> accKey = Key.create(Account.class, accId);  
							 Account accountFromEditor = ofy().load().key(accKey).now();
//							 log.warning("messengerId: "+accountFromEditor.getMessengerId());
							 if(accountFromEditor!=null) 
							 { 
//								 messIdList += "\n"+accountFromEditor.getMessengerId();
								 log.warning("messengerId: "+accountFromEditor.getMessengerId());
								 this.sendMessage.sendMessenge(accountFromEditor.getMessengerId(), reqMes); 
							 }
					 	}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				else
				{
					log.warning("Request Review is not null");
//					log.warning("habogay da tung di dao qua day"); 
				 	mes = "Yêu cầu chữa bài của bạn đang được xử lý"; 
				}
			}
			else
			{
				mes = "Bạn không đủ tiền để yêu cầu sửa bài. Bạn hãy nạp cec bằng cách chuyển khoản vnd vào tài khoản vietcombank. Tên chủ tk: Luong Thi Phien Số tk: 0801000250785 Nội dung: CEC-"+account.getId();
			}
			
		}
		if(mes !="" || mes ==null)
		{
			this.sendMessage.sendMessenge(senderId, mes);
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