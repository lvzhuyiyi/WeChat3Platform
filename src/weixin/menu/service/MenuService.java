package weixin.menu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import web.common.service.CommonService;

@Service("menuService")
@Transactional(propagation = Propagation.REQUIRED)
public class MenuService extends CommonService {

}
