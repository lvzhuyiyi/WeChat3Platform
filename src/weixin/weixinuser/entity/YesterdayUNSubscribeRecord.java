package weixin.weixinuser.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import web.system.entity.WeiXinPublicUser;

@Entity
public class YesterdayUNSubscribeRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int unSubscribers;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "puser_id")
	private WeiXinPublicUser pUser;
	@Temporal(TemporalType.DATE)
	@Column(name = "s_date")
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnSubscribers() {
		return unSubscribers;
	}

	public void setUnSubscribers(int unSubscribers) {
		this.unSubscribers = unSubscribers;
	}

	public WeiXinPublicUser getpUser() {
		return pUser;
	}

	public void setpUser(WeiXinPublicUser pUser) {
		this.pUser = pUser;
	}

}
