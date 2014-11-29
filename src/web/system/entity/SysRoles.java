package web.system.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 */
@Entity
@Table(name = "sys_roles")
public class SysRoles implements Serializable {

	private int role_id;

	private String role_name;

	private String role_desc;

	private int enable;

	private int issys;

	private String module_id;

	private Set<SysUsers> users = new HashSet<SysUsers>();

	private Set<SysAuthorities> authorities;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_roles_authorities", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	public Set<SysAuthorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<SysAuthorities> authorities) {
		this.authorities = authorities;
	}

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "sysroles", fetch = FetchType.LAZY)
	public Set<SysUsers> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUsers> users) {
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	@Column(name = "ROLE_NAME")
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	@Column(name = "ROLE_DESC")
	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	@Column(name = "ENABLE")
	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	@Column(name = "ISSYS")
	public int getIssys() {
		return issys;
	}

	public void setIssys(int issys) {
		this.issys = issys;
	}

	@Column(name = "MODULE_ID")
	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

}
