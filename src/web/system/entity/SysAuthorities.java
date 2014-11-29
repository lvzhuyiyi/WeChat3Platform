package web.system.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 */
@Entity
@Table(name = "sys_authorities")
public class SysAuthorities implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHORITY_ID")
	private int authority_id;
	@Column(name = "AUTHORITY_MARK")
	private String authority_mark;
	@Column(name = "AUTHORITY_NAME")
	private String authority_name;
	@Column(name = "AUTHORITY_DESC")
	private String authority_desc;
	@Column(name = "MESSAGE")
	private String message;
	@Column(name = "ENABLE")
	private int enable;
	@Column(name = "ISSYS")
	private int issys;
	@Column(name = "MODULE_ID")
	private String module_id;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "authorities")
	private Set<SysRoles> sysroles;

	public Set<SysRoles> getSysRoles() {
		return sysroles;
	}

	public void setSysRoles(Set<SysRoles> sysUsersRoleses) {
		this.sysroles = sysUsersRoleses;
	}

	public int getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(int authority_id) {
		this.authority_id = authority_id;
	}

	public String getAuthority_mark() {
		return authority_mark;
	}

	public void setAuthority_mark(String authority_mark) {
		this.authority_mark = authority_mark;
	}

	public String getAuthority_name() {
		return authority_name;
	}

	public void setAuthority_name(String authority_name) {
		this.authority_name = authority_name;
	}

	public String getAuthority_desc() {
		return authority_desc;
	}

	public void setAuthority_desc(String authority_desc) {
		this.authority_desc = authority_desc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getIssys() {
		return issys;
	}

	public void setIssys(int issys) {
		this.issys = issys;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	@Override
	public String toString() {
		return getAuthority_name() + ":" + getAuthority_desc();
	}
}
