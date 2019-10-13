<template>
<div class="bgcolorsFour">
    <div class="centerContentFour">
       <div class="main">
            <el-dialog title="提示" v-model="dialogVisible" :visible.sync="dialogVisible" size="tiny" width="30%" center>
                <span>确定要删除吗</span>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click='deleteLm()'>确 定</el-button>
                </span>
            </el-dialog>

            <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
                <el-form-item label="" prop="content">
                        <el-input type="input" v-model="ruleForm.content" style="width:840px" placeholder="想留点什么吗" ></el-input>
                    </el-form-item>
                <el-form-item v-if="isVisitor == false">
                    <el-button type="primary" @click="getLeaving()">发表</el-button>
                </el-form-item>
                <el-form-item v-else>
                    <el-button type="primary" @click="getLeavingByVisitor()">发表</el-button>
                </el-form-item>
            </el-form>

            <div class="leaving-item" v-for="item in dataList">
                <div class="left">
                    <div class="name" style="cursor:pointer" @click="openReply(item.id)"><i class="el-icon-message"></i>{{item.name}}</div>
                    <p>{{item.content}}</p>
                </div>
                <div class="right"> 
                    <span>{{item.createDate}}</span>
                    <div>
                        <el-button type="danger" size="mini" @click="openReply(item.id)" icon="el-icon-message"></el-button>
                        <el-button type="danger" size="mini" @click="readyDelete(item.id)" icon="el-icon-delete" v-if="isVisitor == false"></el-button>
                    </div>
                </div>
            </div>


                <el-dialog title="提示" v-model="dialogVisibleReply" :visible.sync="dialogVisibleReply" size="tiny" width="30%" center>
                    <el-form :model="replyData">
                        <el-form-item label="回复" label-width="60px">
                          <el-input v-model="replyData.content" auto-complete="off" type="text" placeholder="请输入要回复的内容"></el-input>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="dialogVisibleReply = false">取 消</el-button>
                        <el-button type="primary" @click='replyLeaving()'>确 定</el-button>
                    </span>
                </el-dialog>

            <div style="margin-top:10px;">
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
</div>
</template>
<script>
    export default{
        data(){
            return {
                dataList:{},
                dialogVisible:false,
                dialogVisibleReply:false,
                nowLeavingId :'',
                nowReplyId:'',
                currentPage: 1,
                pageNumber:10,
                totalNumber:0,
                ruleForm:{
                    content:''
                },
                replyData:{
                    content:''
                },
                isVisitor:false               //设置是否为访客
            }
        },
        created(){
            if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
                this.isVisitor = true;
                this.getLmByVisitor();
              }
              else{
                this.getLm();
              }
        },
        methods:{
            readyDelete(Id){
                this.dialogVisible = true;
                this.nowLeavingId = Id;
            },
            openReply(id){
                this.dialogVisibleReply = true;
                this.nowReplyId = id;
            },
            getLm(){
                var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getLeavingInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    currentPage: that.currentPage,
                    pageNumber: that.pageNumber
                })).then(res => {
                    that.dataList = res.data;
                })

                this.$ajax.post('http://localhost:8080/GraduationDesign/getLeavingCount', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId')
                })).then(res => {
                    that.totalNumber = res.data;
                })

            },
            deleteLm(){
                var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/deleteLeavingInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    leavingId: that.nowLeavingId
                })).then(res => {
                    if(res.data.code ==1){
                    	that.$message.success(res.data.msg);
                    	that.dialogVisible = false;
                    	that.getLm();
                    }
                    else{
                    	that.$message.error(res.data.msg);
                    	that.dialogVisible = false;
                    }
                })
            },
            handleCurrentChange(val) {
            	this.currentPage = val;
                if(this.isVisitor){
                    this.getLmByVisitor();
                }else{
                    this.getLm();
                }
            },
            handleSizeChange(val) {
            	this.pageNumber = val;
            	if(this.isVisitor){
                    this.getLmByVisitor();
                }else{
                    this.getLm();
                }
            },
            getLeaving(){
                var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/addLeavingInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    content: that.ruleForm.content,
                    checkId: sessionStorage.getItem('nameId')
                })).then(res => {
                    if(res.data.code ==1){
                    	that.$message.success(res.data.msg);
                    	that.ruleForm.content = "";
                        that.getLm();
                    }
                    else{
                    	that.$message.error(res.data.msg);
                    }
                })
            },
            replyLeaving(){
                var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/replyleaving', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    vositorId:sessionStorage.getItem('nameId'),
                    content: that.replyData.content,
                })).then(res => {
                    if(res.data.code ==1){
                        that.$message.success(res.data.msg);
                        that.getLm();
                    }
                    else{
                        that.$message.error(res.data.msg);
                    }
                    that.replyData.content = "";
                    that.dialogVisibleReply = false;
                })
            },

            getLmByVisitor(){
                var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getLeavingInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('visitedId'),
                    currentPage: that.currentPage,
                    pageNumber: that.pageNumber
                })).then(res => {
                    that.dataList = res.data;
                })

                this.$ajax.post('http://localhost:8080/GraduationDesign/getLeavingCount', this.$qs.stringify({
                    id: sessionStorage.getItem('visitedId')
                })).then(res => {
                    that.totalNumber = res.data;
                })

            },
            getLeavingByVisitor(){
                var that = this;
                if(!sessionStorage.getItem('nameId')){
                    this.$message({message: '请登录后再发表留言！',type: 'warning'});
                }
                else{
                    this.$ajax.post('http://localhost:8080/GraduationDesign/addLeavingInfo', this.$qs.stringify({
                        id: sessionStorage.getItem('visitedId'),
                        content: that.ruleForm.content,
                        checkId: sessionStorage.getItem('nameId')
                    })).then(res => {
                        if(res.data.code ==1){
                            that.$message.success(res.data.msg);
                            that.ruleForm.content = "";
                            this.getLmByVisitor();
                        }
                        else{
                            that.$message.error(res.data.msg);
                        }
                    })
                }
            },

        }
    }
</script>
<style>
    .bgcolorsFour{
        width: 100%;
        height: auto;
        background-color: #e8e9d6;
    }
    .centerContentFour{
        width: 1000px;
        /*height: 100%;*/
        min-height: 523px;
        margin: 0 auto;
        background-color: #ffffff;
    }
    .main{
        padding: 30px;
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
</style>
