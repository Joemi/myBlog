package entity;
// default package



/**
 * Imformation entity. @author MyEclipse Persistence Tools
 */

public class Imformation  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer userId;
     private String infoType;
     private String abstract_;
     private String content;
     private Long clickRate;
     private String title;
     private String infoStatus;
     private String createDate;
     private Integer commentNum;
     private String picturePath;


    // Constructors

    /** default constructor */
    public Imformation() {
    }

	/** minimal constructor */
    public Imformation(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public Imformation(Integer id, Integer userId, String infoType, String abstract_, String content, Long clickRate, String title, String infoStatus, String createDate, Integer commentNum, String picturePath) {
        this.id = id;
        this.userId = userId;
        this.infoType = infoType;
        this.abstract_ = abstract_;
        this.content = content;
        this.clickRate = clickRate;
        this.title = title;
        this.infoStatus = infoStatus;
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

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInfoType() {
        return this.infoType;
    }
    
    public void setInfoType(String infoType) {
        this.infoType = infoType;
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

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfoStatus() {
        return this.infoStatus;
    }
    
    public void setInfoStatus(String infoStatus) {
        this.infoStatus = infoStatus;
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