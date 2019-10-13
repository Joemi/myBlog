package entity;

import java.util.Date;

/**
 * Messageboard entity. @author MyEclipse Persistence Tools
 */

public class Messageboard implements java.io.Serializable {

	// Fields

	private Integer id;
	private String createDate;
	private Integer userId;
	private String content;
	private String messageStatus;

	// Constructors

	/** default constructor */
	public Messageboard() {
	}

	/** minimal constructor */
	public Messageboard(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Messageboard(Integer id, String createDate, Integer userId,
			String content, String messageStatus) {
		this.id = id;
		this.createDate = createDate;
		this.userId = userId;
		this.content = content;
		this.messageStatus = messageStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageStatus() {
		return this.messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

}