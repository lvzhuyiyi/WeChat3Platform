package weixin.basic.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weixin.basic.constants.MyConstants;
import weixin.basic.message.response.Article;
import weixin.basic.message.response.Music;
import weixin.basic.pojo.GroupArticle;
import weixin.basic.pojo.GroupVideo;
import weixin.basic.pojo.SNSUserInfo;
import weixin.basic.pojo.WeiXinGroup;
import weixin.basic.pojo.WeiXinMedia;
import weixin.basic.pojo.WeiXinOauth2Token;
import weixin.basic.pojo.WeiXinQRCode;
import weixin.basic.pojo.WeiXinUser;
import weixin.basic.pojo.WeiXinUserList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AdvIntUtil {

	private static Logger log = LoggerFactory.getLogger(AdvIntUtil.class);
	private static ArrayList<Integer> msgids = new ArrayList<Integer>();

	public static String makeTextCustomerMessage(String openId, String content) {
		content = content.replace("\"", "\\\"");
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId, content);
	}

	public static String makeImageCustomerMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	public static String makeVoiceCustomerMessage(String openId, String mediaId) {

		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}

	public static String makeVideoCustomerMessage(String openId,
			String mediaId, String thumbMediaId) {

		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId, thumbMediaId);
	}

	public static String makeMusicCustomerMessage(String openId, Music music) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
		jsonMsg = String.format(jsonMsg, openId, JSON.toJSONString(music));
		jsonMsg = jsonMsg.replace("musicUrl", "musicurl");
		jsonMsg = jsonMsg.replace("HQMusicUrl", "hqmusicurl");
		jsonMsg = jsonMsg.replace("thumbMediaId", "thumb_media_id");
		System.out.println(jsonMsg);
		return jsonMsg;
	}

	public static String makeNewsCustomerMessage(String openId,
			List<Article> articleList) {

		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
		jsonMsg = String
				.format(jsonMsg, openId, JSON.toJSONString(articleList));
		jsonMsg = jsonMsg.replace("picUrl", "picurl");
		System.out.println(jsonMsg);
		return jsonMsg;
	}

	public static boolean sendCustomerMessage(String accessToken, String jsonMsg) {
		log.info("消息内容:{}", jsonMsg);
		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("客服消息发送失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	public static WeiXinOauth2Token getOauth2AccessToken(String appId,
			String appSecret, String code) {
		WeiXinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {

			try {
				wat = new WeiXinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInteger("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}

		}
		return wat;
	}

	public static WeiXinOauth2Token refreshOauth2AccessToken(String appid,
			String refreshToken) {
		WeiXinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appid);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {

			try {
				wat = new WeiXinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInteger("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}

		}
		return wat;
	}

	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsu = null;
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TPKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"OPENID", openId);
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {

			try {
				snsu = new SNSUserInfo();
				snsu.setOpenId(jsonObject.getString("openid"));
				snsu.setNickName(jsonObject.getString("nickname"));
				snsu.setSex(jsonObject.getInteger("sex"));
				snsu.setCountry(jsonObject.getString("country"));
				snsu.setProvince(jsonObject.getString("province"));
				snsu.setCity(jsonObject.getString("city"));
				snsu.setHeadImgUrl(jsonObject.getString("headimageurl"));
				snsu.setPrivilegeList(JSON.parseArray(
						jsonObject.getJSONArray("privilege").toJSONString(),
						String.class));
			} catch (Exception e) {
				snsu = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}

		}
		return snsu;
	}

	public static String getOAuthUrl(String redirectUri, String state)
			throws UnsupportedEncodingException {
		String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		oauthUrl = oauthUrl.replace("APPID", MyConstants.APPID);
		oauthUrl = oauthUrl.replace("REDIREC_URI",
				URLEncoder.encode(redirectUri, "utf-8"));
		oauthUrl = oauthUrl.replace("SCOPE", "snsapi_userinfo");
		oauthUrl = oauthUrl.replace("STATE", state);
		return oauthUrl;
	}

	/**
	 * 创建临时带参二维码
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param expireSeconds
	 *            二维码有效时间，单位为秒，最大不超过1800
	 * @param sceneId
	 *            场景ID
	 * @return WeixinQRCode
	 */
	public static WeiXinQRCode createTemporaryQRCode(String accessToken,
			int expireSeconds, int sceneId) {
		WeiXinQRCode weixinQRCode = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建临时带参二维码
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				String.format(jsonMsg, expireSeconds, sceneId));

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeiXinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject
						.getInteger("expire_seconds"));
				log.info("创建临时带参二维码成功 ticket:{} expire_seconds:{}",
						weixinQRCode.getTicket(),
						weixinQRCode.getExpireSeconds());
			} catch (Exception e) {
				weixinQRCode = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建临时带参二维码失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}
		return weixinQRCode;
	}

	/**
	 * 创建永久带参二维码
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param sceneId
	 *            场景ID
	 * @return ticket
	 */
	public static String createPermanentQRCode(String accessToken, int sceneId) {
		String ticket = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建永久带参二维码
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				String.format(jsonMsg, sceneId));

		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
				log.info("创建永久带参二维码成功 ticket:{}", ticket);
			} catch (Exception e) {
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建永久带参二维码失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}
		return ticket;
	}

	/**
	 * 根据ticket换取二维码
	 * 
	 * @param ticket
	 *            二维码ticket
	 * @param savePath
	 *            保存路径
	 */
	public static String getQRCode(String ticket, String savePath) {
		String filePath = null;
		// 拼接请求地址
		String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET",
				WeixinUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 将ticket作为文件名
			filePath = savePath + ticket + ".jpg";

			// 将微信服务器返回的输入流写入文件
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			log.info("根据ticket换取二维码成功，filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("根据ticket换取二维码失败：{}", e);
		}
		return filePath;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 */
	public static WeiXinUser getUserInfo(String accessToken, String openId) {
		WeiXinUser weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeiXinUser();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInteger("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject
						.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInteger("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo
						.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (RuntimeException e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
				} else {
					int errorCode = jsonObject.getInteger("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode,
							errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}

	/**
	 * 获取关注者列表
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 * @param nextOpenId
	 *            第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static WeiXinUserList getUserList(String accessToken,
			String nextOpenId) {
		WeiXinUserList weixinUserList = null;

		if (null == nextOpenId)
			nextOpenId = "";

		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"NEXT_OPENID", nextOpenId);
		// 获取关注者列表
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeiXinUserList();
				System.err.println("------jsonObject.getInteger(\"total\")--:"
						+ jsonObject.getInteger("total"));
				weixinUserList.setTotal(jsonObject.getInteger("total"));
				weixinUserList.setCount(jsonObject.getInteger("count"));
				weixinUserList.setNextOpenId(jsonObject
						.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSON.parseArray(dataObject
						.getJSONArray("openid").toJSONString(), String.class));
			} catch (NullPointerException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.format("获取用户列表失败 errcode:{%s} errmsg:{%s}",
						errorCode, errorMsg);
			}
		}
		return weixinUserList;
	}

	/**
	 * 查询分组
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public synchronized static List<WeiXinGroup> getGroups(String accessToken) {
		List<WeiXinGroup> weixinGroupList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 查询分组
		JSONObject jsonObject = WeixinUtil
				.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				System.err
						.println("----jsonObject.getJSONArray(\"groups\").toString()--"
								+ jsonObject);
				weixinGroupList = JSON.parseArray(
						jsonObject.getJSONArray("groups").toString(),
						WeiXinGroup.class);
			} catch (NullPointerException e) {
				weixinGroupList = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.format("获取分组列表失败 errcode:{%s} errmsg:{%s}\n",
						errorCode, errorMsg);
			}
		}
		return weixinGroupList;
	}

	/**
	 * 查询分组
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public synchronized static Integer queryGroup(String accessToken,
			String openId) {
		Integer id = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"openid\":\"%s\"}";
		// 查询分组
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "GET",
				String.format(jsonData, openId));

		if (null != jsonObject) {
			try {
				id = jsonObject.getInteger("groupid");
			} catch (NullPointerException e) {
				id = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.out.format("查询分组失败 errcode:{%s} errmsg:{%s}", errorCode,
						errorMsg);
			}
		}
		return id;
	}

	/**
	 * 创建分组
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param groupName
	 *            分组名称
	 * @return
	 */
	public static WeiXinGroup createGroup(String accessToken, String groupName) {
		WeiXinGroup weixinGroup = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\" : {\"name\" : \"%s\"}}";
		// 创建分组
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				String.format(jsonData, groupName));

		if (null != jsonObject) {
			try {
				weixinGroup = new WeiXinGroup();
				weixinGroup.setId(jsonObject.getJSONObject("group").getInteger(
						"id"));
				weixinGroup.setName(jsonObject.getJSONObject("group")
						.getString("name"));
			} catch (NullPointerException e) {
				weixinGroup = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				System.err.println("创建分组失败 errcode:{} errmsg:{}" + errorCode
						+ errorMsg);
			}
		}
		return weixinGroup;
	}

	/**
	 * 修改分组名
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param groupId
	 *            分组id
	 * @param groupName
	 *            修改后的分组名
	 * @return true | false
	 */
	public static boolean updateGroup(String accessToken, int groupId,
			String groupName) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
		// 修改分组名
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				String.format(jsonData, groupId, groupName));

		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("修改分组名成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("修改分组名失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	/**
	 * 移动用户分组
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @param groupId
	 *            分组id
	 * @return true | false
	 */
	public static boolean updateMemberGroup(String accessToken, String openId,
			int groupId) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
		// 移动用户分组
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				String.format(jsonData, openId, groupId));

		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				System.out.format("移动用户分组成功 errcode:{%s} errmsg:{%s}",
						errorCode, errorMsg);
			} else {
				System.out.format("移动用户分组失败 errcode:{%s} errmsg:{%s}",
						errorCode, errorMsg);
			}
		}
		return result;
	}

	/**
	 * 上传媒体文件
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param type
	 *            媒体文件类型（image、voice、video和thumb）
	 * @param mediaFileUrl
	 *            媒体文件的url
	 */
	public static WeiXinMedia uploadMedia(String accessToken, String type,
			String mediaFileUrl) {
		WeiXinMedia weixinMedia = null;
		// 拼装请求地址
		String uploadMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken)
				.replace("TYPE", type);

		// 定义数据分隔符
		String boundary = "------------7da2e536604c8";
		try {
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl
					.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();
			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl
					.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");

			// 从请求头中获取内容类型
			String contentType = meidaConn.getHeaderField("Content-Type");
			// 根据内容类型判断文件扩展名
			String fileExt = WeixinUtil.getFileExt(contentType);
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream
					.write(String
							.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n",
									fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n",
					contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(
					meidaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// 将媒体文件写到输出流（往微信服务器写数据）
				outputStream.write(buf, 0, size);
				System.out.println(new String(buf));
			}
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();

			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSON.parseObject(buffer.toString());
			weixinMedia = new WeiXinMedia();
			weixinMedia.setType(jsonObject.getString("type"));
			// type等于thumb时的返回结果和其它类型不一样
			if ("thumb".equals(type))
				weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			else
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
			weixinMedia.setCreatedAt(jsonObject.getInteger("created_at"));
		} catch (Exception e) {
			weixinMedia = null;
			e.printStackTrace();
			log.error("上传媒体文件失败：{}", e);
		}
		return weixinMedia;
	}

	/**
	 * 下载媒体文件
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param mediaId
	 *            媒体文件标识
	 * @param savePath
	 *            文件在服务器上的存储路径
	 * @return
	 */
	public static String getMedia(String accessToken, String mediaId,
			String savePath) {
		String filePath = null;
		// 拼接请求地址
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace(
				"MEDIA_ID", mediaId);
		System.out.println(requestUrl);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 根据内容类型获取扩展名
			String fileExt = WeixinUtil.getFileExt(conn
					.getHeaderField("Content-Type"));
			// 将mediaId作为文件名
			filePath = savePath + mediaId + fileExt;

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			log.info("下载媒体文件成功，filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("下载媒体文件失败：{}", e);
		}
		return filePath;
	}

	public static WeiXinMedia uploadNews(String accessToken,
			List<GroupArticle> articleList) {
		WeiXinMedia result = null;
		String jsonMsg = makeNews(articleList);
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			try {
				result.setType(jsonObject.getString("type"));
				result.setMediaId(jsonObject.getString("media_id"));
				result.setCreatedAt(jsonObject.getInteger("created_at"));

			} catch (Exception e) {
				System.out.println(e.getMessage());
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("客服消息发送失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	public static String makeNews(List<GroupArticle> articleList) {

		String jsonMsg = "{\"articles\":%s}";
		return String.format(jsonMsg, JSON.toJSONString(articleList));
	}

	public static WeiXinMedia uploadGroupVideo(String accessToken, GroupVideo gv) {
		WeiXinMedia result = null;
		String jsonMsg = JSONObject.toJSONString(gv);
		String requestUrl = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			try {
				result.setType(jsonObject.getString("type"));
				result.setMediaId(jsonObject.getString("media_id"));
				result.setCreatedAt(jsonObject.getInteger("created_at"));

			} catch (Exception e) {
				System.out.println(e.getMessage());
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("客服消息发送失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	public static String makeGTextMassMessage(int groupid, String context) {
		String jsonMsg = "{\"filter\":{\"group_id\":\"%d\"},\"text\":{\"content\":\"%s\"},\"msgtype\":\"text\"}";
		jsonMsg = String.format(jsonMsg, groupid, context);
		System.out.println("makeGTextMassMessage:" + jsonMsg);
		return jsonMsg;
	}

	public static String makeGNewsMassMessage(int groupid, String accessToken,
			List<GroupArticle> articleList) {
		WeiXinMedia wm = uploadNews(accessToken, articleList);
		String jsonMsg = "{\"filter\":{\"group_id\":\"%d\"},\"mpnews\":{\"media_id\":\"%s\"},\"msgtype\":\"mpnews\"}";
		jsonMsg = String.format(jsonMsg, groupid, wm.getMediaId());
		System.out.println("makeGNewsMassMessage:" + jsonMsg);
		return jsonMsg;
	}

	public static String makeGVoiceMassMessage(int groupid, String mediaid) {

		String jsonMsg = "{\"filter\":{\"group_id\":\"%d\"},\"voice\":{\"media_id\":\"%s\"},\"msgtype\":\"voice\"}";
		return String.format(jsonMsg, groupid, mediaid);
	}

	public static String makeGPicMassMessage(int groupid, String mediaid) {

		String jsonMsg = "{\"filter\":{\"group_id\":\"%d\"},\"image\":{\"media_id\":\"%s\"},\"msgtype\":\"image\"}";
		return String.format(jsonMsg, groupid, mediaid);
	}

	public static String makeGVideoMassMessage(int groupid, String accessToken,
			GroupVideo gv) {
		WeiXinMedia wm = uploadGroupVideo(accessToken, gv);
		String jsonMsg = "{\"filter\":{\"group_id\":\"%d\"},\"mpvideo\":{\"media_id\":\"%s\"},\"msgtype\":\"mpvideo\"}";
		return String.format(jsonMsg, groupid, wm.getMediaId());
	}

	public static boolean sendGroupMassMessage(String accessToken,
			String jsonMsg) {

		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				JSONObject jso = JSON.parseObject(jsonMsg);
				if (jso.getString("msgtype").equals("mpnews")
						|| jso.getString("msgtype").equals("mpvideo"))
					msgids.add(jsonObject.getInteger("msg_id"));
				System.out.format("群发消息发送成功 errcode:{%s} errmsg:{%s}\n",
						errorCode, errorMsg);
				log.info("群发消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				System.out.format("群发消息发送失败errcode:{%s} errmsg:{%s}\n",
						errorCode, errorMsg);
			}
		}
		return result;
	}

	public static String makeUTextMassMessage(List<String> users, String context) {
		String jsonMsg = "{\"touser\":%s,\"text\":{\"content\":\"%s\"},\"msgtype\":\"text\"}";
		return String.format(jsonMsg, JSON.toJSONString(users), context);
	}

	public static String makeUNewsMassMessage(List<String> users,
			String accessToken, List<GroupArticle> articleList) {
		WeiXinMedia wm = uploadNews(accessToken, articleList);
		String jsonMsg = "{\"touser\":%s,\"mpnews\":{\"media_id\":\"%s\"},\"msgtype\":\"mpnews\"}";
		return String
				.format(jsonMsg, JSON.toJSONString(users), wm.getMediaId());
	}

	public static String makeUVoiceMassMessage(List<String> users,
			String mediaid) {

		String jsonMsg = "{\"touser\":%s,\"voice\":{\"media_id\":\"%s\"},\"msgtype\":\"voice\"}";
		return String.format(jsonMsg, JSON.toJSONString(users), mediaid);
	}

	public static String makeUPicMassMessage(List<String> users, String mediaid) {

		String jsonMsg = "{\"touser\":%s,\"image\":{\"media_id\":\"%s\"},\"msgtype\":\"image\"}";
		return String.format(jsonMsg, JSON.toJSONString(users), mediaid);
	}

	public static String makeUVideoMassMessage(List<String> users,
			String accessToken, GroupVideo gv) {
		WeiXinMedia wm = uploadGroupVideo(accessToken, gv);
		String jsonMsg = "{\"touser\":%s,\"mpvideo\":{\"media_id\":\"%s\"},\"msgtype\":\"mpvideo\"}";
		return String
				.format(jsonMsg, JSON.toJSONString(users), wm.getMediaId());
	}

	public static boolean sendUsersMassMessage(String accessToken,
			String jsonMsg) {

		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				JSONObject jso = JSON.parseObject(jsonMsg);
				if (jso.getString("msgtype").equals("mpnews")
						|| jso.getString("msgtype").equals("mpvideo"))
					msgids.add(jsonObject.getInteger("msg_id"));
				System.out.println("客服消息发送成功 errcode:{} errmsg:{}" + errorCode
						+ ":" + errorMsg);
				log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("客服消息发送失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	public static boolean deleteMessage(String accessToken, String msgid) {
		boolean result = false;
		String jsonMsg = String.format("{\"msg_id\":%d}",
				Integer.parseInt(msgid));
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = WeixinUtil.httpsRequest(requestUrl, "POST",
				jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getInteger("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				msgids.remove(Integer.parseInt(msgid));
				log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("客服消息发送失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}

	public static void test() {

		// 获取接口访问凭证
		String accessToken = WeixinUtil.getAccessToken("APPID", "APPSECRET")
				.getToken();

		/**
		 * 发送客服消息（文本消息）
		 */
		// 组装文本客服消息
		String jsonTextMsg = makeTextCustomerMessage(
				"oEdzejiHCDqafJbz4WNJtWTMbDcE",
				"点击查看<a href=\"http://blog.csdn.net/lyq8479\">柳峰的博客</a>");
		// 发送客服消息
		sendCustomerMessage(accessToken, jsonTextMsg);

		/**
		 * 发送客服消息（图文消息）
		 */
		Article article1 = new Article();
		article1.setTitle("微信上也能斗地主");
		article1.setDescription("");
		article1.setPicUrl("http://www.egouji.com/xiaoq/game/doudizhu_big.png");
		article1.setUrl("http://resource.duopao.com/duopao/games/small_games/weixingame/Doudizhu/doudizhu.htm");
		Article article2 = new Article();
		article2.setTitle("傲气雄鹰\n80后不得不玩的经典游戏");
		article2.setDescription("");
		article2.setPicUrl("http://www.egouji.com/xiaoq/game/aoqixiongying.png");
		article2.setUrl("http://resource.duopao.com/duopao/games/small_games/weixingame/Plane/aoqixiongying.html");
		List<Article> list = new ArrayList<Article>();
		list.add(article1);
		list.add(article2);
		// 组装图文客服消息
		String jsonNewsMsg = makeNewsCustomerMessage(
				"oEdzejiHCDqafJbz4WNJtWTMbDcE", list);
		// 发送客服消息
		sendCustomerMessage(accessToken, jsonNewsMsg);

		/**
		 * 创建临时二维码
		 */
		WeiXinQRCode weixinQRCode = createTemporaryQRCode(accessToken, 900,
				111111);
		// 临时二维码的ticket
		System.out.println(weixinQRCode.getTicket());
		// 临时二维码的有效时间
		System.out.println(weixinQRCode.getExpireSeconds());

		/**
		 * 根据ticket换取二维码
		 */
		String ticket = "gQEg7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2lIVVJ3VmJsTzFsQ0ZuQ0Y1bG5WAAIEW35+UgMEAAAAAA==";
		String savePath = "G:/download";
		// 根据ticket换取二维码
		getQRCode(ticket, savePath);

		/**
		 * 获取用户信息
		 */
		WeiXinUser user = getUserInfo(accessToken,
				"oNr2vjgcPp4Rhuo9MRiPqW6MpzCo");
		System.out.println("OpenID：" + user.getOpenId());
		System.out.println("关注状态：" + user.getSubscribe());
		System.out.println("关注时间：" + user.getSubscribeTime());
		System.out.println("昵称：" + user.getNickname());
		System.out.println("性别：" + user.getSex());
		System.out.println("国家：" + user.getCountry());
		System.out.println("省份：" + user.getProvince());
		System.out.println("城市：" + user.getCity());
		System.out.println("语言：" + user.getLanguage());
		System.out.println("头像：" + user.getHeadImgUrl());

		/**
		 * 查询分组
		 */
		List<WeiXinGroup> groupList = getGroups(accessToken);
		// 循环输出各分组信息
		for (WeiXinGroup group : groupList) {
			System.out.println(String.format("ID：%d 名称：%s 用户数：%d",
					group.getId(), group.getName(), group.getCount()));
		}

		/**
		 * 创建分组
		 */
		WeiXinGroup group = createGroup(accessToken, "公司员工");
		System.out.println(String.format("成功创建分组：%s id：%d", group.getName(),
				group.getId()));

		/**
		 * 修改分组名
		 */
		updateGroup(accessToken, 100, "同事");

		/**
		 * 移动用户分组
		 */
		updateMemberGroup(accessToken, "oEdzejiHCDqafJbz4WNJtWTMbDcE", 100);

		/**
		 * 上传多媒体文件
		 */
		WeiXinMedia weixinMedia = uploadMedia(accessToken, "voice",
				"http://localhost:8080/weixinmpapi/test.mp3");
		System.out.println(weixinMedia.getMediaId());
		System.out.println(weixinMedia.getType());
		System.out.println(weixinMedia.getCreatedAt());

		/**
		 * 下载多媒体文件
		 */
		getMedia(
				accessToken,
				"N7xWhOGYSLWUMPzVcGnxKFbhXeD_lLT5sXxyxDGEsCzWIB2CcUijSeQOYjWLMpcn",
				"G:/download");

	}

	public static void main(String[] args) {
		/*
		 * List<GroupArticle> al=new ArrayList<GroupArticle>(); GroupArticle
		 * ga=new GroupArticle(); GroupArticle ga1=new GroupArticle();
		 * al.add(ga); al.add(ga1); System.out.println(makeNews(al));
		 */

		String accessToken = WeixinUtil.getAccessToken(MyConstants.APPID,
				MyConstants.APPSECRET).getToken();

		// OpenID列表：[oNr2vjgcPp4Rhuo9MRiPqW6MpzCo, oNr2vjjPpTof6PaAGC5Zqk2zr-8I]
		// 微信号： gh_8036805f3e34
		/*
		 * WeixinUserList weixinUserList = getUserList(accessToken, "");
		 * System.out.println("总关注用户数：" + weixinUserList.getTotal());
		 * System.out.println("本次获取用户数：" + weixinUserList.getCount());
		 * System.out.println("OpenID列表：" +
		 * weixinUserList.getOpenIdList().toString());
		 * System.out.println("next_openid：" + weixinUserList.getNextOpenId());
		 */

		/*
		 * ID：0 名称：未分组 用户数：0 ID：1 名称：黑名单 用户数：0 ID：2 名称：星标组 用户数：0 ID：100 名称：我的粉丝
		 * 用户数：2 ID：101 名称：同事 用户数：0 ID：102 名称：公司员工 用户数：0
		 */
		/*
		 * List<WeixinGroup> groupList = getGroups(accessToken);
		 * 
		 * for (WeixinGroup group : groupList) {
		 * System.out.println(String.format("ID：%d 名称：%s 用户数：%d", group.getId(),
		 * group.getName(), group.getCount())); }
		 */
		/*
		 * String jsonMsg=makeGTextMassMessage(100,"大家都是sb");
		 * System.out.println(jsonMsg);
		 * sendGroupMassMessage(accessToken,jsonMsg);
		 */

		/*
		 * List<String> users=Arrays.asList(new
		 * String[]{"oNr2vjgcPp4Rhuo9MRiPqW6MpzCo",
		 * "oNr2vjjPpTof6PaAGC5Zqk2zr-8I"}); String
		 * jsonMsg=makeUTextMassMessage( users,"哈哈哈哈哈 这是群发消息");
		 * System.out.println(jsonMsg);
		 * sendUsersMassMessage(accessToken,jsonMsg);
		 */
		/**
		 * 上传多媒体文件
		 */
		WeiXinMedia weixinMedia = uploadMedia(accessToken, "image",
				"D:/ProjectFile/wx/ccf431adcbef76092320e6ac2ddda3cc7cd99ede.jpg");
		System.out.println(weixinMedia.getMediaId());
		System.out.println(weixinMedia.getType());
		System.out.println(weixinMedia.getCreatedAt());

	}
}
