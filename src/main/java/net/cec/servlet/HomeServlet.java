package net.cec.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fcs.Querify;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.TableResult;
import com.googlecode.objectify.cmd.Query;

import static com.googlecode.objectify.ObjectifyService.ofy;

import net.cec.entities.Account;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;
import net.cec.task.handler.GetPostContent;

@WebServlet(
    name = "HomeServlet",
    urlPatterns = {"/"}
)
public class HomeServlet extends HttpServlet {
	static Logger log = Logger.getLogger(GetPostContent.class.getName());   

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/home.jsp");  
  //servlet2 is the url-pattern of the second servlet  
  //SELECT id FROM `crazy-english-community.cec.MemberPost` where (content like '%#LESSON%' or content like '%#lesson%') and length(id) < 40 LIMIT 1000 
    List<String> idAltpList = new ArrayList<String>();
    try {
		TableResult result = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where (content like '%#LESSON%' or content like '%#lesson%') and length(id) < 40 LIMIT 10 ");
		for (FieldValueList row :result.iterateAll()) 
		{
	        log.warning("id of altp content: "+row.get("id").getStringValue());
	        idAltpList.add(row.get("id").getStringValue());
		}
	} catch (JobException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    List<MemberPost> altpPosts=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idAltpList).values()); 
    request.setAttribute("altpPosts", altpPosts);
    List<String> memberListID = new ArrayList<>();
    for (Iterator iterator = altpPosts.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		log.warning("attachment: "+memberPost.getAttachments().getUrl());
		memberPost.setPicture(memberPost.getAttachments().getUrl());
		memberListID.add(memberPost.getPosterId());
		
	}
    
    Map<String, Member> ids = ofy().load().type(Member.class).ids(memberListID);
    request.setAttribute("memberIds", ids);
    log.warning("length of altpPosts: "+altpPosts.size());
    try {
		dispatcher.forward(request, response);
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//method may be include or forward      
    
    

  }
}