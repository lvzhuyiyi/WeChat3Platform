package web.core.util;

public class WeiXinConstants {

	/**
	 * 登录用户账号信息，加载到session缓存中
	 */
	public static final String WEIXIN_ACCOUNT = "WEIXIN_ACCOUNT";
	/**
	 * 微信商城用户登录是的openid
	 */
	public static final String USER_OPENID = "USER_OPENID";
	public static final String CORE_URL = "http://weixiaokai.duapp.com/coreservlet";
	public static final String IMAGE_URL = "http://weixiaokai.duapp.com/image/";
	public static final String PAGE_URL = "http://weixiaokai.duapp.com/template/";
	public static String defaultText = "你说的这句话我听不懂!";

	public static String getDefaultText() {
		return defaultText;
	}

	public static void setDefaultText(String defaultText) {
		WeiXinConstants.defaultText = defaultText;
	}

}
