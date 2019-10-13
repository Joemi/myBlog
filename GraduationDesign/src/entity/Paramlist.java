package entity;

/**
 * Paramlist entity. @author MyEclipse Persistence Tools
 */

public class Paramlist implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paramCode;
	private String paramName;
	private String paramValue;
	private String paramText;

	// Constructors

	/** default constructor */
	public Paramlist() {
	}

	/** minimal constructor */
	public Paramlist(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Paramlist(Integer id, String paramCode, String paramName,
			String paramValue, String paramText) {
		this.id = id;
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.paramText = paramText;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamText() {
		return this.paramText;
	}

	public void setParamText(String paramText) {
		this.paramText = paramText;
	}

}