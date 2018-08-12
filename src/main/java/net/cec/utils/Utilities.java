package net.cec.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;





public class Utilities {

	public static Logger LOGGER = Logger.getLogger(Utilities.class.getName());

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
