package web.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ForbiddeDirectVisitJspFilter implements Filter {
	private FilterConfig config;

	@Override
	public void init(FilterConfig config) throws ServletException {

		this.config = null;
	}

	@Override
	public void destroy() {

		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		System.out.println("requestPaths in  ForbiddenDirectVisitJspfilter:"
				+ requestURI + ":" + contextPath);
		if (requestURI.endsWith("timeout.jsp")
				|| requestURI.endsWith("login.jsp")
				|| requestURI.endsWith("introduce.jsp"))
			chain.doFilter(request, response);
		else if (requestURI.endsWith(".jsp")) {
			System.out
					.println("in ForbiddenDirectVisitJspfilter begin forward");
			request.getRequestDispatcher("/webpage/login/login.jsp").forward(
					request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

}
