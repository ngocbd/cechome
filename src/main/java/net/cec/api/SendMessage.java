package net.cec.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.cec.utils.Secret;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/sendmessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(SendMessage.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void sendMessenge(String receiverId, String content) throws ServletException, IOException {
		String query = "https://graph.facebook.com/me/messages?access_token=" + Secret.FacebookPageAccessToken;
		String json = "{   \"recipient\": {     \"id\": \"" + receiverId + "\"   },   \"message\": {     \"text\": \"" + content
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String receiverId = request.getParameter("receiverid");
		String content = request.getParameter("content");
		this.sendMessenge(receiverId, content);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
