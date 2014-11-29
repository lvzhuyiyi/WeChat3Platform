package weixin.template.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import web.system.entity.WeiXinPublicUser;

@Entity
@Table(name = "template")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "templateType")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "pUser" })
public class BaseTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -908908908090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer no;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "puser_id")
	private WeiXinPublicUser pUser;

	private String word;

	@Column(insertable = false, updatable = false)
	private String templateType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public WeiXinPublicUser getpUser() {
		return pUser;
	}

	public void setpUser(WeiXinPublicUser pUser) {
		this.pUser = pUser;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public void setWord(String word) {

		this.word = word;
	}

	public String getWord() {

		return word;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

}
