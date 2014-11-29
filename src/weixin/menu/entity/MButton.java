package weixin.menu.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import web.system.entity.WeiXinPublicUser;

@Entity
@Table(name = "mbutton")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "pUser" })
public class MButton implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "m_no")
	private int no;
	@Column(name = "m_name")
	private String name;

	@Column(name = "m_type")
	private String type;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "puser_id")
	private WeiXinPublicUser pUser;

	@JsonIgnore
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parentButton")
	private List<MButton> sub_button;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private MButton parentButton;

	private String pname;
	private String url;
	@Column(name = "m_key")
	private String key;

	public List<MButton> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<MButton> sub_button) {
		this.sub_button = sub_button;
	}

	public MButton getParentButton() {
		return parentButton;
	}

	public void setParentButton(MButton parentButton) {
		this.parentButton = parentButton;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WeiXinPublicUser getpUser() {
		return pUser;
	}

	public void setpUser(WeiXinPublicUser pUser) {
		this.pUser = pUser;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
