package entity;

/**
 * Foususer entity. @author MyEclipse Persistence Tools
 */

public class Foususer implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer fousUserId;

	// Constructors

	/** default constructor */
	public Foususer() {
	}

	/** minimal constructor */
	public Foususer(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Foususer(Integer id, Integer userId, Integer fousUserId) {
		this.id = id;
		this.userId = userId;
		this.fousUserId = fousUserId;
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

	public Integer getFousUserId() {
		return this.fousUserId;
	}

	public void setFousUserId(Integer fousUserId) {
		this.fousUserId = fousUserId;
	}

}