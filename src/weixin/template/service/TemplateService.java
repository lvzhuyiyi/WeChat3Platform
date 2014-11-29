package weixin.template.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import web.common.service.CommonService;

@Service("templateService")
@Transactional(propagation = Propagation.REQUIRED)
public class TemplateService extends CommonService {

}
