package web.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionExpiredFiler implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		String requestURI = request.getRequestURI();
		System.out.println("in sessionExpiredfilter pos 0,before indirect:"
				+ requestURI + ":req:" + req.hashCode());
		if (requestURI.endsWith(".jpg") || requestURI.endsWith(".jpeg")
				|| requestURI.endsWith(".png") || requestURI.endsWith("html")
				|| requestURI.endsWith(".htm") || requestURI.endsWith(".css")
				|| requestURI.endsWith(".js") || requestURI.endsWith(".ftl")
				|| requestURI.indexOf("plug-in") != -1
				|| requestURI.indexOf("image") != -1
				|| requestURI.indexOf("template") != -1)
			chain.doFilter(req, rep);
		else if (requestURI.indexOf("coreServlet.do") == -1
				&& requestURI.indexOf(".jsp") == -1 && !requestURI.equals("/")
				&& requestURI.indexOf("loginController.do") == -1
				&& request.getSession(false) == null) {
			System.out.println("in sessionExpiredfilter,begin indirect jsp:"
					+ requestURI + ":req:" + req.hashCode());
			response.sendRedirect("webpage/login/timeout.jsp");
		} else
			chain.doFilter(req, rep);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
