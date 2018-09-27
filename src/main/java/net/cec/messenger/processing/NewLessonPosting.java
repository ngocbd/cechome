package net.cec.messenger.processing;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

import com.googlecode.objectify.Key;

import net.cec.api.SendMessage;
import net.cec.entities.Account;
import net.cec.entities.MemberPost;
import net.cec.utils.Utilities;

/**
 * Servlet implementation class Receive New Lesson
 */
@WebServlet("/task/posting/newlesson")
public class NewLessonPosting extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utilities = new Utilities();
	SendMessage sendMessage = new SendMessage();
	Logger log = Logger.getLogger(NewLessonPosting.class.getName());  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewLessonPosting() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//code: #newlesson https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post] 
		String mes = "";
		String content = request.getParameter("nlcontent");
		String postId = utilities.getNumberFromString(content);
		String senderId = request.getParameter("senderid");
		Account account = utilities.getAccountByMessengerId(senderId); 
		String memberPostId = "1784461175160264_"+postId;  
		log.warning("Start to get account name. MessengerId: "+senderId);
		
			 	
	 	Key<MemberPost> key = Key.create(MemberPost.class, memberPostId); 
	 	MemberPost memberPost = ofy().load().key(key).now(); 
	 	if(memberPost!=null) 
	 	{
	 		if(account.getEmail()!=null)
	 		{
	 			String str = (memberPost.getContent()).toLowerCase(); 
		 		Matcher matcherLesson = Pattern.compile("#lesson(\\d+)cec").matcher(str); 
		 		if (matcherLesson.find()) 
		 		{
		 			if(memberPost.getAttachments()!=null)
		 			{
		 				if(memberPost.getAttachments().getType().equals("video_inline")) {
		 					int newLesson = Integer.parseInt(utilities.getNumberFromString(str))+1;
				 			log.warning("The New Lesson: "+newLesson);
				 			//send the new lesson to the student 
				 			mes = "Bài luyện tiếp theo của bạn: https://cec.net.vn/lesson/"+newLesson; 
		 				}
		 				else
		 				{
		 					mes = "Bạn gửi link bài luyện đã học không đúng.";
		 				}
		 			}	
		 		} 
		 		else 
		 		{ 
		 			mes = "Bạn gửi link bài luyện đã học không đúng."; 
		 		}
	 		}
	 		else
	 		{
	 			mes = "Bạn chưa cập nhật đầy đủ thông tin cá nhân để chúng tôi có thể gửi bài mới qua email của bạn. https://user.cec.net.vn/account"; 
	 		}	 
	 	} 
	 	else 
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
	 		mes = "Bài luyện tiếp theo của bạn sẽ được gửi tới email của bạn"; 
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