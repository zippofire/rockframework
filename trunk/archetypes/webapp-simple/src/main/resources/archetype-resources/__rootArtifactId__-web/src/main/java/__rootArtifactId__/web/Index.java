package ${package}.${rootArtifactId}.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {
	
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("<html><body>Welcome to ${rootArtifactId}</body></html>");
	}
	
}