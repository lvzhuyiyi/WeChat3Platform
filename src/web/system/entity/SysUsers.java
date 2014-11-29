package web.system.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import web.common.entity.IdEntity;

/**
 * 系统用户类表
 * 
 * @author
 */
@Entity
@Table(name = "sys_users")
public class SysUsers extends IdEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String username;// 用户名
	/** 电子邮箱 */
	private java.lang.String email;
	private String password;// 用户密码
	private Short status;// 状态1：在线,2：离线,0：禁用

	private List<WeiXinPublicUser> publicUserList = new ArrayList<WeiXinPublicUser>();

	private Set<SysRoles> sysroles = new HashSet<SysRoles>();
	@Transient
	private List<SysAuthorities> authorities = new ArrayList<SysAuthorities>();

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "sys_users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<SysRoles> getSysroles() {
		return sysroles;
	}

	public void setSysroles(Set<SysRoles> sysUsersRoleses) {
		this.sysroles = sysUsersRoleses;
	}

	@JsonIgnore
	@Transient
	public List<SysAuthorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<SysAuthorities> authorities) {
		this.authorities = authorities;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "password", length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "username", nullable = false, length = 100)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "user")
	public List<WeiXinPublicUser> getPublicUserList() {
		return this.publicUserList;
	}

	public void setPublicUserList(List<WeiXinPublicUser> realName) {
		this.publicUserList = realName;
	}

}