package web.security.handler;

import java.util.HashMap;
import java.util.Map;

import web.system.entity.SysAuthorities;

public class AuthorityRank {
	public static final Map<String, Integer> authRank;
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_SERVICE = "ROLE_SERVICE";
	public static final String ROLE_VIP2 = "ROLE_VIP2";
	public static final String ROLE_VIP = "ROLE_VIP";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_GUEST = "ROLE_GUEST";
	static {
		authRank = new HashMap<String, Integer>();
		authRank.put("ROLE_ADMIN", 0);
		authRank.put("ROLE_SERVICE", 1);
		authRank.put("ROLE_VIP2", 2);
		authRank.put("ROLE_VIP", 3);
		authRank.put("ROLE_USER", 4);
		authRank.put("ROLE_GUEST", 5);
	}

	public static int compare(SysAuthorities sa1, SysAuthorities sa2) {
		try {
			Integer rank1 = authRank.get(sa1.getAuthority_name());
			Integer rank2 = authRank.get(sa2.getAuthority_name());

			return rank2 - rank1;
		} catch (NullPointerException e) {
			System.err.println("权限名非法！");
			return 2;
		}
	}

	public static int compare(String sa1, String sa2) {
		Integer rank1 = authRank.get(sa1);
		Integer rank2 = authRank.get(sa2);
		return rank2 - rank1;
	}

	public static int compare(SysAuthorities sa1, String sa2) {
		try {
			Integer rank1 = authRank.get(sa1.getAuthority_name());
			Integer rank2 = authRank.get(sa2);

			return rank2 - rank1;
		} catch (NullPointerException e) {
			System.err.println("权限名非法！");
			return 2;
		}
	}
}
