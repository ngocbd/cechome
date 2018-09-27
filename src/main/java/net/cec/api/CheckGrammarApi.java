package net.cec.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.cec.utils.Utilities;

/**
 * Servlet implementation class CheckGrammarApi
 */
@WebServlet("/check")
public class CheckGrammarApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilities utils = new Utilities();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckGrammarApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		https://54.39.21.78/v2/check?text=she%20are%20 
		String path = "https://54.39.21.78/v2/check";
		String text = request.getParameter("text");
		String result = Jsoup.connect(path).method(Method.POST)
				.data("text", text).data("language","en-US")
				.validateTLSCertificates(false)
				.ignoreContentType(true)
				.ignoreHttpErrors(true)
				.execute().body();
		response.getWriter().println(result);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
