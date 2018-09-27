package net.cec.systems;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.googlecode.objectify.ObjectifyService;
import net.cec.entities.*;


/**
 * Application Lifecycle Listener implementation class StartupListener
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public StartupListener() {
        // TODO Auto-generated constructor stub
    }

    
    
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ObjectifyService.register(MemberPost.class);
    	ObjectifyService.register(Account.class);
    	ObjectifyService.register(Member.class);
    	ObjectifyService.register(RequestReview.class);
    	ObjectifyService.register(Editor.class);
    	ObjectifyService.register(Device.class);
    	ObjectifyService.register(Lesson.class);
    }
	
}
