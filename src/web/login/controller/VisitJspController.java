package web.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.core.util.ResourceUtil;
import web.system.entity.WeiXinPublicUser;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/jspController")
public class VisitJspController {
	@RequestMapping(params = "main")
	public String toMain(HttpServletRequest request,
			HttpServletResponse response) {

		return "main/main";
	}

	@RequestMapping(params = "goMain")
	@ResponseBody
	public JSONObject checkGoMain(HttpServletRequest request,
			HttpServletResponse response) {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();

		JSONObject json = new JSONObject();
		if (pUser == null) {
			json.put("status", "failure");
		} else {
			json.put("status", "success");
		}
		return json;
	}

	@RequestMapping(params = "frame")
	public String toFrame(HttpServletRequest request,
			HttpServletResponse response) {

		return "main/frame";
	}

	@RequestMapping(params = "timeout")
	public String toTimeOut(HttpServletRequest request,
			HttpServletResponse response) {
		return "login/timeout";
	}
}
