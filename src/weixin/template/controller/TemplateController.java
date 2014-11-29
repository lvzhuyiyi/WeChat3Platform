package weixin.template.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.servlet.URLDecoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import web.core.util.ContextHolderUtils;
import web.core.util.Hanyu;
import web.core.util.ResourceUtil;
import web.core.util.WeiXinConstants;
import web.system.entity.WeiXinPublicUser;
import weixin.freemarker.engine.TemplateContext;
import weixin.template.entity.DefaultText;
import weixin.template.entity.NewsTemplate;
import weixin.template.entity.TextTemplate;
import weixin.template.service.TemplateService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/templateController")
public class TemplateController {
	@Autowired
	private TemplateContext templateContext;

	@Autowired
	private TemplateService templateService;

	@RequestMapping(params = "addNewsTemplate")
	@ResponseBody
	public String addNewsTemplate(NewsTemplate nt,
			@RequestParam("pic") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		nt.setNo(null);
		WeiXinPublicUser puser = ResourceUtil.getWeiXinAccount();
		String vPath = ResourceUtil.getBasePath(request);
		// 注意vpath不要作为getrealpath参数，getRealPath参数是相对web根目录的相对路径
		String path = request.getSession().getServletContext()
				.getRealPath("upload/");
		System.out.println("开始上传 realpath=" + path);
		System.out.println("开始上传 vpath=" + vPath);
		Hanyu hy = new Hanyu();
		String fileName = hy.getStringPinYin(puser.getName()) + "-"
				+ file.getOriginalFilename();
		// String fileName = URLEncoder.encode(puser.getName(), "utf-8") + "-"
		// + file.getOriginalFilename();
		File targetFile = new File(path, fileName);
		// File targetFile = new File(vPath + "upload/", fileName);
		nt.setPicUrl(vPath + "upload/" + fileName);
		nt.setDate(new Date());
		nt.setpUser((WeiXinPublicUser) ContextHolderUtils.getSession()
				.getAttribute(WeiXinConstants.WEIXIN_ACCOUNT));
		/*
		 * if (!targetFile.exists()) { targetFile.mkdirs(); }
		 */
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("nt.getMainContent():------------\n"
				+ nt.getMainContent());
		if (nt.getPageUrl() == null || nt.getPageUrl().trim().equals("")) {
			Map<String, String> root = new HashMap<String, String>();
			String content = nt.getMainContent();
			StringBuffer buffer = new StringBuffer();
			buffer.append(content);
			Pattern p = Pattern
					.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
			Matcher m = p.matcher(content);
			int count = 0;
			String tPath = vPath.substring(0, vPath.length() - 1);
			while (m.find()) {
				if (m.group(1).indexOf("http://") == -1) {
					buffer.insert(m.start(1) + count * tPath.length(), tPath);
					count++;
				}
			}
			System.out.println(buffer.toString());
			root.put("title", nt.getTitle());
			root.put("desc", nt.getIntroduce());
			root.put("content", buffer.toString());
			nt.setMainContent(buffer.toString());
			String hPath = request.getSession().getServletContext()
					.getRealPath("template/html/");
			System.out.println("hPath:" + hPath);

			String htmlName = hy.getStringPinYin(puser.getName()) + "-"
					+ hy.getStringPinYin(nt.getTitle()) + "-"
					+ DateFormat.getDateTimeInstance().format(nt.getDate())
					+ ".html";
			htmlName = htmlName.replaceAll(" |:|,", "-");
			templateContext.generateArticleIndex(root, hPath + "/" + htmlName);
			nt.setPageUrl(vPath + "template/html/" + htmlName);
		}
		templateService.save(nt);
		return "图文模板保存成功！";
	}

	@RequestMapping(params = "editNewsTemplate")
	@ResponseBody
	public String editNewsTemplate(NewsTemplate nt,
			@RequestParam("pic") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WeiXinPublicUser puser = ResourceUtil.getWeiXinAccount();
		System.out.println("nt.getNo():" + nt.getNo());
		NewsTemplate n = (NewsTemplate) templateService.findByProperty(
				NewsTemplate.class, "no", nt.getNo()).get(0);
		String vPath = ResourceUtil.getBasePath(request);
		if (!file.isEmpty()) {
			File f = new File(n.getPicUrl());
			f.delete();
			String path = request.getSession().getServletContext()
					.getRealPath("upload/");
			System.out.println("开始重新上传 realpath=" + path);
			System.out.println("开始重新上传 vpath=" + vPath);
			Hanyu hy = new Hanyu();
			String fileName = hy.getStringPinYin(puser.getName()) + "-"
					+ file.getOriginalFilename();
			File targetFile = new File(path, fileName);
			n.setPicUrl(vPath + "upload/" + fileName);
			/*
			 * if (!targetFile.exists()) { targetFile.mkdirs(); }
			 */
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		n.setDate(new Date());
		if (nt.getPageUrl() == null || nt.getPageUrl().trim().equals("")) {
			File f = new File(n.getPageUrl());
			f.delete();
			String oHtmlPath = n.getPageUrl();
			String vHtmlPath = oHtmlPath.substring(0,
					oHtmlPath.lastIndexOf('/') + 1);
			System.out.println(" vHtmlPath:" + vHtmlPath);
			vHtmlPath = vHtmlPath.substring(vPath.length());
			String rHtmlPath = request.getSession().getServletContext()
					.getRealPath(vHtmlPath);
			File oFile = new File(rHtmlPath, oHtmlPath.substring(oHtmlPath
					.lastIndexOf('/') + 1));
			if (oFile.exists())
				oFile.delete();
			System.out.println("----------\n" + oHtmlPath + "\n" + vHtmlPath
					+ "\n" + rHtmlPath);
			String content = nt.getMainContent();
			StringBuffer buffer = new StringBuffer();
			buffer.append(content);
			Pattern p = Pattern
					.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");// <img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
			Matcher m = p.matcher(content);
			int count = 0;
			String tPath = vPath.substring(0, vPath.length() - 1);
			System.out.println("=------tpath:" + tPath);
			while (m.find()) {
				if (m.group(1).indexOf("http://") == -1) {
					buffer.insert(m.start(1) + count * tPath.length(), tPath);
					count++;
				}
			}
			System.out.println(buffer.toString());
			Map<String, String> root = new HashMap<String, String>();
			root.put("title", nt.getTitle());
			root.put("desc", nt.getIntroduce());
			root.put("content", buffer.toString());
			nt.setMainContent(buffer.toString());
			String hPath = request.getSession().getServletContext()
					.getRealPath("template/html/");
			System.out.println("hPath:" + hPath);
			Hanyu hy = new Hanyu();
			String htmlName = hy.getStringPinYin(puser.getName()) + "-"
					+ hy.getStringPinYin(nt.getTitle()) + "-"
					+ DateFormat.getDateTimeInstance().format(n.getDate())
					+ ".html";
			htmlName = htmlName.replaceAll(" |:|,", "-");
			templateContext.generateArticleIndex(root, hPath + "/" + htmlName);
			n.setPageUrl(vPath + "template/html/" + htmlName);
		}
		n.setIntroduce(nt.getIntroduce());
		n.setMainContent(nt.getMainContent());
		n.setTitle(nt.getTitle());
		n.setWord(nt.getWord());
		templateService.saveOrUpdate(n);
		return "图文模板修改成功！";
	}

	@RequestMapping(params = "removeNewsTemplate")
	@ResponseBody
	public String removeNewsTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String odata = request.getParameter("data");
		String json = URLDecoder.decode(odata);
		NewsTemplate nt = JSON.parseObject(json, NewsTemplate.class);
		File f1 = new File(nt.getPicUrl());
		File f2 = new File(nt.getPageUrl());
		f1.delete();
		f2.delete();
		try {
			templateService.delete(nt);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return "请先删除模板引用！";
		}
		return "图文模板删除成功！";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "editTextTemplate")
	@ResponseBody
	public String editTextTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		// 获取编辑数据 这里获取到的是json字符串
		String deleted = request.getParameter("deleted");
		String inserted = request.getParameter("inserted");
		String updated = request.getParameter("updated");
		if (deleted != null) {
			// 把json字符串转换成对象
			List<TextTemplate> listDeleted = JSONArray.parseArray(
					URLDecoder.decode(deleted), TextTemplate.class);
			// TODO 下面就可以根据转换后的对象进行相应的操作了
			for (TextTemplate tt : listDeleted) {
				// tt无需先持久化
				try {
					templateService.delete(tt);
				} catch (ConstraintViolationException e) {
					e.printStackTrace();
					return "请先删除模板引用！";
				}
			}
		}

		if (inserted != null) {
			// 把json字符串转换成对象
			List<TextTemplate> listInserted = JSONArray.parseArray(
					URLDecoder.decode(inserted), TextTemplate.class);
			for (TextTemplate tt : listInserted) {
				tt.setDate(new Date());
				tt.setpUser((WeiXinPublicUser) ContextHolderUtils.getSession()
						.getAttribute(WeiXinConstants.WEIXIN_ACCOUNT));
				// tt无需先持久化
				templateService.save(tt);
			}
		}

		if (updated != null) {
			// 把json字符串转换成对象
			List<TextTemplate> listUpdated = JSONArray.parseArray(
					URLDecoder.decode(inserted), TextTemplate.class);
			for (TextTemplate tt : listUpdated) {
				// tt无需先持久化
				tt.setDate(new Date());
				tt.setpUser((WeiXinPublicUser) ContextHolderUtils.getSession()
						.getAttribute(WeiXinConstants.WEIXIN_ACCOUNT));

				System.out.println("tt.getWord():" + tt.getNo());
				templateService.updateEntity(tt);
			}
		}

		return "文本模板保存成功！";
	}

	@RequestMapping(params = "loadTextTemplate")
	@ResponseBody
	public JSONArray loadTextTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WeiXinPublicUser puser = ResourceUtil.getWeiXinAccount();
		List<TextTemplate> templates = templateService.findByProperty(
				TextTemplate.class, "pUser.id", puser.getId());
		ObjectMapper om = new ObjectMapper();
		String json = "";
		try {
			json = om.writeValueAsString(templates);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return JSONArray.parseArray(json);
	}

	@RequestMapping(params = "loadNewsTemplate")
	@ResponseBody
	public JSONArray loadNewsTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WeiXinPublicUser puser = ResourceUtil.getWeiXinAccount();
		List<NewsTemplate> templates = templateService.findByProperty(
				NewsTemplate.class, "pUser.id", puser.getId());
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		String json = "";
		try {
			json = om.writeValueAsString(templates);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return JSONArray.parseArray(json);
	}

	@RequestMapping(params = "addDefault")
	@ResponseBody
	public String addDefaultText(@RequestParam("name") String word)
			throws Exception {
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		DefaultText dt = (DefaultText) templateService.findByProperty(
				DefaultText.class, "pUser.id", pUser.getId());
		dt.setDefaultText(word);
		templateService.updateEntity(dt);
		return dt.getDefaultText();
	}

	@RequestMapping(params = "loadDefault")
	@ResponseBody
	public JSONObject loadDefaultText() throws Exception {
		JSONObject json = new JSONObject();
		WeiXinPublicUser pUser = ResourceUtil.getWeiXinAccount();
		List<DefaultText> dts = templateService.findByProperty(
				DefaultText.class, "pUser.id", pUser.getId());
		if (dts.size() > 0) {
			DefaultText dt = dts.get(0);
			json.put("word", dt.getDefaultText());
		}
		return json;
	}
}
