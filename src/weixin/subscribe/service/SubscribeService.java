package weixin.subscribe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import web.common.service.CommonService;

@Service("subscribeService")
@Transactional(propagation = Propagation.REQUIRED)
public class SubscribeService extends CommonService {

}
