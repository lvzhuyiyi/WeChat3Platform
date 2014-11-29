package user.management.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jodd.servlet.URLDecoder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import user.management.service.WeixinPublicAccountService;
import web.core.util.ContextHolderUtils;
import web.core.util.ResourceUtil;
import web.core.util.WeiXinConstants;
import web.login.service.LoginService;
import web.system.entity.SysUsers;
import web.system.entity.WeiXinPublicUser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/userController")
public class UserController {
	private final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private WeixinPublicAccountService weiXinService;

	@RequestMapping(params = "loadCurrentUserArray")
	@ResponseBody
	public JSONArray loadUserArray() {
		SysUsers user = ResourceUtil.getSessionUser();
		List<SysUsers> result = new ArrayList<SysUsers>();
		// JSONArray result=new JSONArray();
		result.add(user);
		ObjectMapper om = new ObjectMapper();
		String json = "";
		try {
			json = om.writeValueAsString(result);
		} catch (IOException e) {
			log.warn("write to json string error:" + result, e);
			e.printStackTrace();
			return null;
		}
		System.out.println("in usercontroller JsonArray:" + json);
		return JSONArray.parseArray(json);
	}

	@RequestMapping(params = "loadCurrentUser")
	@ResponseBody
	public JSONObject loadUser() {
		SysUsers user = ResourceUtil.getSessionUser();

		// JSONArray result=new JSONArray();
		// result.add(user);
		ObjectMapper om = new ObjectMapper();
		String json = "";
		try {
			json = om.writeValueAsString(user);
		} catch (IOException e) {
			log.warn("write to json string error:" + user, e);
			e.printStackTrace();
			return null;
		}
		System.out.println("in usercontroller load user JsonObject:" + json);
		return JSONObject.parseObject(json);
	}

	@RequestMapping(params = "modifyUser")
	@ResponseBody
	public String modifyUser(String username, String email, String password) {
		SysUsers user = ResourceUtil.getSessionUser();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		loginService.updateEntity(user);
		return "个人信息修改成功";

	}

	@RequestMapping(params = "addWeixin")
	@ResponseBody
	public String addWeixin(WeiXinPublicUser puser) {
		SysUsers user = ResourceUtil.getSessionUser();
		puser.setUrl(WeiXinConstants.CORE_URL);
		puser.setStatus("候选");
		// puser.setToken(UUIDGenerator.generate());
		puser.setToken("weixiaokai");
		user.getPublicUserList().add(puser);
		loginService.save(puser);
		loginService.updateEntity(user);
		WeiXinPublicUser p = ResourceUtil.getWeiXinAccount();
		if (p == null)
			ResourceUtil.setWeiXinAccount(puser);
		return "公众号信息保存成功";

	}

	@RequestMapping(params = "editWeixin")
	@ResponseBody
	public String editWeixin(HttpServletRequest req)
			throws UnsupportedEncodingException {
		String rows = req.getParameter("data");
		System.out.println("in user controller editweixin:" + rows);
		rows = java.net.URLDecoder.decode(rows, "utf-8");
		System.out.println("in user controller editweixin:" + rows);
		List<WeiXinPublicUser> rowl = JSONArray.parseArray(rows,
				WeiXinPublicUser.class);
		WeiXinPublicUser pUser = rowl.get(0);
		SysUsers user = ResourceUtil.getSessionUser();
		WeiXinPublicUser p = ResourceUtil.getWeiXinAccount();
		pUser.setUser(user);
		if (pUser.getStatus().equals("默认")) {
			ResourceUtil.setWeiXinAccount(pUser);
			p.setStatus("候选");
			weiXinService.updateEntity(p);
		}
		weiXinService.updateEntity(pUser);
		return "公众号信息修改成功";

	}

	@RequestMapping(params = "loadWeixin")
	@ResponseBody
	public JSONArray loadWeixin() {

		SysUsers user = ResourceUtil.getSessionUser();
		if (user == null)
			return null;

		List<WeiXinPublicUser> result = loginService.findByProperty(
				WeiXinPublicUser.class, "user.id", user.getId());
		System.out.println("in usercontroller load weixn JsonArray user is:"
				+ user.getId());
		System.out
				.println("in usercontroller load weixn JsonArray result.size():"
						+ result.size());
		ObjectMapper om = new ObjectMapper();
		String json = "";
		try {
			json = om.writeValueAsString(result);
		} catch (IOException e) {
			log.warn("write to json string error:" + result, e);
			e.printStackTrace();
			return null;
		}
		System.out.println("in usercontroller load weixn JsonArray:" + json);
		return JSONArray.parseArray(json);

	}

	@RequestMapping(params = "selectDefault")
	@ResponseBody
	public String selectDefaultWeiXin(HttpServletRequest request) {

		SysUsers user = ResourceUtil.getSessionUser();
		String Jsonrow = URLDecoder.decode(request.getParameter("data"));

		WeiXinPublicUser puser = JSON.parseObject(Jsonrow,
				WeiXinPublicUser.class);
		puser.setStatus("默认");
		puser.setUser(user);
		HttpSession session = ContextHolderUtils.getSession();
		session.setAttribute(WeiXinConstants.WEIXIN_ACCOUNT, puser);

		List<WeiXinPublicUser> list = user.getPublicUserList();
		for (WeiXinPublicUser pu : list) {
			if (!pu.getId().equals(puser.getId())) {
				pu.setStatus("候选");
				loginService.saveOrUpdate(pu);
			}
		}
		loginService.saveOrUpdate(puser);
		return "设置成功";
	}
}
