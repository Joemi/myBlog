<template>
	<div class="bgcolorsThreeM">
		<div class="centerContentThreeM">
			<div style="height:40px;width100%;padding:20px">
				<div class="search-input" v-if="isVisitor">
					<span>标题：</span>
					<input type="text" name="mc" v-model="search.title" placeholder="请输入标题" />
				</div>
				<div style="float:left">
					<span>分类：</span>
					<el-select v-model="search.type" clearable placeholder="请选择">
	    				<el-option
	     					v-for="item in options"
	      					:key="item.value"
	      					:label="item.label"
	      					:value="item.value">
	   					 </el-option>
	   				</el-select>
				</div>
                <div style="float:left;margin-left:20px" v-if="isVisitor == false">
                    <span>文章状态：</span>
                    <el-select v-model="search.articleStatus" clearable placeholder="请选择">
                        <el-option
                            v-for="itemStutus in optionStatus"
                            :key="itemStutus.value"
                            :label="itemStutus.label"
                            :value="itemStutus.value">
                         </el-option>
                    </el-select>
                </div>
				<div style="float:left;margin-left:20px">
					<el-button type="primary" icon="el-icon-search" @click="searchBolg()">搜索</el-button>
				</div>
				<div style="float:right">
					<el-button type="primary" icon="el-icon-edit" @click="newBlog()" v-if="isVisitor == false">新增</el-button>
				</div>
			</div>
			<div class='blog-content'>
	            <el-dialog title="提示" v-model="dialogVisible" :visible.sync="dialogVisible" size="tiny" width="30%" center>
	                <span>确定要删除吗</span>
	                <span slot="footer" class="dialog-footer">
	                    <el-button @click="dialogVisible = false">取 消</el-button>
	                    <el-button type="primary" @click='deleteTitle()'>确 定</el-button>
	                </span>
	            </el-dialog>
	            <div class='card' v-for = "item in noteData">
	                <div class='manager'>
                        <el-button type="primary" size="mini" icon="el-icon-view" @click="checkBlog(item.id)"></el-button>
	                    <el-button type="primary" size="mini" icon="el-icon-edit" @click="editBolg(item.id)" v-if="isVisitor == false"></el-button>
	                    <el-button type="primary" size="mini" icon="el-icon-delete" @click="deleteBlog(item.id)" v-if="isVisitor == false"></el-button>
	                </div>
	                <div class='card-title' style="cursor:pointer" @click="checkBlog(item.id)">
	                    {{item.title}}
	                </div>
	                <div class='tag-box'>
	                    <el-tag :type='redomColor()' >{{item.typeName}}</el-tag><span>{{item.abstract_}}</span>
	                </div>
	                <a class='remand'>
	                    阅读数({{item.clickRate}})&nbsp&nbsp评论({{item.commentNum}})
	                </a>
	            </div>
              
              <div class='card' v-if = "noteData == ''">
                  <div style="text-indent:20px;text-align:center;padding-top:20px" >暂时还没有该类博文，立即去创建吧！</div>
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

		</div>
	</div>
</template>
<script>
export default {
    data() {
      return {
        dialogVisible: false,
        noteData:"",
        color:['success','warning','primary'],
        nowDeleteId:'',
        options: [],
        optionStatus:{},
        search:{
          title:'',
          type:'',
          articleStatus:''
        },
        currentPage: 1,
        pageNumber:10,
        totalNumber:0,
        isVisitor:false               //设置是否为访客
      }
    }, 
    mounted(){
        if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
            this.isVisitor = true;
            this.getListByVisitor();
        }
        else{
            this.getList();
        }
        this.getParamList();
    },
    methods: {
        searchBolg(){
            if(this.isVisitor){
                this.getListByVisitor();
            }else{
                this.getList();
            }
        },
        getParamList(){
            this.$ajax.post('http://localhost:8080/GraduationDesign/getParamListInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                paramCode: 'type'
            })).then(res=>{
                this.options = res.data;
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getParamListInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                paramCode: 'articleStatus'
            })).then(res=>{
                this.optionStatus = res.data;
            })
        },
        getList(){
            var that = this;
            this.$ajax.post('http://localhost:8080/GraduationDesign/getMyBlogInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                type: that.search.type,
                articleStatus: that.search.articleStatus,
                currentPage: that.currentPage,
                pageNumber: that.pageNumber
            })).then(res=>{
                that.noteData = res.data;
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getMyBlogCount', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                type: that.search.type,
                articleStatus: that.search.articleStatus
            })).then(res=>{
                that.totalNumber = res.data;
            })
        },
        handleCurrentChange(val) {
          this.currentPage = val;
          if(this.isVisitor){
                this.getListByVisitor();
            }else{
                this.getList();
            }
        },
        handleSizeChange(val) {
          this.pageNumber = val;
          if(this.isVisitor){
                this.getListByVisitor();
            }else{
                this.getList();
            }
        },
        redomColor(value){
            var num = Math.floor(Math.random()*3)
            return this.color[num]
        },
        deleteBlog(id){
            this.dialogVisible = true
            this.nowDeleteId = id;
        },
        deleteTitle(){
            var that = this;
            this.dialogVisible = false;
            this.$ajax.post('http://localhost:8080/GraduationDesign/deleteBlog',this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    blogId:that.nowDeleteId
                })).then(res=>{
                    if(res.code!=0){
                        that.$message.success(res.data.msg);
                        that.getList();
                    }else{
                       that.$message.error(res.data.msg);
                    }
                    that.nowDeleteId = '';
                })
        },
        editBolg(id){
            sessionStorage.setItem('articleId',id);
            this.$router.push("/home/editBlog");
        },
        newBlog(){
        	this.$router.push("/home/editBlog");
        },
        checkBlog(id){
            sessionStorage.setItem('articleId',id);
            this.$router.push("/home/checkBlog");
        },


        getListByVisitor(){
            var that = this;
            this.$ajax.post('http://localhost:8080/GraduationDesign/getBlogInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                title: that.search.title,
                type: that.search.type,
                currentPage: that.currentPage,
                pageNumber: that.pageNumber
            })).then(res=>{
                that.noteData = res.data;
            })

            this.$ajax.post('http://localhost:8080/GraduationDesign/getBlogCount', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                title: that.search.title,
                type: that.search.type
            })).then(res=>{
                that.totalNumber = res.data;
            })
        },
    }
}
</script>
<style>
	.search-input {
		word-wrap: break-word;
		min-width: 100px;
		float: left;
		font-size: 16px;
		color: #333333;
		margin-right: 20px;
	}
	.search-input span {
  		display: inline-block;
  		text-align: right;
	}

	.search-input input[type="text"] {
  		height: 38px;
  		line-height: 38px;
  		margin: auto;
  		text-align: left;
  		width: 190px;
  		border: 1px solid #dcdfe6;
  		border-radius: 4px;
  		padding: 0 15px;
	}
	input::-webkit-input-placeholder{	/* 修改input框中placeholder的样式（字体颜色）*/
        color:#c0c9db;
     }
    input::-moz-placeholder{   /* Mozilla Firefox 19+ */
        color:#c0c9db;
    }
    input:-moz-placeholder{    /* Mozilla Firefox 4 to 18 */
        color:#c0c9db;
    }
    input:-ms-input-placeholder{  /* Internet Explorer 10-11 */ 
        color:#c0c9db;
    }
	.bgcolorsThreeM{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContentThreeM{
		width: 1000px;
		height: auto;
        min-height: 523px;
		margin: 0 auto;
		background-color: #ffffff;
	}
	.blog-content{
    	width:100%;
        height: auto;
	    .card{
	        position:relative;
	        width:96%;
	        margin:20px 20px;
	        height:70px;
	        border-bottom:1px solid #d1dbe5;
	        overflow:hidden;
	   	.card-title{
	        text-indent:20px;
	    }
	    .tag-box{
	        position:absolute;
	        left:20px;
	        bottom:10px;
	        span{
	            margin:0 2px;
	        }
	    }
	    .manager{
	        position:absolute;
	        right:20px;
	        top:10px;
	    }
	    .remand{
	        position:absolute;
	        bottom:10px;
	        right:20px;
	        color:#666;
	        font-size:14px;
	    }
    }
}
</style>