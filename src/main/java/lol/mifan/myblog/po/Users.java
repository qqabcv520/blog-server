package lol.mifan.myblog.po;
// Generated 2017-5-16 12:53:03 by Hibernate Tools 4.3.5.Final

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", catalog = "mifanblog")
public class Users implements java.io.Serializable {

	private Integer id;
	private String username;
	private String password;
	private String email;
	private String phone;
	private String avatar;
	private Date createTime;
	private Date lastLoginTime;
	private boolean deleted;
	private String note;
	private String token;
	private Set<Article> articles = new HashSet<Article>(0);
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<Role> roles = new HashSet<Role>(0);

	public Users() {
	}

	public Users(boolean deleted) {
		this.deleted = deleted;
	}

	public Users(String username, String password, String email, String phone, String avatar, Date createTime,
			Date lastLoginTime, boolean deleted, String note, String token, Set<Article> articles, Set<Review> reviews,
			Set<Role> roles) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.avatar = avatar;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.deleted = deleted;
		this.note = note;
		this.token = token;
		this.articles = articles;
		this.reviews = reviews;
		this.roles = roles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "avatar")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time", length = 19)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "deleted", nullable = false)
	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "note")
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "token")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "userrole", catalog = "mifanblog", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
