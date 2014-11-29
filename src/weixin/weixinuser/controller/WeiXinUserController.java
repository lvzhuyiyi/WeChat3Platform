package weixin.weixinuser.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import web.core.util.ContextHolderUtils;
import web.core.util.DateUtils;
import web.core.util.ResourceUtil;
import web.login.service.LoginService;
import web.system.entity.WeiXinPublicUser;
import weixin.basic.pojo.WeiXinGroup;
import weixin.basic.pojo.WeiXinUser;
import weixin.basic.pojo.WeiXinUserList;
import weixin.basic.util.AdvIntUtil;
import weixin.basic.util.WeixinUtil;
import weixin.weixinuser.entity.UserNumRecord;
import weixin.weixinuser.entity.YesterdaySubscribeRecord;
import weixin.weixinuser.entity.YesterdayUNSubscribeRecord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/weiXinUserController")
public class WeiXinUserController {
	@Autowired
	private LoginService loginService;

	@RequestMapping(params = "loadRemoteGroup")
	@ResponseBody
	private synchronized JSONArray loadRemoteGroup() {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return JSON.parseArray("[]");
		List<WeiXinGroup> groups = AdvIntUtil.getGroups(accessToken);
		if (groups == null)
			groups = new ArrayList<WeiXinGroup>();
		HttpSession session = ContextHolderUtils.getSession();
		session.setAttribute("groups", groups);
		notifyAll();
		return JSON.parseArray(JSON.toJSONString(groups));
	}

	@RequestMapping(params = "addGroup")
	@ResponseBody
	private String addGroup(@RequestParam("name") String name) {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return "你未添加公众号或者微信服务器连接失败！";
		WeiXinGroup group = AdvIntUtil.createGroup(accessToken, name);
		if (group == null)
			return "连接服务器失败";
		return JSON.toJSONString(group);
	}

	@RequestMapping(params = "editGroup")
	@ResponseBody
	private String editGroup(@RequestParam("name") String name,
			@RequestParam("id") int id) {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return "你未添加公众号或者微信服务器连接失败！";
		boolean b = AdvIntUtil.updateGroup(accessToken, id, name);
		if (!b)
			return "修改失败";
		return "修改成功";
	}

	@RequestMapping(params = "moveUserToGroup")
	@ResponseBody
	private String moveUserToGroup(@RequestParam("openId") String openId,
			@RequestParam("id") int id) {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return "你未添加公众号或者微信服务器连接失败！";
		boolean b = AdvIntUtil.updateMemberGroup(accessToken, openId, id);
		if (!b)
			return "修改失败";
		return "修改成功";
	}

	@RequestMapping(params = "query")
	@ResponseBody
	private synchronized JSONArray queryUser() throws JsonGenerationException,
			JsonMappingException, IOException {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return JSON.parseArray("[]");
		WeiXinUserList uList = AdvIntUtil.getUserList(accessToken, null);
		HttpSession session = ContextHolderUtils.getSession();
		session.setAttribute("openIds", uList.getOpenIdList());
		notifyAll();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = mapper.writeValueAsString(uList);
		JSONArray array = new JSONArray();
		array.add(JSON.parseObject(jsonStr));
		return array;
	}

	private final Lock lock = new ReentrantLock();
	private final Lock lock1 = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private final Condition condition1 = lock1.newCondition();

	@RequestMapping(params = "loadWeiXinUser")
	@ResponseBody
	private synchronized JSONArray loadWeiXinUser() throws InterruptedException {
		String accessToken = getAccessToken();
		if (accessToken == null)
			return JSON.parseArray("[]");
		HttpSession session = ContextHolderUtils.getSession();
		while (session.getAttribute("openIds") == null)
			wait();
		List<String> openIds = (List<String>) session.getAttribute("openIds");
		List<WeiXinUser> users = new ArrayList<WeiXinUser>();
		for (String openId : openIds) {
			WeiXinUser user = AdvIntUtil.getUserInfo(accessToken, openId);
			int id = AdvIntUtil.queryGroup(accessToken, openId);
			user.setGroupId(id);
			while (session.getAttribute("groups") == null)
				wait();
			List<WeiXinGroup> groups = (List<WeiXinGroup>) session
					.getAttribute("groups");
			for (WeiXinGroup group : groups) {
				if (group.getId() == id) {
					user.setGroupName(group.getName());
				}
			}
			users.add(user);
		}
		session.setAttribute("users", users);
		notifyAll();
		session.setAttribute("usernum", users.size());
		notifyAll();
		return JSON.parseArray(JSON.toJSONString(users));
	}

	@RequestMapping(params = "countSex")
	@ResponseBody
	private synchronized JSONArray countSex() throws InterruptedException,
			IOException {
		HttpSession session = ContextHolderUtils.getSession();
		while (session.getAttribute("users") == null)
			wait();
		List<WeiXinUser> users = (List<WeiXinUser>) session
				.getAttribute("users");
		int size = users.size();
		int male = 0, female = 0, unknown = 0;
		for (WeiXinUser user : users) {
			int sex = user.getSex();
			switch (sex) {
			case 0:
				unknown++;
				break;
			case 1:
				male++;
				break;
			case 2:
				female++;
				break;
			}
		}
		JsonFactory factory = new JsonFactory();
		StringWriter writer = new StringWriter();
		JsonGenerator g = factory.createJsonGenerator(writer);
		g.writeStartArray();
		g.writeStartArray();
		g.writeString("男");
		g.writeNumber(((double) male) / size);
		g.writeEndArray();
		g.writeStartArray();
		g.writeString("女");
		g.writeNumber(((double) female) / size);
		g.writeEndArray();
		g.writeStartArray();
		g.writeString("未知");
		g.writeNumber(((double) unknown) / size);
		g.writeEndArray();
		g.writeEndArray();
		g.close();
		return JSON.parseArray(writer.toString());
	}

	@RequestMapping(params = "countLan")
	@ResponseBody
	private synchronized JSONArray countLanguage() throws InterruptedException,
			IOException {
		HttpSession session = ContextHolderUtils.getSession();
		while (session.getAttribute("users") == null)
			wait();
		List<WeiXinUser> users = (List<WeiXinUser>) session
				.getAttribute("users");
		int size = users.size();
		int[] lan = { 0, 0, 0, 0, 0, 0 };
		for (WeiXinUser user : users) {
			String language = user.getLanguage();
			switch (language) {
			case "zh_CN":
				lan[0]++;
				break;
			case "zh_TW":
				lan[1]++;
				break;
			case "zh_HK":
				lan[2]++;
				break;
			case "en_UK":
			case "en_US":
				lan[3]++;
				break;
			case "fr-fr":
				lan[4]++;
				break;
			default:
				lan[5]++;
				break;
			}
		}

		return JSON.parseArray(JSON.toJSONString(lan));
	}

	@RequestMapping(params = "countProvince")
	@ResponseBody
	private synchronized JSONArray countProvince() throws InterruptedException,
			IOException {
		HttpSession session = ContextHolderUtils.getSession();
		while (session.getAttribute("users") == null)
			wait();
		List<WeiXinUser> users = (List<WeiXinUser>) session
				.getAttribute("users");
		int size = users.size();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (WeiXinUser user : users) {
			String province = user.getProvince();
			switch (province) {
			case "北京":
				if (map.get("北京") == null) {
					map.put("北京", 1);
				} else {
					map.put("北京", map.get("北京") + 1);
				}
				break;
			case "天津":
				if (map.get("天津") == null) {
					map.put("天津", 1);
				} else {
					map.put("天津", map.get("天津") + 1);
				}
				break;
			case "上海":
				if (map.get("上海") == null) {
					map.put("上海", 1);
				} else {
					map.put("上海", map.get("上海") + 1);
				}
				break;
			case "浙江":
				if (map.get("浙江") == null) {
					map.put("浙江", 1);
				} else {
					map.put("浙江", map.get("浙江") + 1);
				}
				break;
			case "安徽":
				if (map.get("安徽") == null) {
					map.put("安徽", 1);
				} else {
					map.put("安徽", map.get("安徽") + 1);
				}
				break;
			case "福建":
				if (map.get("福建") == null) {
					map.put("福建", 1);
				} else {
					map.put("福建", map.get("福建") + 1);
				}
				break;
			case "重庆":
				if (map.get("重庆") == null) {
					map.put("重庆", 1);
				} else {
					map.put("重庆", map.get("重庆") + 1);
				}
				break;
			case "江西":
				if (map.get("江西") == null) {
					map.put("江西", 1);
				} else {
					map.put("江西", map.get("江西") + 1);
				}
				break;
			case "香港":
				if (map.get("香港") == null) {
					map.put("香港", 1);
				} else {
					map.put("香港", map.get("香港") + 1);
				}
				break;
			case "山东":
				if (map.get("山东") == null) {
					map.put("山东", 1);
				} else {
					map.put("山东", map.get("山东") + 1);
				}
				break;
			case "澳门":
				if (map.get("澳门") == null) {
					map.put("澳门", 1);
				} else {
					map.put("澳门", map.get("澳门") + 1);
				}
				break;
			case "河南":
				if (map.get("河南") == null) {
					map.put("河南", 1);
				} else {
					map.put("河南", map.get("河南") + 1);
				}
				break;
			case "内蒙古":
				if (map.get("内蒙古") == null) {
					map.put("内蒙古", 1);
				} else {
					map.put("内蒙古", map.get("内蒙古") + 1);
				}
				break;
			case "湖北":
				if (map.get("湖北") == null) {
					map.put("湖北", 1);
				} else {
					map.put("湖北", map.get("湖北") + 1);
				}
				break;
			case "新疆":
				if (map.get("新疆") == null) {
					map.put("新疆", 1);
				} else {
					map.put("新疆", map.get("新疆") + 1);
				}
				break;
			case "湖南":
				if (map.get("湖南") == null) {
					map.put("湖南", 1);
				} else {
					map.put("湖南", map.get("湖南") + 1);
				}
				break;
			case "宁夏":
				if (map.get("宁夏") == null) {
					map.put("宁夏", 1);
				} else {
					map.put("宁夏", map.get("宁夏") + 1);
				}
				break;
			case "广东":
				if (map.get("广东") == null) {
					map.put("广东", 1);
				} else {
					map.put("广东", map.get("广东") + 1);
				}
				break;
			case "西藏":
				if (map.get("西藏") == null) {
					map.put("西藏", 1);
				} else {
					map.put("西藏", map.get("西藏") + 1);
				}
				break;
			case "海南":
				if (map.get("海南") == null) {
					map.put("海南", 1);
				} else {
					map.put("海南", map.get("海南") + 1);
				}
				break;
			case "广西":
				if (map.get("广西") == null) {
					map.put("广西", 1);
				} else {
					map.put("广西", map.get("广西") + 1);
				}
				break;
			case "四川":
				if (map.get("四川") == null) {
					map.put("四川", 1);
				} else {
					map.put("四川", map.get("四川") + 1);
				}
				break;
			case "河北":
				if (map.get("河北") == null) {
					map.put("河北", 1);
				} else {
					map.put("河北", map.get("河北") + 1);
				}
				break;
			case "贵州":
				if (map.get("贵州") == null) {
					map.put("贵州", 1);
				} else {
					map.put("贵州", map.get("贵州") + 1);
				}
				break;
			case "山西":
				if (map.get("山西") == null) {
					map.put("山西", 1);
				} else {
					map.put("山西", map.get("山西") + 1);
				}
				break;
			case "云南":
				if (map.get("云南") == null) {
					map.put("云南", 1);
				} else {
					map.put("云南", map.get("云南") + 1);
				}
				break;
			case "辽宁":
				if (map.get("辽宁") == null) {
					map.put("辽宁", 1);
				} else {
					map.put("辽宁", map.get("辽宁") + 1);
				}
				break;
			case "陕西":
				if (map.get("陕西") == null) {
					map.put("陕西", 1);
				} else {
					map.put("陕西", map.get("陕西") + 1);
				}
				break;
			case "吉林":
				if (map.get("吉林") == null) {
					map.put("吉林", 1);
				} else {
					map.put("吉林", map.get("吉林") + 1);
				}
				break;
			case "甘肃":
				if (map.get("甘肃") == null) {
					map.put("甘肃", 1);
				} else {
					map.put("甘肃", map.get("甘肃") + 1);
				}
				break;
			case "黑龙江":
				if (map.get("黑龙江") == null) {
					map.put("黑龙江", 1);
				} else {
					map.put("黑龙江", map.get("黑龙江") + 1);
				}
				break;
			case "青海":
				if (map.get("青海") == null) {
					map.put("青海", 1);
				} else {
					map.put("青海", map.get("青海") + 1);
				}
				break;
			case "江苏":
				if (map.get("江苏") == null) {
					map.put("江苏", 1);
				} else {
					map.put("江苏", map.get("江苏") + 1);
				}
				break;
			case "台湾":
				if (map.get("台湾") == null) {
					map.put("台湾", 1);
				} else {
					map.put("台湾", map.get("台湾") + 1);
				}
				break;
			default:
				if (map.get("未知") == null) {
					map.put("未知", 1);
				} else {
					map.put("未知", map.get("未知") + 1);
				}
				break;
			}

		}
		JsonFactory factory = new JsonFactory();
		StringWriter writer = new StringWriter();
		JsonGenerator g = factory.createJsonGenerator(writer);
		g.writeStartArray();
		for (String key : map.keySet()) {
			g.writeStartArray();
			g.writeString(key);
			g.writeNumber(((double) map.get(key)) / size);
			g.writeEndArray();
		}
		g.writeEndArray();
		g.close();
		return JSON.parseArray(writer.toString());
	}

	@RequestMapping(params = "getYSNums")
	@ResponseBody
	private JSONArray getYesterdaySubscribeNums() throws InterruptedException,
			IOException {
		lock.lock();
		try {
			WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
			HttpSession session = ContextHolderUtils.getSession();
			JSONObject json = new JSONObject();
			json.put("name", "昨日关注人数");
			json.put("value", "--");
			json.put("group", "昨日关注情况");
			JSONArray array = new JSONArray();

			if (pUser == null) {
				session.setAttribute("sub", 0);
				condition.signalAll();
				array.add(json);
				return array;
			}
			List<YesterdaySubscribeRecord> ysrl = loginService
					.findByPropertyByFieldOrder(YesterdaySubscribeRecord.class,
							"pUser.id", pUser.getId(), "date");
			Calendar today = Calendar.getInstance();
			Calendar lastDay = Calendar.getInstance();
			if (ysrl.size() == 0) {
				session.setAttribute("sub", 0);
				condition.signalAll();
				array.add(json);
				return array;
			}
			YesterdaySubscribeRecord ysr0 = ysrl.get(0);
			lastDay.setTime(ysr0.getDate());
			if (DateUtils.compareCalendar(today, lastDay)) {
				if (ysrl.size() == 1) {
					session.setAttribute("sub", 0);
					condition.signalAll();
					array.add(json);
					return array;
				} else {
					json.put("value", ysrl.get(1).getSubscribers());
					session.setAttribute("sub", ysrl.get(1).getSubscribers());
					condition.signalAll();
					array.add(json);
					return array;
				}
			} else {
				json.put("value", ysrl.get(0).getSubscribers());

				session.setAttribute("sub", ysrl.get(0).getSubscribers());
				condition.signalAll();
				array.add(json);
				return array;
			}
		} finally {
			lock.unlock();
		}
	}

	@RequestMapping(params = "getYUSNums")
	@ResponseBody
	private JSONArray getYesterdayUnSubscribeNums()
			throws InterruptedException, IOException {
		lock.lock();
		try {
			WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
			JSONObject json = new JSONObject();
			HttpSession session = ContextHolderUtils.getSession();
			json.put("name", "昨日取消关注人数:");
			json.put("value", "--");
			json.put("group", "昨日取消关注情况");
			JSONArray array = new JSONArray();
			if (pUser == null) {
				session.setAttribute("unsub", 0);
				condition.signalAll();
				array.add(json);
				return array;
			}
			List<YesterdayUNSubscribeRecord> ysrl = loginService
					.findByPropertyByFieldOrder(
							YesterdayUNSubscribeRecord.class, "pUser.id",
							pUser.getId(), "date");
			Calendar today = Calendar.getInstance();
			Calendar lastDay = Calendar.getInstance();
			if (ysrl.size() == 0) {
				session.setAttribute("unsub", 0);
				condition.signalAll();
				array.add(json);
				return array;
			}
			YesterdayUNSubscribeRecord ysr0 = ysrl.get(0);
			lastDay.setTime(ysr0.getDate());
			if (DateUtils.compareCalendar(today, lastDay)) {
				if (ysrl.size() == 1) {
					session.setAttribute("unsub", 0);
					condition.signalAll();
					array.add(json);
					return array;
				} else {
					json.put("value", ysrl.get(1).getUnSubscribers());
					session.setAttribute("unsub", ysrl.get(1)
							.getUnSubscribers());
					condition.signalAll();
					array.add(json);
					return array;
				}
			} else {
				json.put("value", ysrl.get(0).getUnSubscribers());
				session.setAttribute("unsub", ysrl.get(0).getUnSubscribers());
				condition.signalAll();
				array.add(json);
				return array;
			}
		} finally {
			lock.unlock();
		}
	}

	@RequestMapping(params = "getYASNums")
	@ResponseBody
	private JSONArray getYesterdayAggragateSubscribeNums()
			throws InterruptedException, IOException {
		lock.lock();
		try {
			HttpSession session = ContextHolderUtils.getSession();
			JSONArray array = new JSONArray();
			while (session.getAttribute("sub") == null
					|| session.getAttribute("unsub") == null) {
				condition.await();
			}
			int sub = (int) session.getAttribute("sub");
			int unsub = (int) session.getAttribute("unsub");
			JSONObject json = new JSONObject();
			json.put("name", "净关注人数:");
			json.put("value", sub - unsub);
			json.put("group", "净关注情况");
			array.add(json);
			return array;
		} finally {
			lock.unlock();
		}
	}

	@RequestMapping(params = "getTSNums")
	@ResponseBody
	private synchronized JSONArray getTotalSubscribeNums()
			throws InterruptedException, IOException {

		HttpSession session = ContextHolderUtils.getSession();
		JSONArray array = new JSONArray();
		while (session.getAttribute("usernum") == null) {
			wait();
		}
		int size = (int) session.getAttribute("usernum");
		JSONObject json = new JSONObject();
		json.put("name", "总关注人数:");
		json.put("value", size);
		json.put("group", "总关注情况");
		array.add(json);
		return array;

	}

	@RequestMapping(params = "countUsers")
	@ResponseBody
	private JSONArray countUsers() throws InterruptedException, IOException {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		if (pUser == null)
			return null;
		JSONArray array = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("name", "用户");
		List<UserNumRecord> unr = loginService.findByPropertyByFieldOrder(
				UserNumRecord.class, "pUser.id", pUser.getId(), "date");
		JSONArray array1 = new JSONArray();
		for (int i = 0; i < 7; i++) {

			array1.add(0, unr.get(i).getUsers());
		}
		object.put("data", array1);
		array.add(object);
		return array;
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