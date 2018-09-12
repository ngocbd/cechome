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
	static Logger log = Logger.getLogger(HomeServlet.class.getName());   

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
    
    List<String> idDay90List = new ArrayList<String>();
    try {
		TableResult resultDay90 = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Dd][Aa][Yy] \\d+/\\d+') = true and length(id) < 40 limit 10");
		for (FieldValueList row :resultDay90.iterateAll()) 
		{
	        log.warning("id of Days 90 content: "+row.get("id").getStringValue());
	        idDay90List.add(row.get("id").getStringValue());
		}
	} catch (JobException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> day90Posts=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idDay90List).values()); 
    request.setAttribute("day90Posts", day90Posts);
    List<String> memberDay90ListID = new ArrayList<>();
    for (Iterator iterator = day90Posts.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		log.warning("attachment: "+memberPost.getAttachments().getUrl());
		memberPost.setPicture(memberPost.getAttachments().getUrl());
		memberDay90ListID.add(memberPost.getPosterId());	
	}
    Map<String, Member> day90Ids = ofy().load().type(Member.class).ids(memberDay90ListID);
    request.setAttribute("day90Ids", day90Ids);
    
    List<String> idSanTayList = new ArrayList<String>();
    try {
		TableResult santayResult = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Ss][Ăă][Nn] [Tt][Ââ][Yy]') = true and length(id) < 40 limit 10");
		for (FieldValueList row :santayResult.iterateAll()) 
		{
	        log.warning("id of san tay content: "+row.get("id").getStringValue());
	        idSanTayList.add(row.get("id").getStringValue());
		}
	} catch (JobException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> santayPosts=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idSanTayList).values()); 
    request.setAttribute("santayPosts", santayPosts);
    List<String> memberSantayListID = new ArrayList<>();
    for (Iterator iterator = santayPosts.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		log.warning("attachment: "+memberPost.getAttachments().getUrl());
		memberPost.setPicture(memberPost.getAttachments().getUrl());
		memberSantayListID.add(memberPost.getPosterId());	
	}
    Map<String, Member> sanTayIds = ofy().load().type(Member.class).ids(memberSantayListID);
    request.setAttribute("sanTayIds", sanTayIds);
    log.warning("length of santayPosts: "+santayPosts.size());
    
    
    try {
		dispatcher.forward(request, response);
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//method may be include or forward      
    
    
//SELECT content FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Dd]ay \d+/\d+') = true limit 10
  }
}