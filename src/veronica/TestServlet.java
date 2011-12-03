package veronica;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String blah = req.getParameter("something");
		
		System.out.println("IN TEST SERVLET");
		System.out.println(blah);
		
//		if (blah.equals("this is hello world!")) {
//			System.out.println("******");
//			Queue queue = QueueFactory.getDefaultQueue();
//			queue.add(url("/worker/testServlet").method(Method.GET).param("something", "thids is hello world!"));
//			System.out.println("*******DONE");
//		}
//		int i = 0;
//		while (i < 35){
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}  // try-catch statement
//		i++;
//		System.out.println("Slept for " + i + " seconds");
//		}
		
		
		
//		System.out.println("done sleeping********************");
//		if (blah.equals("this is hello world!")) {
//			System.out.println("******");
//			Queue queue = QueueFactory.getDefaultQueue();
//			queue.add(url("/worker/testServlet").method(Method.GET).param("something", "thids is hello world!"));
//			System.out.println("*******DONE");
//		}
	}
}
