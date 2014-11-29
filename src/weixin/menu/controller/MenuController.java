package weixin.menu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.servlet.URLDecoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import web.core.util.ContextHolderUtils;
import web.core.util.ResourceUtil;
import web.core.util.WeiXinConstants;
import web.login.service.LoginService;
import web.system.entity.WeiXinPublicUser;
import weixin.basic.menu.ClickButton;
import weixin.basic.menu.ComplexButton;
import weixin.basic.menu.Menu;
import weixin.basic.menu.MenuManager;
import weixin.basic.menu.ViewButton;
import weixin.menu.entity.MButton;
import weixin.menu.entity.MMenuWrapper;
import weixin.menu.service.MenuService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller("menuController")
@RequestMapping("/menuController")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private LoginService loginService;

	@RequestMapping(params = "saveMenu")
	@ResponseBody
	public String saveMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		// 获取编辑数据 这里获取到的是json字符串
		String deleted = request.getParameter("deleted");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		if (deleted != null) {
			// 把json字符串转换成对象
			List<MButton> listDeleted = JSON.parseArray(
					URLDecoder.decode(deleted), MButton.class);
			// TODO 下面就可以根据转换后的对象进行相应的操作了
			for (MButton tt : listDeleted) {

				menuService.delete(tt);

			}
		}

		if (inserted != null) {
			// 把json字符串转换成对象
			List<MButton> listInserted = JSON.parseArray(
					URLDecoder.decode(inserted), MButton.class);
			for (MButton mb : listInserted) {

				mb.setpUser((WeiXinPublicUser) ContextHolderUtils.getSession()
						.getAttribute(WeiXinConstants.WEIXIN_ACCOUNT));
				System.out.println("mb.getType()" + mb.getType());
				menuService.save(mb);
			}
		}

		if (updated != null) {
			// 把json字符串转换成对象
			List<MButton> listUpdated = JSON.parseArray(
					URLDecoder.decode(updated), MButton.class);
			for (MButton mb : listUpdated) {
				// tt无需先持久化
				mb.setpUser((WeiXinPublicUser) ContextHolderUtils.getSession()
						.getAttribute(WeiXinConstants.WEIXIN_ACCOUNT));
				System.out.println("in updated mb.getType()" + mb.getType());
				menuService.updateEntity(mb);
			}
		}

		return "菜单设计保存成功！";
	}

	/*
	 * {"menu":{"button":[{"name":"生活助手","sub_button":[{"name":"天气预报","sub_button"
	 * :[],
	 * "type":"click","key":"11"},{"name":"公交查询","sub_button":[],"type":"click"
	 * ,"key":"12"},
	 * {"name":"周边搜索","sub_button":[],"type":"click","key":"13"},{"name"
	 * :"历史上的今天",
	 * "sub_button":[],"type":"click","key":"14"},{"name":"电影排行榜","sub_button"
	 * :[], "type":"click","key":"32"}]},{"name":"休闲驿站",
	 * "sub_button":[{"name":"歌曲点播","sub_button":[],"type":"click","key":"21"},
	 * {"name":"经典游戏","sub_button":[],"type":"click","key":"22"},
	 * {"name":"美女电台","sub_button":[],"type":"click","key":"23"},
	 * {"name":"人脸识别","sub_button":[],"type":"click","key":"24"},
	 * {"name":"聊天唠嗑","sub_button":[],"type":"click","key":"25"}]},
	 * {"name":"更多",
	 * "sub_button":[{"name":"Q友圈","sub_button":[],"type":"click","key"
	 * :"31"},{"name"
	 * :"幽默笑话","sub_button":[],"type":"click","key":"33"},{"name":"用户反馈"
	 * ,"sub_button"
	 * :[],"type":"click","key":"34"},{"name":"关于我们","sub_button":[]
	 * ,"type":"click"
	 * ,"key":"35"},{"name":"使用帮助","sub_button":[],"type":"view","url"
	 * :"http://liufeng.gotoip2.com/xiaoqrobot/help.jsp"}]}]}}
	 */

	@RequestMapping(params = "getMenu")
	@ResponseBody
	public JSONArray getMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WeiXinPublicUser user = ResourceUtil.getWeiXinAccount();
		JSONObject json = MenuManager.getMenuJson(user.getAppId(),
				user.getAppSecret());
		menuService.deleteByHql("delete MButton b where pUser.id=?",
				user.getId());

		if (json == null || !json.getBoolean("status")) {
			return JSON.parseArray("[]");
		} else {
			System.out.println("json.getString(\"msg\")"
					+ json.getString("msg"));
			// Menu menu = JSON.parseObject(json.getString("msg"), Menu.class);
			ObjectMapper mapper = new ObjectMapper();
			MMenuWrapper menuWrapper = mapper.readValue(json.getString("msg"),
					MMenuWrapper.class);
			List<MButton> buttons = menuWrapper.getMenu().getButton();
			List<MButton> results = new ArrayList<MButton>();
			for (MButton b : buttons) {
				if (b.getSub_button().size() > 0) {
					b.setType("复合类型");
					b.setpUser(user);
					menuService.saveOrUpdate(b);
					results.add(b);
					for (MButton sb : b.getSub_button()) {
						if (sb.getType().equals("click"))
							sb.setType("点击类型");
						if (sb.getType().equals("view"))
							sb.setType("链接类型");
						sb.setPname(b.getName());
						sb.setpUser(user);
						menuService.saveOrUpdate(sb);
						results.add(sb);
					}
				} else if (b.getType() == null) {
					b.setType("复合类型");
					b.setpUser(user);
					menuService.saveOrUpdate(b);
					results.add(b);
				} else if (b.getType().equals("click")) {
					b.setType("点击类型");
					b.setpUser(user);
					menuService.saveOrUpdate(b);
					results.add(b);
				} else if (b.getType().equals("view")) {
					b.setType("链接类型");
					b.setpUser(user);
					menuService.saveOrUpdate(b);
					results.add(b);
				}
			}
			return JSON.parseArray(JSON.toJSONString(results));
		}

	}

	@RequestMapping(params = "loadDiyMenu")
	@ResponseBody
	public JSONArray loadDiyMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<MButton> buttons = menuService.findByProperty(MButton.class,
				"pUser.id", pUser.getId());
		// List<MButton> buttons = pUser.getmButtons();
		System.out.println("buttons.size()" + buttons.size());
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(buttons);
		return JSON.parseArray(json);
	}

	@RequestMapping(params = "deleteMenu")
	@ResponseBody
	public String deleteMenu() throws Exception {
		WeiXinPublicUser user = ResourceUtil.getWeiXinAccount();
		JSONObject json = MenuManager.deleteMenu(user.getAppId(),
				user.getAppSecret());
		if (0 == json.getInteger("errcode")) {
			return "删除菜单成功！";
		} else {
			return "删除菜单失败！原因是：" + json.getString("errmsg");
		}
	}

	@RequestMapping(params = "generateMenu")
	@ResponseBody
	public String generateMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		deleteMenu();
		String errmsg = "";
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<MButton> buttons = menuService.findByProperty(MButton.class,
				"pUser.id", pUser.getId());
		// List<MButton> buttons = puser.getmButtons();
		List<MButton> mainButtons = new ArrayList<MButton>();
		Menu menu = new Menu();
		// 遍历按钮 建立树形关系
		for (MButton mb : buttons) {
			if (mb.getPname() != null && !mb.getPname().trim().equals("")
					&& !mb.getPname().trim().equals("未定义")) {
				MButton parent = find(buttons, mb.getPname());
				if (parent == null) {
					errmsg += mb.getName() + "父菜单项不存在 <br>";
				}
				parent.getSub_button().add(mb);
				mb.setParentButton(parent);
			} else {
				mainButtons.add(mb);
			}
		}
		if (mainButtons.size() > 3) {
			errmsg += "主菜单不能超过3个！<br>";
		}
		// 从主菜单出发 把按钮转化为Button
		for (MButton mb : mainButtons) {
			if (mb.getType().equals("complex") || mb.getType().equals("复合类型")) {
				ComplexButton cb = new ComplexButton();
				cb.setName(mb.getName());
				List<MButton> mButtons = mb.getSub_button();
				if (mButtons.size() > 5) {
					errmsg += mb.getName() + "子菜单不能超过5个！<br>";
				}
				for (MButton smb : mButtons) {
					if (smb.getType().equals("click")
							|| smb.getType().equals("点击类型")) {
						ClickButton clb = new ClickButton();
						clb.setName(smb.getName());
						clb.setType("click");
						clb.setKey(smb.getKey());
						cb.getSub_button().add(clb);
					} else if (smb.getType().equals("view")
							|| smb.getType().equals("链接类型")) {
						ViewButton vb = new ViewButton();
						vb.setName(smb.getName());
						vb.setType("view");
						vb.setUrl(smb.getUrl());
						cb.getSub_button().add(vb);
					} else {
						errmsg += smb.getType() + "菜单项类型非法 <br>";
					}
					menu.getButton().add(cb);
				}

			} else if (mb.getType().equals("click")
					|| mb.getType().equals("点击类型")) {
				ClickButton clb = new ClickButton();
				clb.setName(mb.getName());
				clb.setType("click");
				clb.setKey(mb.getKey());
				menu.getButton().add(clb);
			} else if (mb.getType().equals("view")
					|| mb.getType().equals("链接类型")) {
				ViewButton vb = new ViewButton();
				vb.setName(mb.getName());
				vb.setType("view");
				vb.setUrl(mb.getUrl());
				menu.getButton().add(vb);
			} else {
				errmsg += mb.getType() + "菜单项类型非法 <br>";
			}

		}

		// 调用接口生成菜单
		WeiXinPublicUser user = ResourceUtil.getWeiXinAccount();
		JSONObject json = MenuManager.createMenu(menu, user.getAppId(),
				user.getAppSecret());
		if (0 == json.getInteger("errcode")) {
			errmsg += "创建菜单成功<br>";
		} else {
			errmsg += json.getString("errmsg");
		}
		return errmsg;
	}

	private MButton find(List<MButton> buttons, String pname) {
		for (MButton button : buttons) {
			if (button.getName().equals(pname))
				return button;
		}
		return null;
	}

}
