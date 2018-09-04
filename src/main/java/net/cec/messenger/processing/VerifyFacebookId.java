package net.cec.messenger.processing;

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
import net.cec.api.WebHookServlet;
import net.cec.entities.Account;
import net.cec.utils.Utilities;
import net.cec.api.*;
import static com.googlecode.objectify.ObjectifyService.ofy;
/**
 * Servlet implementation class VerifyFacebookId
 */
@WebServlet("/task/fbid/verify")
public class VerifyFacebookId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(VerifyFacebookId.class.getName());   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyFacebookId() {
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
		String content = request.getParameter("vcontent");
		String fbId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
		Account account = utilities.getAccountByMessengerId(senderId);
		account.setFbId(fbId);
		log.warning("Id of the Facebook acc by verify: " + senderId);
		log.warning("get fbId from acc: "+account.getFbId());
		ofy().save().entities(account).now(); 
		mes = "Facebook Id Number của bạn đã được verify"; 
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
