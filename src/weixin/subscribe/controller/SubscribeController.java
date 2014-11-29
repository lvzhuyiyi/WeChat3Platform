package weixin.subscribe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.core.util.ResourceUtil;
import web.system.entity.WeiXinPublicUser;
import weixin.subscribe.entity.SubscribeResponse;
import weixin.subscribe.service.SubscribeService;
import weixin.template.entity.BaseTemplate;
import weixin.template.entity.NewsTemplate;
import weixin.template.entity.TextTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller("subscribeController")
@RequestMapping("/subscribeController")
public class SubscribeController {
	@Autowired
	private SubscribeService subscribeService;

	@RequestMapping(params = "loadNo")
	@ResponseBody
	private JSONArray loadNo() {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<BaseTemplate> templates = subscribeService.findByProperty(
				BaseTemplate.class, "pUser.id", pUser.getId());
		JSONArray array = new JSONArray();
		for (BaseTemplate tp : templates) {

			JSONObject json = new JSONObject();
			json.put("value", tp.getNo());
			json.put("text", tp.getNo() + "(" + tp.getWord() + ")");
			array.add(json);
		}
		return array;
	}

	@RequestMapping(params = "selectNo")
	@ResponseBody
	private String selectNo(@RequestParam("no") int no) {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<SubscribeResponse> responses = subscribeService.findByProperty(
				SubscribeResponse.class, "pUser.id", pUser.getId());
		BaseTemplate bt = (BaseTemplate) subscribeService.findUniqueByProperty(
				BaseTemplate.class, "no", no);

		if (responses.size() == 0) {
			SubscribeResponse sr = new SubscribeResponse();
			sr.setbTemplate(bt);
			subscribeService.save(sr);
		} else {
			SubscribeResponse sr = responses.get(0);
			sr.setbTemplate(bt);
			subscribeService.updateEntity(sr);
		}
		return "回复模板修改成功";
	}

	@RequestMapping(params = "loadResponse")
	@ResponseBody
	private JSONArray loadResponse() {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<SubscribeResponse> responses = subscribeService.findByProperty(
				SubscribeResponse.class, "pUser.id", pUser.getId());
		JSONArray array = new JSONArray();
		for (SubscribeResponse tp : responses) {
			BaseTemplate bt = tp.getbTemplate();
			JSONObject json = new JSONObject();
			json.put("no", bt.getNo());
			if (bt instanceof TextTemplate) {
				TextTemplate tt = (TextTemplate) bt;
				json.put("type", "文本模板");
				json.put("content", tt.getContent());
			} else {
				NewsTemplate nt = (NewsTemplate) bt;
				json.put("type", "图文模板");
				json.put("content", nt.getMainContent());
			}

			array.add(json);
		}
		return array;
	}
}
