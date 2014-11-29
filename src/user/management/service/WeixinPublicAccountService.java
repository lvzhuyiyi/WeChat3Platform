package user.management.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import web.common.service.CommonService;

@Service("weixinPublicAccountService")
@Transactional(propagation = Propagation.REQUIRED)
public class WeixinPublicAccountService extends CommonService {

}
