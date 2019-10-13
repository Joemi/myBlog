package entity;

import java.util.Date;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */

public class Userinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String realName;
	private String mail;
	private String sex;
	private Integer age;
	private String address;
	private String constellation;
	private String signature;
	private String headPortrait;
	private String createDate;
	private Integer articleNumber;
	private Integer traffic;

	// Constructors

	/** default constructor */
	public Userinfo() {
	}

	/** minimal constructor */
	public Userinfo(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Userinfo(Integer id, Integer userId, String realName, String mail,
			String sex, Integer age, String address, String constellation,
			String signature, String headPortrait, String createDate,
			Integer articleNumber, Integer traffic) {
		this.id = id;
		this.userId = userId;
		this.realName = realName;
		this.mail = mail;
		this.sex = sex;
		this.age = age;
		this.address = address;
		this.constellation = constellation;
		this.signature = signature;
		this.headPortrait = headPortrait;
		this.createDate = createDate;
		this.articleNumber = articleNumber;
		this.traffic = traffic;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConstellation() {
		return this.constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHeadPortrait() {
		return this.headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getArticleNumber() {
		return this.articleNumber;
	}

	public void setArticleNumber(Integer articleNumber) {
		this.articleNumber = articleNumber;
	}

	public Integer getTraffic() {
		return this.traffic;
	}

	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}

}