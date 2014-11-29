package web.security.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import web.core.util.ContextHolderUtils;
import web.system.entity.SysUsers;
import web.system.manager.Client;
import web.system.manager.ClientManager;

public class DisplayUserTag extends BaseTag {
	@Override
	public int doStartTag() throws JspException {

		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		// 返回内容为空 或者长度为0就直接忽略此标签 否则继续计算标签内容
		if (client == null) {
			print("游客");
			return SKIP_BODY;
		} else {

			SysUsers user = client.getUser();
			print(user.getUsername());
			return SKIP_BODY;
		}
	}
}
