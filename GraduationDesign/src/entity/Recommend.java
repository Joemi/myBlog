package entity;

import java.util.Date;

/**
 * Recommend entity. @author MyEclipse Persistence Tools
 */

public class Recommend implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer articleId;
	private Long recommendRate;
	private String createDate;

	// Constructors

	/** default constructor */
	public Recommend() {
	}

	/** minimal constructor */
	public Recommend(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Recommend(Integer id, Integer userId, Integer articleId,
			Long recommendRate, String createDate) {
		this.id = id;
		this.userId = userId;
		this.articleId = articleId;
		this.recommendRate = recommendRate;
		this.createDate = createDate;
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

	public Integer getArticleId() {
		return this.articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Long getRecommendRate() {
		return this.recommendRate;
	}

	public void setRecommendRate(Long recommendRate) {
		this.recommendRate = recommendRate;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}