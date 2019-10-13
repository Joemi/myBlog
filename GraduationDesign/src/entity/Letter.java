package entity;

import java.util.Date;

/**
 * Letter entity. @author MyEclipse Persistence Tools
 */

public class Letter implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer visitorId;
	private String title;
	private String createDate;
	private String content;
	private String letterStatus;

	// Constructors

	/** default constructor */
	public Letter() {
	}

	/** minimal constructor */
	public Letter(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Letter(Integer id, Integer userId, Integer visitorId, String title,
			String createDate, String content, String letterStatus) {
		this.id = id;
		this.userId = userId;
		this.visitorId = visitorId;
		this.title = title;
		this.createDate = createDate;
		this.content = content;
		this.letterStatus = letterStatus;
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

	public Integer getVisitorId() {
		return this.visitorId;
	}

	public void setVisitorId(Integer visitorId) {
		this.visitorId = visitorId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLetterStatus() {
		return this.letterStatus;
	}

	public void setLetterStatus(String letterStatus) {
		this.letterStatus = letterStatus;
	}

}