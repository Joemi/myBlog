package entity;

import java.util.Date;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */

public class Article implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String type;
	private String abstract_;
	private String content;
	private Long clickRate;
	private Integer userId;
	private String articleStatus;
	private String createDate;
	private Integer commentNum;
	private String picturePath;
	
	// Constructors

	/** default constructor */
	public Article() {
	}

	/** minimal constructor */
	public Article(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Article(Integer id, String title, String type, String abstract_,
			String content, Long clickRate, Integer userId,
			String articleStatus, String createDate, Integer commentNum, String picturePath) {
		this.id = id;
		this.title = title;
		this.type = type;
		this.abstract_ = abstract_;
		this.content = content;
		this.clickRate = clickRate;
		this.userId = userId;
		this.articleStatus = articleStatus;
		this.createDate = createDate;
		this.commentNum = commentNum;
		this.picturePath = picturePath;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getClickRate() {
		return this.clickRate;
	}

	public void setClickRate(Long clickRate) {
		this.clickRate = clickRate;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getArticleStatus() {
		return this.articleStatus;
	}

	public void setArticleStatus(String articleStatus) {
		this.articleStatus = articleStatus;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Integer getCommentNum() {
        return this.commentNum;
    }
    
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
    
    public String getPicturePath() {
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

}