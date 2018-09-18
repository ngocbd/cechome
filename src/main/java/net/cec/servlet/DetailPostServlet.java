	package net.cec.servlet;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import net.cec.entities.MemberPost;
import net.cec.models.Attachments;
import net.cec.task.handler.GetPostContent;

/**
 * Servlet implementation class DetailPostServlet
 */
@WebServlet("/p/*")
public class DetailPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(GetPostContent.class.getName());    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//https://www.facebook.com/groups/244938432775599/permalink/260423091227133/
		Matcher matcher = Pattern.compile("/p/(\\d*)/?").matcher(request.getRequestURI());
		 int count = 0;
	    matcher.find();
//		1784461175160264_1785781861694862
		String postId = "1784461175160264_"+matcher.group(1);
		
		Key<MemberPost> key = Key.create(MemberPost.class, postId);
		MemberPost memberPost = ofy().load().key(key).now();
		Attachments attachment = memberPost.getAttachments();
		String display = "";
		
		if(attachment!=null)
		{
			//display video, image. priority video first, image second.
			log.warning("type of Attachments: "+ attachment.getType());
			if(attachment.getType().equals("video_inline"))
			{
				display = "<div class=\"fb-video\" data-href=\""+attachment.getUrl()+"\" data-width=\"500\" data-show-text=\"false\">";
			}
			if(attachment.getType().equals("photo"))
			{
				display = "<img  src=\""+attachment.getMedia().getImage().getSrc()+"\">";
			}
			
		}
		request.setAttribute("post", memberPost);
		request.setAttribute("display", display);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/post.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
