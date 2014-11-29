package weixin.weixinuser.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import web.system.entity.WeiXinPublicUser;

@Entity
public class UserNumRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int users;
	@Temporal(TemporalType.DATE)
	@Column(name = "u_date")
	private Date date;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "puser_id")
	private WeiXinPublicUser pUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsers() {
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public WeiXinPublicUser getpUser() {
		return pUser;
	}

	public void setpUser(WeiXinPublicUser pUser) {
		this.pUser = pUser;
	}

}
