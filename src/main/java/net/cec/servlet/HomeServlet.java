package net.cec.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
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
import net.cec.entities.RequestReview;
import net.cec.models.Attachments;
import net.cec.task.handler.GetPostContent;
import net.cec.utils.Utilities;

@WebServlet(
    name = "HomeServlet",
    urlPatterns = {"/"}
)
public class HomeServlet extends HttpServlet {
	static Logger log = Logger.getLogger(HomeServlet.class.getName());   
	Utilities utilities = new Utilities();
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    RequestDispatcher dispatcher=request.getRequestDispatcher("/pages/home.jsp");  

    /*
     * Get top 10 Altp Videos
     * */
    List<String> idAltpList = new ArrayList<String>();
    try {
		TableResult result = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where (content like '%#LESSON%' or content like '%#lesson%') and length(id) < 40 order by createdDate desc LIMIT 10");
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
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> altpPosts1=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idAltpList).values()); 
    List<MemberPost> altpPosts = new ArrayList<MemberPost>();
    List<String> memberListID = new ArrayList<>();
    for (Iterator iterator = altpPosts1.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		if(memberPost.getAttachments()!=null)
		{
			log.warning("attachment: "+memberPost.getAttachments().getUrl());
			if(memberPost.getAttachments().getType().equals("video_inline"))
			{
				memberPost.setPicture(memberPost.getAttachments().getUrl());
				memberListID.add(memberPost.getPosterId());	
				altpPosts.add(memberPost);
			}
		}
	}
    request.setAttribute("altpPosts", altpPosts);
    Map<String, Member> ids = ofy().load().type(Member.class).ids(memberListID);
    request.setAttribute("memberIds", ids);
    log.warning("length of altpPosts: "+altpPosts.size());
    
    /*
     * Get top 10 90 days Videos
     * */
    List<String> idDay90List = new ArrayList<String>();
    try {
		TableResult resultDay90 = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Dd][Aa][Yy] \\d+/\\d+') = true and length(id) < 40 order by createdDate desc  limit 10");
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
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> day90Posts1=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idDay90List).values()); 
    List<MemberPost> day90Posts = new ArrayList<MemberPost>();
    List<String> memberDay90ListID = new ArrayList<>();
    for (Iterator iterator = day90Posts1.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		if(memberPost.getAttachments()!=null)
		{
			log.warning("attachment: "+memberPost.getAttachments().getUrl());
			if(memberPost.getAttachments().getType().equals("video_inline"))
			{
				memberPost.setPicture(memberPost.getAttachments().getUrl());
				memberDay90ListID.add(memberPost.getPosterId());
				day90Posts.add(memberPost);
			}
		}
		
		
	}
    request.setAttribute("day90Posts", day90Posts);
    Map<String, Member> day90Ids = ofy().load().type(Member.class).ids(memberDay90ListID);
    request.setAttribute("day90Ids", day90Ids);
    
    /*
     * Get top 10 San Tay Videos
     * */
    List<String> idSanTayList = new ArrayList<String>();
    try {
		TableResult santayResult = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Ss][Ăă][Nn] [Tt][Ââ][Yy]') = true and length(id) < 40 order by createdDate desc limit 10");
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
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> santayPosts1=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idSanTayList).values()); 
    
    List<String> memberSantayListID = new ArrayList<>();
    List<MemberPost> santayPosts = new ArrayList<MemberPost>();
    for (Iterator iterator = santayPosts1.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		if(memberPost.getAttachments()!=null)
		{
			log.warning("attachment: "+memberPost.getAttachments().getUrl());
			if(memberPost.getAttachments().getType().equals("video_inline"))
			{
				memberPost.setPicture(memberPost.getAttachments().getUrl());
				memberSantayListID.add(memberPost.getPosterId());	
				santayPosts.add(memberPost);
			}
			
		}
		
	}
    request.setAttribute("santayPosts", santayPosts);
    Map<String, Member> sanTayIds = ofy().load().type(Member.class).ids(memberSantayListID);
    request.setAttribute("sanTayIds", sanTayIds);
    log.warning("length of santayPosts: "+santayPosts.size());
    
    /*
     * Get top 10 live stream Videos
     * */
    List<String> idLivestreamList = new ArrayList<String>();
    try {
		TableResult livestreamResult = Querify.getInstance("cec").query("SELECT id FROM `crazy-english-community.cec.MemberPost` where  REGEXP_CONTAINS(content,r'[Ll][Ii][Vv][Ee][Ss][Tt][Rr][Ee][Aa][Mm]') = true and length(id) < 40 order by createdDate desc limit 10");
		for (FieldValueList row :livestreamResult.iterateAll()) 
		{
			log.warning("id of live stream content: "+row.get("id").getStringValue());
	        idLivestreamList.add(row.get("id").getStringValue());    
		}
	} catch (JobException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    List<MemberPost> livestreamPosts1=  new ArrayList<MemberPost>(ofy().load().type(MemberPost.class).ids(idLivestreamList).values()); 
    //create livestreamPost list to filter the post don't contain video.
    List<MemberPost> livestreamPosts = new ArrayList<MemberPost>();
    List<String> memberLivestreamListID = new ArrayList<>();
    for (Iterator iterator = livestreamPosts1.iterator(); iterator.hasNext();) {
		MemberPost memberPost = (MemberPost) iterator.next();
		if(memberPost.getAttachments()!=null)
		{
			log.warning(": "+memberPost.getAttachments());
			if(memberPost.getAttachments().getType().equals("video_inline"))
			{
				memberPost.setPicture(memberPost.getAttachments().getUrl());
				livestreamPosts.add(memberPost);
				memberLivestreamListID.add(memberPost.getPosterId());
			}
		}
			
	}
    request.setAttribute("livestreamPosts", livestreamPosts);
    Map<String, Member> livestreamIds = ofy().load().type(Member.class).ids(memberLivestreamListID);
    request.setAttribute("livestreamIds", livestreamIds);
    log.warning("idLiveStreamList: "+ idLivestreamList.size());
    
    
    
    /*
     * Get top 10 posts are requested reviewing
     * */
    
    List<RequestReview> requestReviewPosts = new ArrayList<RequestReview>(ofy().load().type(RequestReview.class).filter("status <",2).limit(10).list());
    request.setAttribute("requestReviewPosts", requestReviewPosts);
    
    
    
    /*
     * Get top 10 posts reviewing are done
     * */
    
    List<RequestReview> reviewDonePosts = new ArrayList<RequestReview>(ofy().load().type(RequestReview.class).filter("status ==",2).limit(10).list());
    request.setAttribute("reviewDonePosts", reviewDonePosts);
    
    /*
     * Get top 10 member are the most rich
     * */
    
    List<Account> richMembers = new ArrayList<Account>(ofy().load().type(Account.class).order("-money").limit(10).list());
    request.setAttribute("richMembers", richMembers);
    
    
    try {
		dispatcher.forward(request, response);
	} catch (ServletException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//method may be include or forward      
    
    
  }
}