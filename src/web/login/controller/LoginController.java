package web.login.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import web.common.controller.BaseController;
import web.core.util.ContextHolderUtils;
import web.core.util.IpUtil;
import web.core.util.ResourceUtil;
import web.core.util.WeiXinConstants;
import web.login.service.LoginService;
import web.system.entity.SysRoles;
import web.system.entity.SysUsers;
import web.system.entity.WeiXinPublicUser;
import web.system.manager.Client;
import web.system.manager.ClientManager;

import com.alibaba.fastjson.JSONObject;

@Scope("prototype")
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController {
	private final Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(params = "checklogin")
	@ResponseBody
	public JSONObject check(SysUsers user, HttpServletRequest req) {
		System.out.println("....name..." + user.getUsername()
				+ "...password..." + user.getPassword());
		HttpSession session = ContextHolderUtils.getSession();
		JSONObject j = new JSONObject();
		String randCode = req.getParameter("randCode");
		if (StringUtils.isEmpty(randCode)) {
			j.put("msg", "请输入验证码");
			j.put("success", false);
			return j;
		} else if (!randCode.equalsIgnoreCase(String.valueOf(session
				.getAttribute("randCode")))) {
			// todo "randCode"和验证码servlet中该变量一样，通过统一的系统常量配置比较好，暂时不知道系统常量放在什么地方合适
			j.put("msg", "验证码错误");
			j.put("success", false);
			return j;
		} else {
			List<SysUsers> lu = loginService.findByProperty(SysUsers.class,
					"username", user.getUsername());
			try {
				if (lu.size() == 0) {
					j.put("success", false);
					j.put("msg", "该用户不存在 ");
					return j;
				} else {

					if (!loginService.checkExist(user)) {
						j.put("success", false);
						j.put("msg", "密码错误 ");
						return j;
					} else {
						user = lu.get(0);
						user.setAuthorities(loginService
								.loadUserAuthorities(user.getUsername()));
						Client client = new Client();
						client.setIp(IpUtil.getIpAddr(req));
						client.setLogindatetime(new Date());
						client.setUser(user);
						ClientManager.getInstance().addClient(session.getId(),
								client);
						List<WeiXinPublicUser> list = user.getPublicUserList();
						Enumeration<String> em = session.getAttributeNames();
						while (em.hasMoreElements()) {
							session.removeAttribute(em.nextElement().toString());
						}
						session.setAttribute("CKFinder_UserRole", "admin");
						for (WeiXinPublicUser pu : list) {
							if (pu.getStatus().equals("默认")) {
								session.setAttribute(
										WeiXinConstants.WEIXIN_ACCOUNT, pu);
							}
						}
						j.put("success", true);
						j.put("msg", "");
						return j;
					}
				}
			} catch (Exception e) {
				j.put("success", false);
				j.put("msg", "系统异常！！");
				e.printStackTrace();
				return j;

			}
		}
	}

	@RequestMapping(params = "checkregister")
	@ResponseBody
	public JSONObject checkRegister(SysUsers user, HttpServletRequest req) {
		System.out.println("....name..." + user.getUsername()
				+ "...password..." + user.getPassword());
		HttpSession session = ContextHolderUtils.getSession();
		JSONObject j = new JSONObject();
		int users = loginService.findByProperty(SysUsers.class, "username",
				user.getUsername()).size();

		if (users > 0) {
			j.put("success", false);
			j.put("msg", "该用户已存在 ");
			return j;
		} else {

			j.put("success", true);
			j.put("msg", "");
			user.setStatus((short) 1);
			user.getSysroles().add(
					(SysRoles) loginService.findByProperty(SysRoles.class,
							"role_id", 2).get(0));
			loginService.save(user);
			return j;
		}

	}

	/*
	 * spring的MVC是对Servlet的封装，ModelAndView中addObject应该是对request.setAttribute
	 * 方法的封装，所以在jsp中如果想以el表达式来读取数据，应该用${requestScope.depts}，
	 * 对应于<%=request.getAttribute("depts")%>
	 */
	@RequestMapping(params = "login")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = ContextHolderUtils.getSession();
		SysUsers user = ResourceUtil.getSessionUser();
		if (user != null) {
			System.out
					.println("------------------------\nin login controller params login there is a user mapped session\n===========================");

			return "/main/frame";

		} else {
			System.err
					.println("in login controller params login there is no user mapped session");
			return "login/login";
			// return new ModelAndView("login/login");
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		SysUsers user = ResourceUtil.getSessionUser();

		ClientManager.getInstance().removeClient(session.getId());
		session.invalidate();
		// session.setMaxInactiveInterval(0);
		ModelAndView modelAndView = new ModelAndView(new RedirectView(
				"loginController.do?login"));
		return modelAndView;
	}

	@RequestMapping(params = "noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("common/noAuth");
	}
}
