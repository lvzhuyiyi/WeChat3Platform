package weixin.subscribe.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import web.system.entity.WeiXinPublicUser;
import weixin.template.entity.BaseTemplate;

@Entity
@Table(name = "subscriberesponse")
public class SubscribeResponse implements Serializable {
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "template_id")
	private BaseTemplate bTemplate;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "puser_id")
	private WeiXinPublicUser pUser;

	public BaseTemplate getbTemplate() {
		return bTemplate;
	}

	public void setbTemplate(BaseTemplate bTemplate) {
		this.bTemplate = bTemplate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
