package net.cec.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;

import com.fcs.Querify;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.cmd.Query;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;

import net.cec.entities.Account;
import net.cec.entities.Editor;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;
import net.cec.entities.RequestReview;
import net.cec.mesenger.Hook;
import net.cec.utils.Secret;

import static com.googlecode.objectify.ObjectifyService.ofy;

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

	public String getNumberFromString(String str) {
		Matcher matcher = Pattern.compile("(\\d+)").matcher(str);
		matcher.find();
		return matcher.group(1);
	}

	/**
	 * Get the List of the urls from the String
	 * 
	 * @param String
	 *            text
	 * @return list<String> url
	 */
	public List<String> extractUrls(String text) {
		List<String> containedUrls = new ArrayList<String>();
		String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher urlMatcher = pattern.matcher(text);

		while (urlMatcher.find()) {
			containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
		}

		return containedUrls;
	}

	/**
	 * Get the real url from the directly url
	 * @param String directlyUrl
	 * @return String realUrl
	 * */
	public String urlRedirect(String url) {
		String urlRedirectStr = "";
		try {
			urlRedirectStr = Jsoup.connect(url).followRedirects(false).execute().header("Location");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlRedirectStr;
	}

	public void sendMessenge(String id, String mes) throws ServletException, IOException {
		String query = "https://graph.facebook.com/me/messages?access_token=" + Secret.FacebookPageAccessToken;
		String json = "{   \"recipient\": {     \"id\": \"" + id + "\"   },   \"message\": {     \"text\": \"" + mes
				+ "\"   } }";

		URL url = new URL(query);
		log.warning("query: " + query + "\njson str: " + json);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");

		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();

		// read the response
		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

		in.close();
		conn.disconnect();
	}

	public void sendAllMessageToEditors(String reqMes) throws ServletException, IOException {

		Query<Editor> q = ofy().load().type(Editor.class);
		log.warning("size of editor: " + q.list().size());
		// String messIdList = "List of messengerId: \n";
		for (int i = 0; i < q.list().size(); i++) {
			log.warning("editorId: " + q.list().get(i).getId());
			long accId = Long.parseLong(q.list().get(i).getId());
			Key<Account> accKey = Key.create(Account.class, accId);
			Account accountFromEditor = ofy().load().key(accKey).now();
			log.warning("messengerId: " + accountFromEditor.getMessengerId());
			if (accountFromEditor.getMessengerId() != null) {
				// messIdList += "\n"+accountFromEditor.getMessengerId();
				this.sendMessenge(accountFromEditor.getMessengerId(), reqMes);
			}

		}
		// log.warning(messIdList);
	}

	public Account getAccountByMessengerId(String messengerId) throws IOException {

		Query<Account> q = ofy().load().type(Account.class);
		q = q.filter("messengerId", messengerId);
		Account account = q.first().now();

		if (account == null) {
			String strJson = Jsoup.connect("https://graph.facebook.com/v3.1/" + messengerId
					+ "/ids_for_apps?access_token=" + Secret.FacebookPageAccessToken).ignoreContentType(true).execute()
					.body();
			JSONObject jsonObjectRoot = new JSONObject(strJson.toString());
			String data = jsonObjectRoot.get("data").toString();
			JSONArray jsonArrayData = new JSONArray(data);
			JSONObject jsonObjectAccount = new JSONObject(jsonArrayData.get(0).toString());
			String accountId = jsonObjectAccount.getString("id");
			log.warning("The Account's id: " + accountId);
			log.warning("The Messenger's id: " + messengerId);
			Key<Account> key = Key.create(Account.class, Long.parseLong(accountId));
			account = ofy().load().key(key).now();
			if (account != null) {
				account.setMessengerId(messengerId);
				ofy().save().entities(account);
				log.warning("The Messenger's id after inserting in database: " + messengerId);
			}
		}
		return account;
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
		log.warning("Hook: "+gson.toJson(hook));
		log.warning(hook.getEntry().get(0).getMessaging().get(0).getMessage().getText());
		log.warning("size of hook: "+hook.getEntry().size());
		String content = hook.getEntry().get(0).getMessaging().get(0).getMessage().getText();
		String senderId = hook.getEntry().get(0).getMessaging().get(0).getSender().getId();
		log.warning("messengerId: "+senderId);
		String message = "";
		if(content == "test") 
		{ 
			message = "Hello";
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
		 
		
		
		resp.getWriter().print("OK");
		return;

	}

}
