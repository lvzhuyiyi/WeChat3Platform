package web.core.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import web.core.util.ContextHolderUtils;
import web.core.util.ResourceUtil;
import web.system.entity.SysAuthorities;
import web.system.entity.SysUsers;
import web.system.manager.Client;
import web.system.manager.ClientManager;

/**
 * 权限拦截器
 * 
 * @author 张代浩
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger
			.getLogger(AuthInterceptor.class);
	private List<String> excludeUrls;
	private Map<String, String> pageAuths;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public Map<String, String> getPageAuths() {
		return pageAuths;
	}

	public void setPageAuths(Map<String, String> excludeUrls) {
		this.pageAuths = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 在controller前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		if (requestPath.indexOf("coreServlet.do") != -1)
			return true;
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client == null) {
			client = ClientManager.getInstance().getClient(
					request.getParameter("sessionId"));
		}
		requestPath = requestPath.substring(requestPath.lastIndexOf('/') + 1);
		if (requestPath.endsWith("null"))
			requestPath = requestPath.substring(0, requestPath.length() - 5);
		System.out.println("requestPath:" + requestPath + ":"
				+ requestPath.length());
		if (excludeUrls.contains(requestPath)) {
			System.out.println("白名单 不拦截");
			return true;
		} else {
			if (client != null && client.getUser() != null) {
				if (!hasUrlAuth(client.getUser(), requestPath)) {
					System.out.println("权限不足 被转发:" + requestPath);
					response.sendRedirect("loginController.do?noAuth");
					// request.getRequestDispatcher("webpage/common/noAuth.jsp").forward(request,
					// response);
					return false;
				} else {
					System.out.println("权限足 可以访问:" + requestPath);
					return true;
				}
			}
			System.out.println("client 为null 或许未登录:" + requestPath);

			response.sendRedirect("loginController.do?noAuth");
			return false;

		}
	}

	private boolean hasUrlAuth(SysUsers user, String requestPath) {
		for (String pattern : pageAuths.keySet()) {

			System.out.println("pattern:" + pattern);
			if (requestPath.equals(pattern)) {
				List<String> result = new ArrayList<String>();
				for (SysAuthorities au : user.getAuthorities()) {

					result.add(au.getAuthority_name());
					System.out.println("user authorities:" + au);
				}
				System.out.println("pageAuths:" + pageAuths.get(pattern));
				if (result.contains(pageAuths.get(pattern))) {
					return true;
				}
				return false;
			}
		}
		System.out.println("还未添加权限 放行");
		return true;
	}

	/**
	 * 转发
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "forward")
	public ModelAndView forward(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("loginController.do?login"));
	}

	private void forward(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("webpage/login/timeout.jsp").forward(
				request, response);
	}

}
