package web.login.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.common.service.CommonService;
import web.core.util.ContextHolderUtils;
import web.system.entity.SysAuthorities;
import web.system.entity.SysUsers;
import web.system.manager.Client;
import web.system.manager.ClientManager;

@Service("loginService")
@Transactional
public class LoginService extends CommonService {

	public boolean checkExist(SysUsers user) {
		String hql = "from SysUsers u where u.username='%s' AND u.password='%s'";
		List<SysUsers> re = findByQueryString(String.format(hql,
				user.getUsername(), user.getPassword()));
		return re.size() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<SysAuthorities> loadUserAuthorities(String username) {
		String sql = "SELECT * FROM sys_authorities WHERE AUTHORITY_ID IN( "
				+ "SELECT DISTINCT authority_id FROM sys_roles_authorities  S1 "
				+ "JOIN sys_users_roles S2 ON S1.role_id = S2.role_id "
				+ "JOIN sys_users S3 ON S3.ID = S2.user_id AND S3.username=?)";

		Query query = getSession().createSQLQuery(sql).addEntity(
				SysAuthorities.class);
		query.setParameter(0, username);

		List<SysAuthorities> list = query.list();
		for (SysAuthorities au : list)
			System.out.println(au);
		return list;
	}

	public Collection<SysAuthorities> loadSessionUserAuthority()
			throws UsernameNotFoundException {

		List<SysAuthorities> auths = new ArrayList<SysAuthorities>();

		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SysUsers user = null;

		if (client != null) {
			user = client.getUser();

			// 得到用户的权限
			auths = loadUserAuthorities(user.getUsername());

			user.setAuthorities(auths);

			System.out.println("********************************************");
			System.out.println(user.getAuthorities());
			System.out
					.println("********************************************************");
		}

		return auths;
	}
}
