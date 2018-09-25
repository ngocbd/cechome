package net.cec;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
/**
 * Gen new Deck from BagOfWord  
 */

@WebServlet(name = "TestGCS", urlPatterns = { "/TestGCS" })
public class TestGCS extends HttpServlet {

	private static final long serialVersionUID = 1774416536427856683L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		GcsService gcsService = GcsServiceFactory.createGcsService();
		String folder = request.getParameter("folder");
		String prefix = request.getParameter("prefix");
		
		ListOptions options = new ListOptions.Builder().setRecursive(true)
			  .setPrefix(prefix)
			  .build();
		GcsFileOptions fileOptions = new GcsFileOptions.Builder()
                .acl("public-read").build(); 
		
		ListResult result = gcsService.list(folder, options);
		while(result.hasNext())
		{
			ListItem  items = result.next();
			response.getWriter().println(items.isDirectory() +":"+ items.getName());
			GcsFilename fileName = new GcsFilename(folder, items.getName());
			gcsService.update(fileName, fileOptions);
		}
		
		
		
		
		
	}
}