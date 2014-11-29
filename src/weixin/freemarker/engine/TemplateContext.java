package weixin.freemarker.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import web.core.util.PropertiesUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Component("templateContext")
public class TemplateContext {

	@Resource(name = "freemarkerConfig")
	private FreeMarkerConfigurer freeMarkerCongfig;

	// private Map<String, TemplateDirectiveModel> tags;

	private static final String ENCODING = "UTF-8";
	// @Resource(name = "cacheManager")
	// private static CacheManager em;// ehcache
	/**
	 * 系统模式： PUB-生产（使用ehcache） DEV-开发
	 */
	private static String _sysMode = null;
	static {
		PropertiesUtil util = new PropertiesUtil("sysConfig.properties");

	}

	public Configuration getConfiguration() {
		return freeMarkerCongfig.getConfiguration();
	}

	public Locale getLocale() {
		return getConfiguration().getLocale();
	}

	public Template getTemplate(String ftlName) {
		Template template = null;
		if (ftlName == null) {
			return null;
		}
		try {

			template = getConfiguration().getTemplate(ftlName, getLocale(),
					ENCODING);

			return template;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void generateArticleIndex(Map<String, String> root, String htmlFile)
			throws Exception {
		File f = new File(htmlFile);
		Template temp = getTemplate("index.ftl");
		Writer out = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		temp.process(root, out);
	}

	/**
	 * 从缓存中读取ftl模板
	 * 
	 * @param template
	 * @param encoding
	 * @return
	 */
	/*
	 * public Template getTemplateFromCache(String tableName, String encoding,
	 * String version) { Template template = null; Cache ehCache =
	 * em.getCache("dictCache"); try { // cache的键：类名.方法名.参数名 String cacheKey =
	 * FreemarkerHelper.class.getName() + ".getTemplateFormCache." + tableName +
	 * "." + version; Element element = ehCache.get(cacheKey); if (element ==
	 * null) { template = getConfiguration().getTemplate(tableName, getLocale(),
	 * ENCODING); element = new Element(cacheKey, template);
	 * ehCache.put(element); } else { template = (Template)
	 * element.getObjectValue(); } } catch (IOException e) {
	 * e.printStackTrace(); } return template; }
	 */
	public FreeMarkerConfigurer getFreeMarkerCongfig() {
		return freeMarkerCongfig;
	}

	public void setFreeMarkerCongfig(FreeMarkerConfigurer freeMarkerCongfig) {
		this.freeMarkerCongfig = freeMarkerCongfig;
	}

}
