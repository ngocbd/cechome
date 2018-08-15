package net.cec.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import net.cec.entities.Account;
import net.cec.entities.Member;
import net.cec.entities.MemberPost;
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = req.getReader();

		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		// reqBytes = buffer.toString().getBytes();
		// {"object":"page","entry":[{"id":"1793377184284243","time":1494832381898,"messaging":[{"sender":{"id":"1442179549173434"},"recipient":{"id":"1793377184284243"},"timestamp":1494832381672,"message":{"mid":"mid.$cAAYF5RZzc2JiPDlG6FcCvREyTnIg","seq":93216,"text":"aaaa"}}]}]}
		log.warning(buffer.toString());

		String query = "https://graph.facebook.com/me/messages?access_token=" + Secret.FacebookPageAccessToken;

		try {
			JSONObject jsonObject = new JSONObject(buffer.toString());
			JSONArray jsonArray = new JSONArray(jsonObject.get("entry").toString());
			JSONObject jsonObject2 = new JSONObject(jsonArray.get(0).toString());
			JSONArray jsonArray2 = new JSONArray(jsonObject2.get("messaging").toString());
			JSONObject jsonObject3 = new JSONObject(jsonArray2.get(0).toString());

			if (jsonObject3.get("message") != null) {
				JSONObject jsonObject4 = new JSONObject(jsonObject3.get("sender").toString());
				String messengerId = jsonObject4.get("id").toString();
				Query<Account> q = ofy().load().type(Account.class);
				q = q.filter("messengerId", messengerId);
				Account account = q.first().now();
				// https://graph.facebook.com/v3.1/1210242782437366/ids_for_apps?access_token=
				if (account == null) {
					String strJson = Jsoup
							.connect("https://graph.facebook.com/v3.1/1210242782437366/ids_for_apps?access_token="
									+ Secret.FacebookPageAccessToken)
							.ignoreContentType(true).execute().body();
					JSONObject jsonObjectRoot = new JSONObject(strJson.toString());
					String data = jsonObjectRoot.get("data").toString();
					JSONArray jsonArrayData = new JSONArray(data);
					JSONObject jsonObjectAccount = new JSONObject(jsonArrayData.get(0).toString());
					String accountId = jsonObjectAccount.getString("id");
					log.warning("Account of Id: " + accountId);
					Key<Account> key = Key.create(Account.class, Long.parseLong(accountId));
					account = ofy().load().key(key).now();
					if (account != null) {
						account.setMessengerId(messengerId);
						ofy().save().entities(account);
					}

				}
				
				
				JSONObject jsonObject5 = new JSONObject(jsonObject3.get("message").toString());
				String text = jsonObject5.get("text").toString().trim().replaceAll("[ ]+", " ");
				String mes = "Xin chào tôi có thể giúp gì cho bạn.";
				if (text == "test") {
					mes = "Hello boy";
				} else if (text.startsWith("#verify")) {
					Matcher matcher = Pattern.compile("(\\d*)").matcher(text);
					int count = 0;
				    matcher.find();
					String memberId = matcher.group(1);
					account.setFbId(memberId);
					ofy().save().entities(account);
					
				}

				String json = "{   \"recipient\": {     \"id\": \"" + jsonObject4.get("id").toString()
						+ "\"   },   \"message\": {     \"text\": \"" + mes + "\"   } }";
				

				

				URL url = new URL(query);
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
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		resp.getWriter().print("OK");
		return;

	}

}
