<template>
	<div class="bgcolorsThreeV">
		<div class="centerContentThreeV">
			<div class="title">
				<div class='card'>
					<div class='card-title'>
						<el-tag type="danger">{{articleData.typeName}}</el-tag><span>&nbsp;{{articleData.title}}</span>
					</div>
					<div class='tag-box'>
	                    <span>{{articleData.createDate}}</span><span>&nbsp;&nbsp;阅读({{articleData.clickRate}})</span>
	                </div>
	                <div class='remand'>
	                    <el-button type="primary" icon="el-icon-arrow-left" circle @click="goToList()"></el-button>
	                    <el-button type="primary" icon="el-icon-edit" circle  @click="goToEdit()" v-if="isVisitor == false"></el-button>
	                </div>
				</div>

				<markdown-editor v-model="articleData.content" :configs="configs" ref="markdownEditor" style="display:none"></markdown-editor>
				<div v-html="output"  class="markdown-body" style="margin:20px;padding: 20px;border: 1px solid #d1dbe5;"></div>

				<!-- 图片展示 -->
				<div v-if="articleData.picturePath" class="avatar-uploader" style="padding:10px 10px 10px 20px">
			        <div tabindex="0" class="el-upload el-upload--text">
			          <img  :src="articleData.picturePath" class="avatar">
			        </div>
			    </div>


				<div style="margin:10px 0 0 400px;" v-if="isVisitor">
					<el-button type="warning" plain icon="el-icon-star-off" @click="recommendIt()" v-if="isRecommend" disabled>推荐</el-button>
					<el-button type="warning" plain icon="el-icon-star-off" @click="recommendIt()" v-else>推荐</el-button>
					<el-button type="info" plain icon="el-icon-back" @click="goToIndex()">首页</el-button>
				</div>
				
				<div style="margin:20px;margin-bottom:0">
				<el-form :inline="true" :model="recommend" class="demo-form-inline">
	                <el-form-item label="" prop="content">
	                    <el-input type="input" v-model="recommend.content" style="width:860px" placeholder="想说点什么吗" ></el-input>
	                </el-form-item>
	                <el-form-item v-if="isVisitor == false">
	                    <el-button type="primary" @click="adddComment()">发表</el-button>
	                </el-form-item>
	                <el-form-item v-else>
	                    <el-button type="primary" @click="adddCommentByVisitor()">发表</el-button>
	                </el-form-item>
	            </el-form>
	            </div>


				<div class="main" style="padding:0 30px;">
					<div class="leaving-item" v-for="item in dataList">
		                <div class="left">
		                    <div class="name"><i class="el-icon-message"></i>{{item.name}}</div>
		                    <p>{{item.content}}</p>
		                </div>
		                <div class="right"> 
		                    <span>{{item.createDate}}</span>
		                    <div>
		                    	<el-button type="danger" size="mini" @click="openReply(item.id)" icon="el-icon-message"></el-button>
		                    	<el-button type="danger" size="mini" @click="openDel(item.id)" icon="el-icon-delete" v-if="isVisitor == false"></el-button>
		                    </div>
		                </div>
		            </div>
	            </div>

				<div style="margin-top:10px;margin-left:20px">
	                <el-pagination
	                    background
	                    @size-change="handleSizeChange"
	                    @current-change="handleCurrentChange"
	                    :current-page="currentPage"
	                    :page-sizes="[10, 20, 30, 40]"
	                    :page-size="pageNumber"
	                    layout="total, sizes, prev, pager, next, jumper"
	                    :total="totalNumber">
	                </el-pagination>
	            </div>

	            <el-dialog title="提示" v-model="dialogVisibleDel" :visible.sync="dialogVisibleDel" size="tiny" width="30%" center>
	                <span>确定要删除吗</span>
	                <span slot="footer" class="dialog-footer">
	                    <el-button @click="dialogVisibleDel = false">取 消</el-button>
	                    <el-button type="primary" @click='deleteComment()'>确 定</el-button>
	                </span>
	            </el-dialog>

	            <el-dialog title="提示" v-model="dialogVisibleReply" :visible.sync="dialogVisibleReply" size="tiny" width="30%" center>
	                <el-form :model="replyData">
					    <el-form-item label="回复" label-width="60px">
					      <el-input v-model="replyData.content" auto-complete="off" type="text" placeholder="请输入要回复的内容"></el-input>
					    </el-form-item>
					</el-form>
	                <span slot="footer" class="dialog-footer">
	                    <el-button @click="dialogVisibleReply = false">取 消</el-button>
	                    <el-button type="primary" @click='replyComment()'>确 定</el-button>
	                </span>
	            </el-dialog>

			</div>
		</div>
	</div>
</template>
<script>
import markdownEditor from 'vue-simplemde/src/markdown-editor';         // 导入markdownEditor组件
export default {
	components: {
    	markdownEditor
  	},
  	data(){
  		return{
  			configs: {                                  // markdown编辑器配置参数
	          // initialValue: 'Hello Eric',               // 设置初始值
	          renderingConfig: {
	            codeSyntaxHighlighting: true,           // 开启代码高亮
	            highlightingTheme: 'atom-one-light'     // 自定义代码高亮主题
	          }
        	},
  			articleData:{
  				// title:'HTML标签',
  				// typeName:'HTML',
  				// createDate:'2018-06-01',
  				// clickRate:233,
  				// content:'# 水水水水是是是'
  			},
  			recommend:{},								//评论发送数据
  			dataList:[],
  			output:'',									//博文展示绑定变量
  			currentPage: 1,
            pageNumber:10,
            totalNumber:0,
            dialogVisibleDel:false,
            dialogVisibleReply:false,
            replyData:{},								//评论回复数据
            nowDeleteId:'',
            nowReplyId:'',
            isVisitor:false,							//设置是否为访客
            isRecommend:false							//设置是否推荐过
  		}
  	},
  	computed: {
    	simplemde() {
      	return this.$refs.markdownEditor.simplemde;
    	},
    },
    mounted(){
    	if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
    		this.isVisitor = true;
    		if(sessionStorage.getItem('nameId')){
    			this.addClick();
    			this.getInfoByVisitor();
    		}
    		else{
    			this.getInfoByVisitor();
    		}
    	}
    	else{
    		this.getInfo();
    	}

  	},
    methods:{
    	getInfo(){
    		this.$ajax.post('http://localhost:8080/GraduationDesign/checkBlogOne', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                blogId: sessionStorage.getItem('articleId')
            })).then(res=>{
                this.articleData = res.data;
                this.output = this.simplemde.markdown(this.articleData.content);
                if(this.articleData.picturePath != null && this.articleData.picturePath !=''){
                  this.articleData.picturePath = "http://localhost:8080/GraduationDesign/displayImage?path="+this.articleData.picturePath;
                }
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getCommendInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                articleId: sessionStorage.getItem('articleId'),
                currentPage: this.currentPage,
                pageNumber: this.pageNumber
            })).then(res=>{
                this.dataList = res.data;
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getCommendCount', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                articleId: sessionStorage.getItem('articleId')
            })).then(res=>{
                this.totalNumber = res.data;
            })
    	},
    	handleCurrentChange(val) {
            this.currentPage = val;
            if(this.isVisitor){
            	this.getInfoByVisitor();
            }
            else{
            	this.getInfo();
            }
            
        },
        handleSizeChange(val) {
            this.pageNumber = val;
            if(this.isVisitor){
            	this.getInfoByVisitor();
            }
            else{
            	this.getInfo();
            }
        },
        goToList(){
        	sessionStorage.removeItem("articleId");
        	this.$router.push("/home/blog");
        },

        openDel(id){
        	this.dialogVisibleDel = true;
        	this.nowDeleteId = id;
        },
        openReply(id){
        	this.dialogVisibleReply = true;
        	this.nowReplyId = id;
        },
        deleteComment(id){
        	this.$ajax.post('http://localhost:8080/GraduationDesign/deleteComment', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                commentId: this.nowDeleteId
            })).then(res=>{
                if(res.data.code == 1){
		            this.$message.success(res.data.msg);
		        	this.getInfo();
		        }
		        else{
		            this.$message.error(res.data.msg);
		        }
		        this.dialogVisibleDel = false;
            })
        },
        adddComment(){
            if(this.articleData.articleStatus != 1){
                this.$message({message: '该文章处于待审核或者不通过状态，无法评论！',type: 'warning'});
            }
            else{
                this.$ajax.post('http://localhost:8080/GraduationDesign/addComment', this.$qs.stringify({
                    id :  sessionStorage.getItem('nameId'),
                    articleId: sessionStorage.getItem('articleId'),
                    vositorId:sessionStorage.getItem('nameId'),
                    content:this.recommend.content,
                    articTitle:this.articleData.title
                })).then(res=>{
                    if(res.data.code == 1){
                        this.$message.success(res.data.msg);
                        this.recommend.content = '';
                        this.getInfo();
                    }
                    else{
                        this.$message.error(res.data.msg);
                    }
                })
            }
        },
        replyComment(){
        	this.$ajax.post('http://localhost:8080/GraduationDesign/replyComment', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                vositorId: sessionStorage.getItem('nameId'),
                content:this.replyData.content,
                articTitle:this.articleData.title
            })).then(res=>{
                if(res.data.code == 1){
		            this.$message.success(res.data.msg);
		        }
		        else{
		            this.$message.error(res.data.msg);
		        }
		        this.dialogVisibleReply = false;
		        this.replyData.content ='';
            })
        },
        goToEdit(){
        	this.$router.push("/home/editBlog");
        },


        getInfoByVisitor(){

    		this.$ajax.post('http://localhost:8080/GraduationDesign/checkBlogOne', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                blogId: sessionStorage.getItem('articleId')
            })).then(res=>{
                this.articleData = res.data;
                this.output = this.simplemde.markdown(this.articleData.content);
                if(this.articleData.picturePath != null && this.articleData.picturePath !=''){
                  this.articleData.picturePath = "http://localhost:8080/GraduationDesign/displayImage?path="+this.articleData.picturePath;
                }
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getCommendInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                articleId: sessionStorage.getItem('articleId'),
                currentPage: this.currentPage,
                pageNumber: this.pageNumber
            })).then(res=>{
                this.dataList = res.data;
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getCommendCount', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                articleId: sessionStorage.getItem('articleId')
            })).then(res=>{
                this.totalNumber = res.data;
            })
    	},

    	adddCommentByVisitor(){
            if(!sessionStorage.getItem('nameId')){
                this.$message({message: '请登录后再发表评论！',type: 'warning'});
            }
            else{
                this.$ajax.post('http://localhost:8080/GraduationDesign/addComment', this.$qs.stringify({
                    id :  sessionStorage.getItem('visitedId'),
                    articleId: sessionStorage.getItem('articleId'),
                    vositorId:sessionStorage.getItem('nameId'),
                    content:this.recommend.content,
                    articTitle:this.articleData.title
                })).then(res=>{
                    if(res.data.code == 1){
                        this.$message.success(res.data.msg);
                        this.recommend.content = '';
                        this.getInfoByVisitor();
                    }
                    else{
                        this.$message.error(res.data.msg);
                    }
                })
            }
        },
        recommendIt(){
            if(!sessionStorage.getItem('nameId')){
                this.$message({message: '如果你喜欢本文章请登录后再推荐！',type: 'warning'});
            }
            else{
                this.$ajax.post('http://localhost:8080/GraduationDesign/recommendBlog', this.$qs.stringify({
                    id:  sessionStorage.getItem('visitedId'),
                    blogId: sessionStorage.getItem('articleId')
                })).then(res=>{
                    if(res.data.code == 1){
                        this.$message.success(res.data.msg);
                        this.isRecommend = true;
                    }
                    else{
                        this.$message.error(res.data.msg);
                    }
                })
            }
        },
        goToIndex(){
        	sessionStorage.removeItem("visitedId");
            sessionStorage.removeItem("menuIndex");
        	this.$router.push("/index");
        },
        addClick(){
        	this.$ajax.post('http://localhost:8080/GraduationDesign/addBlogClick', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                blogId: sessionStorage.getItem('articleId')
            })).then(res=>{

        	})
        }

    }
}
</script>
<style>
@import '~simplemde/dist/simplemde.min.css';
@import '~highlight.js/styles/atom-one-dark.css';
@import '~github-markdown-css/github-markdown.css';
	.bgcolorsThreeV{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContentThreeV{
		width: 1000px;
		height: 100%;
        min-height: 523px;
		margin: 0 auto;
		background-color: #ffffff;
	}
	.title{
	    padding: 10px 0;
	    width: 100%;
	}
	.avatar-uploader .el-upload {
	    border: 1px dashed #ef3131;
	    border-radius: 6px;
	    cursor: pointer;
	    position: relative;
	    overflow: hidden;
	}
	.avatar-uploader .el-upload:hover {
	    border-color: #409EFF;
	}
	.avatar-uploader-icon {
	    font-size: 28px;
	    color: #8c939d;
	    width: 178px;
	    height: 178px;
	    line-height: 178px;
	    text-align: center;
	}
	.avatar {
	    width: 178px;
	    height: 178px;
	    display: block;
	}

	.card{
	    position:relative;
	    width:96%;
	    margin:20px;
        height:70px;
        border-bottom:1px solid #d1dbe5;
        overflow:hidden;
    .tag-box{
        position:absolute;
        left:20px;
        bottom:10px;
        color: #948585;
        span{
            margin:0 2px;
        }
    }
    .remand{
        position:absolute;
        bottom:10px;
        right:20px;
        color:#948585;
        font-size:14px;
    }

    .buttonCenter{
    	margin:0 auto;
    	margin-left: 20px;
    }
    .main{
        padding:0 30px;
        .leaving-item{
            border-bottom: 1px solid #d1dbe5;
            padding: 20px 0;
            overflow: hidden;
            .left{
                float: left;
                width: 80%;
            }
            .right{
                float: right;
                width: 20%;
                text-align: right;
                span{
                    display: block;
                    font-size: 14px;
                    margin-bottom: 10px;
                    color:#99A9BF;
                }
            }
        }
        .name{
            font-size: 16px;
            i{
                color: #99A9BF;
                margin-right: 10px;
            }
        }
        p{
            color: #8492A6;
            font-size: 14px;
            margin-top:5px;
            margin-bottom: 0;
            margin-left: 26px;
        }
    }
}
</style>