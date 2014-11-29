package weixin.basic.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.core.util.DateUtils;
import web.core.util.Hanyu;
import web.login.service.LoginService;
import web.security.handler.AuthorityRank;
import web.system.entity.SysAuthorities;
import web.system.entity.SysUsers;
import web.system.entity.WeiXinPublicUser;
import weixin.basic.message.response.Article;
import weixin.basic.message.response.NewsMessage;
import weixin.basic.message.response.TextMessage;
import weixin.basic.util.MessageUtil;
import weixin.extend.functional.TodayInHistoryService;
import weixin.subscribe.entity.SubscribeResponse;
import weixin.template.entity.BaseTemplate;
import weixin.template.entity.DefaultText;
import weixin.template.entity.NewsTemplate;
import weixin.template.entity.TextTemplate;
import weixin.weixinuser.entity.YesterdaySubscribeRecord;
import weixin.weixinuser.entity.YesterdayUNSubscribeRecord;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-05-20
 */
@Service("coreService")
public class CoreService {
	@Autowired
	public LoginService loginService;

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respXml = null;

		try {

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			List<WeiXinPublicUser> pUsers = loginService.findByProperty(
					WeiXinPublicUser.class, "originId", toUserName);
			if (pUsers.size() > 1) {
				textMessage.setContent("请不要为不同用户添加同一个公众号！");
				respXml = MessageUtil.textMessageToXml(textMessage);
				return respXml;
			} else if (pUsers.size() == 0) {
				textMessage.setContent("您不是我们客户或未添加公众号！");
				respXml = MessageUtil.textMessageToXml(textMessage);
				return respXml;
			}
			WeiXinPublicUser pUser = pUsers.get(0);
			Boolean authentificated = false;
			SysUsers user = pUser.getUser();
			List<SysAuthorities> authorities = loginService
					.loadUserAuthorities(user.getUsername());

			for (SysAuthorities auth : authorities) {
				if (AuthorityRank.compare(auth, AuthorityRank.ROLE_VIP) >= 0) {
					authentificated = true;
				}
			}
			if (!authentificated) {
				textMessage.setContent("您还未成为VIP！");
				respXml = MessageUtil.textMessageToXml(textMessage);
				return respXml;
			}
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");

				List<BaseTemplate> templates = pUser.getTemplates();
				for (BaseTemplate tp : templates) {
					String keyword = tp.getWord();
					System.out.println("keyword:"
							+ new String(keyword.getBytes(), "utf-8"));
					Hanyu hy = new Hanyu();
					System.out
							.println("keyword:" + hy.getStringPinYin(keyword));
					if (!keyword.equals(content)
							&& content.indexOf(keyword) == -1) {
						continue;
					}
					if (tp instanceof TextTemplate) {
						TextTemplate tt = (TextTemplate) tp;
						textMessage.setContent(tt.getContent());
						respXml = MessageUtil.textMessageToXml(textMessage);
						return respXml;
					}
					if (tp instanceof NewsTemplate) {
						NewsTemplate tt = (NewsTemplate) tp;
						NewsMessage newsMessage = new NewsMessage();
						newsMessage.setToUserName(fromUserName);
						newsMessage.setFromUserName(toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage
								.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(1);
						List<Article> articles = new ArrayList<Article>();
						Article article = new Article();
						article.setDescription(tt.getIntroduce());
						article.setPicUrl(tt.getPicUrl());
						article.setTitle(tt.getTitle());
						article.setUrl(tt.getPageUrl());
						articles.add(article);
						newsMessage.setArticles(articles);
						respXml = MessageUtil.newsMessageToXml(newsMessage);
						return respXml;
					}
				}
				List<DefaultText> ldt = loginService.findByProperty(
						DefaultText.class, "pUser.id", pUser.getId());
				if (ldt.size() == 0) {
					textMessage.setContent("你说的啥？");
					respXml = MessageUtil.textMessageToXml(textMessage);
					return respXml;
				}
				DefaultText dt = ldt.get(0);
				textMessage.setContent(dt.getDefaultText());
				respXml = MessageUtil.textMessageToXml(textMessage);
			}

			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				String picurl = requestMap.get("PicUrl");

				textMessage.setContent("您发送的是图片消息！");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				String Location_X = requestMap.get("Location_X");
				String Location_Y = requestMap.get("Location_Y");
				String Scale = requestMap.get("Scale");
				String Label = requestMap.get("Label");

				textMessage.setContent("您发送的是地理位置消息！");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				String Title = requestMap.get("Title");
				String Description = requestMap.get("Description");
				String Url = requestMap.get("Url");

				textMessage.setContent("您发送的是链接消息！");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				String mediaId = requestMap.get("MediaId");
				String format = requestMap.get("Format");
				String recognition = requestMap.get("Recognition");

				textMessage.setContent("您发送的是音频消息！");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					List<YesterdaySubscribeRecord> ysrl = loginService
							.findByPropertyByFieldOrder(
									YesterdaySubscribeRecord.class, "pUser.id",
									pUser.getId(), "date");
					if (ysrl.size() == 0) {
						YesterdaySubscribeRecord ysr = new YesterdaySubscribeRecord();
						ysr.setDate(new Date());
						ysr.setSubscribers(1);
						ysr.setpUser(pUser);
						loginService.save(ysr);
					} else {
						YesterdaySubscribeRecord ysr = ysrl.get(0);
						Calendar now = Calendar.getInstance();
						Calendar then = Calendar.getInstance();
						then.setTime(ysr.getDate());
						if (ysrl.size() == 1) {
							if (DateUtils.compareCalendar(now, then)) {
								ysr.setSubscribers(ysr.getSubscribers() + 1);
								loginService.save(ysr);
							} else {
								YesterdaySubscribeRecord ysr1 = new YesterdaySubscribeRecord();
								ysr1.setDate(now.getTime());
								ysr1.setSubscribers(1);
								ysr1.setpUser(pUser);
								loginService.save(ysr1);
							}
						} else if (ysrl.size() == 2) {
							if (DateUtils.compareCalendar(now, then)) {
								ysr.setSubscribers(ysr.getSubscribers() + 1);
								loginService.save(ysr);
							} else {
								YesterdaySubscribeRecord ysr1 = ysrl.get(1);
								ysr1.setDate(now.getTime());
								ysr1.setSubscribers(1);
								loginService.save(ysr1);
							}

						} else if (ysrl.size() > 2) {

						}
						loginService.save(ysr);
					}
					SubscribeResponse sr = pUser.getSubscribeResponse();
					if (sr == null) {
						textMessage.setContent("您好！欢迎关注本公众平台 ");
						respXml = MessageUtil.textMessageToXml(textMessage);
						return respXml;
					} else {
						BaseTemplate bt = sr.getbTemplate();
						if (bt instanceof TextTemplate) {
							TextTemplate tt = (TextTemplate) bt;
							textMessage.setContent(tt.getContent());
							respXml = MessageUtil.textMessageToXml(textMessage);
							return respXml;
						} else {
							NewsTemplate nt = (NewsTemplate) bt;
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage
									.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticleCount(1);
							List<Article> articles = new ArrayList<Article>();
							Article article = new Article();
							article.setDescription(nt.getIntroduce());
							article.setPicUrl(nt.getPicUrl());
							article.setTitle(nt.getTitle());
							article.setUrl(nt.getPageUrl());
							articles.add(article);
							newsMessage.setArticles(articles);
							respXml = MessageUtil.newsMessageToXml(newsMessage);
							return respXml;
						}
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					List<YesterdayUNSubscribeRecord> ysrl = loginService
							.findByPropertyByFieldOrder(
									YesterdayUNSubscribeRecord.class,
									"pUser.id", pUser.getId(), "date");
					if (ysrl.size() == 0) {
						YesterdayUNSubscribeRecord ysr = new YesterdayUNSubscribeRecord();
						ysr.setDate(new Date());
						ysr.setUnSubscribers(1);
						ysr.setpUser(pUser);
						loginService.save(ysr);
					} else {
						YesterdayUNSubscribeRecord ysr = ysrl.get(0);
						Calendar now = Calendar.getInstance();
						Calendar then = Calendar.getInstance();
						then.setTime(ysr.getDate());
						if (ysrl.size() == 1) {
							if (DateUtils.compareCalendar(now, then)) {
								ysr.setUnSubscribers(ysr.getUnSubscribers() + 1);
								loginService.save(ysr);
							} else {
								YesterdayUNSubscribeRecord ysr1 = new YesterdayUNSubscribeRecord();
								ysr1.setDate(now.getTime());
								ysr1.setUnSubscribers(1);
								ysr1.setpUser(pUser);
								loginService.save(ysr1);
							}
						} else if (ysrl.size() == 2) {
							if (DateUtils.compareCalendar(now, then)) {
								ysr.setUnSubscribers(ysr.getUnSubscribers() + 1);
								loginService.save(ysr);
							} else {
								YesterdayUNSubscribeRecord ysr1 = ysrl.get(1);
								ysr1.setDate(now.getTime());
								ysr1.setUnSubscribers(1);
								loginService.save(ysr1);
							}
						} else if (ysrl.size() > 2) {

						}
						loginService.save(ysr);
					}
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
					textMessage.setContent("您好!你已经关注本平台 ");
					respXml = MessageUtil.textMessageToXml(textMessage);
				} else if (eventType
						.equals(MessageUtil.EVENT_TYPE_MASSSENDJOBFINISH)) {
					String totalCount = requestMap.get("TotalCount");
					String filterCount = requestMap.get("FilterCount");
					String sendCount = requestMap.get("SendCount");
					String errorCount = requestMap.get("ErrorCount");
					System.out.println("totalCount:" + totalCount
							+ "filterCount:" + filterCount + "sendCount:"
							+ sendCount + "errorCount:" + errorCount);
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");

					if (eventKey.equals("11")) {
						textMessage.setContent("天气预报菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("12")) {

						textMessage.setContent("公交查询菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("13")) {

						textMessage.setContent("周边搜索菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("14")) {

						textMessage.setContent(TodayInHistoryService
								.getTodayInHistoryInfo());
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("21")) {
						respXml = "歌曲点播菜单项被点击！";

						textMessage.setContent("歌曲点播菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("22")) {
						respXml = "经典游戏菜单项被点击！";

						textMessage.setContent("经典游戏菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("23")) {

						textMessage.setContent("美女电台菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("24")) {

						textMessage.setContent("人脸识别菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);

					} else if (eventKey.equals("25")) {

						textMessage.setContent("周边搜索菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("31")) {

						textMessage.setContent("Q友圈菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("32")) {

						textMessage.setContent("电影排行榜菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("33")) {

						textMessage.setContent("幽默笑话菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("oschina")) {
						Article article = new Article();
						article.setTitle("开源中国");
						article.setDescription("开源中国社区成立于2008年8月，是目前中国最大的开源技术社区");
						article.setPicUrl("");
						article.setUrl("http://m.oschina.net");
						List<Article> articleList = new ArrayList<Article>();

						articleList.add(article);
						NewsMessage newMessage = new NewsMessage();
						textMessage.setContent("幽默笑话菜单项被点击！");
						respXml = MessageUtil.textMessageToXml(textMessage);
					}
				}
			} else {

				textMessage.setContent("你的消息已经收到！");
				respXml = MessageUtil.textMessageToXml(textMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respXml;
	}

}