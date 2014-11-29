package weixin.massdeliver.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.core.util.ContextHolderUtils;
import web.core.util.ResourceUtil;
import web.system.entity.WeiXinPublicUser;
import weixin.basic.pojo.GroupArticle;
import weixin.basic.pojo.WeiXinGroup;
import weixin.basic.pojo.WeiXinMedia;
import weixin.basic.util.AdvIntUtil;
import weixin.basic.util.WeixinUtil;
import weixin.template.entity.BaseTemplate;
import weixin.template.entity.NewsTemplate;
import weixin.template.entity.TextTemplate;
import weixin.template.service.TemplateService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/massDeliverController")
public class MassDeliverController {
	@Autowired
	private TemplateService templateService;

	@RequestMapping(params = "loadNo")
	@ResponseBody
	private JSONArray loadNo() {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		if (pUser == null)
			return JSON.parseArray("[]");
		List<BaseTemplate> templates = templateService.findByProperty(
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

	@RequestMapping(params = "loadGroup")
	@ResponseBody
	private JSONArray loadGroup() {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		if (pUser == null)
			return JSON.parseArray("[]");
		HttpSession session = ContextHolderUtils.getSession();
		List<WeiXinGroup> groups = null;

		while (true) {
			if (session.getAttribute("groups") != null) {
				groups = (List<WeiXinGroup>) session.getAttribute("groups");
				break;
			}
		}
		JSONArray array = new JSONArray();
		for (WeiXinGroup group : groups) {

			JSONObject json = new JSONObject();
			json.put("value", group.getId());
			json.put("text", group.getName() + "(" + group.getCount() + "人)");
			array.add(json);
		}
		return array;
	}

	@RequestMapping(params = "massDeliver")
	@ResponseBody
	private String massDeliver(@RequestParam("no") int no,
			@RequestParam("id") int id) {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		if (pUser == null)
			return "你还未添加公众号!";
		BaseTemplate bt = (BaseTemplate) templateService.findUniqueByProperty(
				BaseTemplate.class, "no", no);
		if (bt instanceof TextTemplate) {
			TextTemplate tt = (TextTemplate) bt;
			String jsonMsg = AdvIntUtil.makeGTextMassMessage(id,
					tt.getContent());
			AdvIntUtil.sendGroupMassMessage(getAccessToken(), jsonMsg);
		} else if (bt instanceof NewsTemplate) {
			NewsTemplate nt = (NewsTemplate) bt;
			WeiXinMedia thumb_media = AdvIntUtil.uploadMedia(getAccessToken(),
					"thumb", nt.getPicUrl());
			List<GroupArticle> articles = new ArrayList<GroupArticle>();
			GroupArticle article = new GroupArticle();
			article.setAuthor(pUser.getName());
			article.setContent(nt.getMainContent());
			article.setContent_source_url(nt.getPageUrl());
			article.setDigest(nt.getIntroduce());
			article.setShowCoverPic(1);
			article.setThumb_media_id(thumb_media.getMediaId());
			article.setTitle(nt.getTitle());
			articles.add(article);
			String jsonMsg = AdvIntUtil.makeGNewsMassMessage(id,
					getAccessToken(), articles);
			AdvIntUtil.sendUsersMassMessage(getAccessToken(), jsonMsg);
			// String jsonMsg
		}
		return "群发消息修改成功";
	}

	private String getAccessToken() {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		if (pUser == null)
			return null;
		String accessToken = pUser.getAccessToken();
		if (accessToken == null) {
			accessToken = WeixinUtil.getAccessToken(pUser.getAppId(),
					pUser.getAppSecret()).getToken();
			pUser.setAccessToken(accessToken);
			ResourceUtil.setWeiXinAccount(pUser);
		}
		System.out.println("accessToken:" + accessToken);
		return accessToken;
	}
}
