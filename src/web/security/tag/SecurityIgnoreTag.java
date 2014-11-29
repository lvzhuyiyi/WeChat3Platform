package web.security.tag;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import web.core.util.ContextHolderUtils;
import web.security.handler.AuthorityRank;
import web.system.entity.SysAuthorities;
import web.system.entity.SysUsers;
import web.system.manager.Client;
import web.system.manager.ClientManager;

public class SecurityIgnoreTag extends BaseTag {
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
			System.out.println("ignore tag authorities:" + authority);
			SysUsers user = client.getUser();
			List<SysAuthorities> aus = user.getAuthorities();
			System.out.println("in ignore tag user authorities:" + aus);
			boolean allBelow = true;
			for (SysAuthorities au : aus) {
				int r = AuthorityRank.compare(au, authority);
				if (r > 0) {
					allBelow = false;
					return SKIP_BODY;
				} else if (r == 0) {
					allBelow = false;
				}
			}
			if (allBelow)
				return SKIP_BODY;
			return EVAL_BODY_INCLUDE;
		}
	}
}
