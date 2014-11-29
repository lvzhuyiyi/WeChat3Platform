package web.security.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import web.core.util.ContextHolderUtils;
import web.system.entity.SysAuthorities;
import web.system.entity.SysUsers;
import web.system.manager.Client;
import web.system.manager.ClientManager;

public class SecurityCheckTag extends BaseTag {
	String authority;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String s) {
		authority = s;
	}

	@Override
	public int doStartTag() throws JspException {

		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		// 返回内容为空 或者长度为0就直接忽略此标签 否则继续计算标签内容
		if (client == null)
			return SKIP_BODY;
		else {
			System.out.println("tag authorities:" + authority);
			SysUsers user = client.getUser();
			List<String> result = new ArrayList<String>();
			for (SysAuthorities au : user.getAuthorities()) {

				result.add(au.getAuthority_name());
				// System.out.println("in jstl tag user authorities:" + au);
			}
			if (!result.contains(authority)) {
				// System.out.println("!result.contains(authority)"
				// + !result.contains(authority));
				return SKIP_BODY;
			} else
				return EVAL_BODY_INCLUDE;
		}
	}

}
