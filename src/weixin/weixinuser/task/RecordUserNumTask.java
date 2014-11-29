package weixin.weixinuser.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.login.service.LoginService;
import web.system.entity.WeiXinPublicUser;
import weixin.basic.pojo.WeiXinUserList;
import weixin.basic.util.AdvIntUtil;
import weixin.basic.util.WeixinUtil;
import weixin.weixinuser.entity.UserNumRecord;

@Service("recordUserNumTask")
public class RecordUserNumTask {
	@Autowired
	private LoginService loginService;

	public void recordUserNum() {
		List<WeiXinPublicUser> pUsers = loginService
				.loadAll(WeiXinPublicUser.class);
		System.out.println("---------------------------------");
		System.out.println("in RecordUserTask1");
		System.out.println("---------------------------------");
		for (WeiXinPublicUser pUser : pUsers) {
			String accessToken = WeixinUtil.getAccessToken(pUser.getAppId(),
					pUser.getAppSecret()).getToken();
			WeiXinUserList uList = AdvIntUtil.getUserList(accessToken, null);
			List<UserNumRecord> records = loginService
					.findByPropertyByFieldAscOrder(UserNumRecord.class,
							"pUser.id", pUser.getId(), "date");
			if (records.size() > 0) {
				UserNumRecord unr = records.get(0);
				unr.setDate(new Date());
				unr.setUsers(uList.getOpenIdList().size());
				loginService.saveOrUpdate(unr);
			}
			uList.getOpenIdList();
		}
		System.out.println("---------------------------------");
		System.out.println("2in RecordUserTask1\2");
		System.out.println("---------------------------------");
	}
}
