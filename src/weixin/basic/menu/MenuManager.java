package weixin.basic.menu;

import java.util.Arrays;

import weixin.basic.constants.MyConstants;
import weixin.basic.pojo.AccessToken;
import weixin.basic.util.WeixinUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MenuManager {
	public static JSONObject getMenuJson(String appId, String appSecret) {

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		JSONObject result = null;
		if (null != at) {

			result = JSON.parseObject(WeixinUtil.getMenu(at.getToken())
					.toString());

		}
		return result;
	}

	public static JSONObject createMenu(Menu menu, String appId,
			String appSecret) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		JSONObject result = null;
		if (null != at) {

			// 调用接口创建菜单
			result = JSON.parseObject(WeixinUtil
					.createMenu(menu, at.getToken()).toString());

		}
		return result;

	}

	public static JSONObject deleteMenu(String appId, String appSecret) {
		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
		JSONObject result = null;
		if (null != at) {

			// 调用接口创建菜单
			result = JSON.parseObject(WeixinUtil.deleteMenu(at.getToken())
					.toString());

		}
		return result;

	}

	private static Menu getDiyMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("天气预报");
		btn11.setType("click");
		btn11.setKey("11");

		ClickButton btn12 = new ClickButton();
		btn12.setName("公交查询");
		btn12.setType("click");
		btn12.setKey("12");

		ClickButton btn13 = new ClickButton();
		btn13.setName("周边搜索");
		btn13.setType("click");
		btn13.setKey("13");

		ClickButton btn14 = new ClickButton();
		btn14.setName("历史上的今天");
		btn14.setType("click");
		btn14.setKey("14");

		ClickButton btn15 = new ClickButton();
		btn15.setName("电影排行榜");
		btn15.setType("click");
		btn15.setKey("32");

		ClickButton btn21 = new ClickButton();
		btn21.setName("歌曲点播");
		btn21.setType("click");
		btn21.setKey("21");

		ClickButton btn22 = new ClickButton();
		btn22.setName("经典游戏");
		btn22.setType("click");
		btn22.setKey("22");

		ClickButton btn23 = new ClickButton();
		btn23.setName("美女电台");
		btn23.setType("click");
		btn23.setKey("23");

		ClickButton btn24 = new ClickButton();
		btn24.setName("人脸识别");
		btn24.setType("click");
		btn24.setKey("24");

		ClickButton btn25 = new ClickButton();
		btn25.setName("聊天唠嗑");
		btn25.setType("click");
		btn25.setKey("25");

		ClickButton btn31 = new ClickButton();
		btn31.setName("Q友圈");
		btn31.setType("click");
		btn31.setKey("31");

		ClickButton btn33 = new ClickButton();
		btn33.setName("幽默笑话");
		btn33.setType("click");
		btn33.setKey("33");

		ClickButton btn34 = new ClickButton();
		btn34.setName("用户反馈");
		btn34.setType("click");
		btn34.setKey("34");

		ClickButton btn35 = new ClickButton();
		btn35.setName("关于我们");
		btn35.setType("click");
		btn35.setKey("35");

		ViewButton btn32 = new ViewButton();
		btn32.setName("使用帮助");
		btn32.setType("view");
		btn32.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("生活助手");
		mainBtn1.setSub_button(Arrays.asList(new Button[] { btn11, btn12,
				btn13, btn14, btn15 }));

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("休闲驿站");
		mainBtn2.setSub_button(Arrays.asList(new Button[] { btn21, btn22,
				btn23, btn24, btn25 }));

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多");
		mainBtn3.setSub_button(Arrays.asList(new Button[] { btn31, btn33,
				btn34, btn35, btn32 }));

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(Arrays.asList(new Button[] { mainBtn1, mainBtn2,
				mainBtn3 }));

		return menu;
	}

	public static void main(String[] args) {
		createMenu(getDiyMenu(), MyConstants.APPID, MyConstants.APPSECRET);
	}
}
