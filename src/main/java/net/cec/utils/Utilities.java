package net.cec.utils;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;

import net.cec.api.WebHookServlet;
import net.cec.entities.Account;





public class Utilities {
	
	public static Logger log = Logger.getLogger(Utilities.class.getName());

	public static String serverUrl = "http://crazy-english-community.appspot.com";

	public static int oneDayInSecond = (60 * 60 * 24);

	public static String defaultCover = "https://scontent.fhan5-1.fna.fbcdn.net/v/t1.0-9/11391717_507317402754165_1600647839889366482_n.jpg?oh=051b80a4d8aad7737afd8d2850ea74d9&oe=599A1976";

	public static String defaultAvatar = "https://scontent.fhan2-1.fna.fbcdn.net/v/t1.0-1/c47.0.160.160/p160x160/10354686_10150004552801856_220367501106153455_n.jpg?oh=c4ee157e861c38c8a06c9bee7ffea1bf&oe=596BCE49";

	public static String defaultName = "Unknow";

	public static String defaultStatusImage = "http://i2.kym-cdn.com/photos/images/original/000/078/864/goofy_time.png";

	static String cacheCategories = "categories";

	static String ranking = "ranking";

	static String varToken = "accessToken";

	static String activityMembers = "activityMembers";

	static String topLevel = "topLevel";

	static String topDedication = "topDedication";

	public static String groupId = "1784461175160264";
	
	public static String chungVDId = "813829762102926";
	
	public static int limitPageFB = 100;

	public static Gson GSON = new Gson();

	public static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ");
	
	public static SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat(
			"dd/MM/yyyy");
	
	public static SimpleDateFormat sdf2 = new SimpleDateFormat(
			"dd/MM");
	/**
	 * Get the Account from Messenger Id
	 * @param String messengerId
	 * @return Account acc
	 * */
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
//			log.warning("The Account's id: " + accountId);
//			log.warning("The Messenger's id: " + messengerId);
			Key<Account> key = Key.create(Account.class, Long.parseLong(accountId));
			account = ofy().load().key(key).now();
			if (account != null) {
				account.setMessengerId(messengerId);
				ofy().save().entities(account);
//				log.warning("The Messenger's id after inserting in database: " + messengerId);
			}
		}
		return account;
	}

	/**
	 * Get number from the String
	 * @param String str
	 * @return String number
	 * */
	public String getNumberFromString(String str) {
		log.warning("String with number: "+str);
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

	
	public enum status {
		success(1), error(-1), draft(0);

		private int s;

		private status(int s) {
			this.s = s;
		}

		public int getStatus() {
			return s;
		}
	}
	public static String replaceEmail (String email) {
		String [] arr = email.split("@");
		if (arr.length >= 2) {
			if (arr[0].length() > 4) {
				String str = arr[0].substring(0,4);
				String str1 = arr[0].substring(4, arr[0].length());
				for (int i = 0; i < str1.length(); i++) {
					str += "*";
				}
				return str+"@"+arr[1];
			}
		}
		
		return email;
	}
	public enum gameType {
		ghepchu(1), ailatrieuphu(2);

		private int s;

		private gameType(int s) {
			this.s = s;
		}

		public int getType() {
			return s;
		}
	}
	
	public enum gameStatus {
		running("running"), end("end"), stop("stop"), pending("pending");

		private String s;

		private gameStatus(String s) {
			this.s = s;
		}

		public String getStatus() {
			return s;
		}
	}

	
	/**
	 * JSON object input stream.
	 * 
	 * @param in
	 *            the in
	 * @return the JSON object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static JSONObject jsonObjectInputStream(InputStream in)
			throws IOException {
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(in,
				Charset.forName("UTF-8")));
		JSONObject json = new JSONObject();
		while ((line = br.readLine()) != null) {
			try {
				json = new JSONObject(line);
			} catch (JSONException e) {
				return null;
			}
		}
		return json;
	}

	/**
	 * BÄƒm nhá»� list thÃ nh list chá»©a cÃ¡c list nhá»� hÆ¡n
	 **/
	public static <T> List<List<T>> chopped(List<T> list, final int L) {
		List<List<T>> parts = new ArrayList<List<T>>();
		final int N = list.size();
		for (int i = 0; i < N; i += L) {
			parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}

	
}
