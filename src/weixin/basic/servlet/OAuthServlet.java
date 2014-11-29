package weixin.basic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import weixin.basic.constants.MyConstants;
import weixin.basic.pojo.SNSUserInfo;
import weixin.basic.pojo.WeiXinOauth2Token;
import weixin.basic.util.AdvIntUtil;

@Controller
@RequestMapping("/authServlet")
public class OAuthServlet {
	private static final long serialVersionUID = -1847238807216447030L;

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");
		String code = request.getParameter("code");
		if (!"authdeny".equals(code)) {
			WeiXinOauth2Token weixinOauth2Token = AdvIntUtil
					.getOauth2AccessToken(MyConstants.APPID,
							MyConstants.APPSECRET, code);
			String accessToken = weixinOauth2Token.getAccessToken();
			String openId = weixinOauth2Token.getOpenId();
			SNSUserInfo snsu = AdvIntUtil.getSNSUserInfo(accessToken, openId);
			request.setAttribute("snsUserInfo", snsu);
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
