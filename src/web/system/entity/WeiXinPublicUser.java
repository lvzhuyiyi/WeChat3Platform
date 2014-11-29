package web.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import weixin.menu.entity.MButton;
import weixin.subscribe.entity.SubscribeResponse;
import weixin.template.entity.BaseTemplate;
import weixin.template.entity.DefaultText;

/**
 * @Title: Entity
 * @Description: 微信公众帐号信息
 * @author onlineGenerator
 * @date 2014-05-21 00:53:47
 * @version V1.0
 * 
 */

@Entity
@Table(name = "weixin_account")
@SuppressWarnings("serial")
public class WeiXinPublicUser implements java.io.Serializable {
	/** 主键 */
	private java.lang.String id;
	/** 公众帐号名称 */
	private java.lang.String name;
	/** 公众帐号TOKEN */
	private java.lang.String token;
	/** 公众帐号url */
	private java.lang.String url;
	/** 公众微信号 */
	private java.lang.String weiXinNo;
	/** 公众原始ID */
	private java.lang.String originId;
	/** 公众号类型 */
	private java.lang.String type;
	/** 电子邮箱 */
	private java.lang.String email;
	/** 公众帐号描述 */
	private java.lang.String desc;
	/** 公众帐号APPID */
	private java.lang.String appId;
	/** 公众帐号APPSECRET */
	private java.lang.String appSecret;
	/** ACCESS_TOKEN */
	private java.lang.String accessToken;
	/** TOKEN获取时间 */
	private java.util.Date tokenTime;
	/** 所属系统用户 **/
	private SysUsers user;
	private String status;
	// 公共账号资源
	private List<BaseTemplate> templates = new ArrayList<BaseTemplate>();
	private SubscribeResponse subscribeResponse;
	private List<MButton> mButtons = new ArrayList<MButton>();
	private DefaultText defaultText;

	@JsonIgnore
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "pUser")
	public DefaultText getDefaultText() {
		return defaultText;
	}

	public void setDefaultText(DefaultText defaultText) {
		this.defaultText = defaultText;
	}

	public String getStatus() {
		return status;
	}

	@JsonIgnore
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "pUser")
	public SubscribeResponse getSubscribeResponse() {
		return subscribeResponse;
	}

	public void setSubscribeResponse(SubscribeResponse subscribeResponse) {
		this.subscribeResponse = subscribeResponse;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "pUser")
	public List<MButton> getmButtons() {
		return mButtons;
	}

	public void setmButtons(List<MButton> mButtons) {
		this.mButtons = mButtons;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 主键
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众帐号名称
	 */
	@Column(name = "WEIXINNAME", nullable = true, length = 200)
	public java.lang.String getName() {
		return this.name;
	}

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "pUser")
	public List<BaseTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<BaseTemplate> templates) {
		this.templates = templates;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众帐号名称
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众帐号TOKEN
	 */
	@Column(name = "TOKEN", nullable = true, length = 200)
	public java.lang.String getToken() {
		return this.token;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众帐号TOKEN
	 */
	public void setToken(java.lang.String accounttoken) {
		this.token = accounttoken;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众微信号
	 */
	@Column(name = "ACCOUNTNUMBER", nullable = true, length = 200)
	public java.lang.String getWeiXinNo() {
		return this.weiXinNo;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众微信号
	 */
	public void setWeiXinNo(java.lang.String accountnumber) {
		this.weiXinNo = accountnumber;
	}

	public java.lang.String getOriginId() {
		return originId;
	}

	public void setOriginId(java.lang.String weixin_accountid) {
		this.originId = weixin_accountid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众号类型
	 */
	@Column(name = "WEIXINTYPE", nullable = true, length = 50)
	public java.lang.String getType() {
		return this.type;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众号类型
	 */
	public void setType(java.lang.String accounttype) {
		this.type = accounttype;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 电子邮箱
	 */
	@Column(name = "EMAIL", nullable = true, length = 200)
	public java.lang.String getEmail() {
		return this.email;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 电子邮箱
	 */
	public void setEmail(java.lang.String accountemail) {
		this.email = accountemail;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众帐号描述
	 */
	@Column(name = "WEIXINDESC", nullable = true, length = 500)
	public java.lang.String getDesc() {
		return this.desc;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众帐号描述
	 */
	public void setDesc(java.lang.String accountdesc) {
		this.desc = accountdesc;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众帐号APPID
	 */
	@Column(name = "APPID", nullable = true, length = 200)
	public java.lang.String getAppId() {
		return this.appId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众帐号APPID
	 */
	public void setAppId(java.lang.String accountappid) {
		this.appId = accountappid;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 公众帐号APPSECRET
	 */
	@Column(name = "APPSECRET", nullable = true, length = 500)
	public java.lang.String getAppSecret() {
		return this.appSecret;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 公众帐号APPSECRET
	 */
	public void setAppSecret(java.lang.String appsecret) {
		this.appSecret = appsecret;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String ACCESS_TOKEN
	 */
	@JsonIgnore
	@Column(name = "ACCESSTOKEN", nullable = true, length = 1000)
	public java.lang.String getAccessToken() {
		return this.accessToken;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String ACCESS_TOKEN
	 */
	public void setAccessToken(java.lang.String accountaccesstoken) {
		this.accessToken = accountaccesstoken;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date TOKEN获取时间
	 */
	@JsonIgnore
	@Column(name = "TOEKNTIME", nullable = true, length = 100)
	@Temporal(TemporalType.TIMESTAMP)
	public java.util.Date getTokenTime() {
		return this.tokenTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date TOKEN获取时间
	 */
	public void setTokenTime(java.util.Date addtoekntime) {
		this.tokenTime = addtoekntime;
	}

	/*
	 * @OneToMany, @ManyToMany要标注集合属性
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JsonIgnore
	public SysUsers getUser() {
		return user;
	}

	public void setUser(SysUsers user) {
		this.user = user;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
