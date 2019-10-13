<template>
<div style="height:100%;width:100%">
    <div class="bgcolorsStyle">
        <div class="headStyleOne">
            <div v-if="isLogin">
                <div class="topDiv" @click="goToHome()">欢迎你：{{loginName}}</div>
            </div>
            <div v-else>
              <div class="topDiv" @click="register">注册</div>
              <div class="topDiv" @click="login">登录</div>
            </div>
        </div>
        <div class="centerContentStyle">
            <div style="margin:0 auto;width:1000px">
                <el-carousel :interval="5000" arrow="always">
                    <el-carousel-item v-for="item in pictures" :key="item.index">
                      <img :src="item.url">
                    </el-carousel-item>
                </el-carousel>
            </div>
            
            <div style="margin:0 auto">
                
                <el-row>
                    <el-col :span="6" >
                        
                        <el-menu
                          default-active="1"
                          class="el-menu-vertical-demo" >
                          <el-menu-item index="1" @click="getInfo(1)">
                            <i class="el-icon-news"></i>
                            <span slot="title">最新文章</span>
                          </el-menu-item>
                          <el-menu-item index="2" @click="getInfo(2)">
                            <i class="el-icon-star-off"></i>
                            <span slot="title">推荐文章</span>
                          </el-menu-item>
                          <el-menu-item index="3" @click="getInfo(3)">
                            <i class="el-icon-view"></i>
                            <span slot="title">资讯管理</span>
                          </el-menu-item>
                          <!-- <el-menu-item index="4" @click="getInfoOne(4)">
                            <i class="el-icon-document"></i>
                            <span slot="title">实力大咖</span>
                          </el-menu-item> -->
                        </el-menu>

                    </el-col>
                    <el-col :span="18" style="border-left:1px solid #e6e6e6">

                        <div class='blog-contentOne'>
                            <div class='card' v-for = "item in noteData">
                                <div class='manager'>
                                    <span>{{item.createDate}}</span>
                                </div>
                                <div class='card-title' style="cursor:pointer" @click="visitedUser(item.userId,item.id)">
                                    {{item.title}}
                                </div>
                                <div class='tag-box'>
                                    <el-tag type='success' >{{item.typeName}}</el-tag><span>{{item.abstract_}}</span>
                                </div>
                                <a class='remand'>
                                    阅读数({{item.clickRate}})&nbsp&nbsp评论({{item.commentNum}})
                                </a>
                            </div>
                            <div style="margin-left:20px">
                            <el-pagination
                                background
                                @current-change="handleCurrentChange"
                                @size-change="handleSizeChange"
                                :current-page="currentPage"
                                :page-size="pageNumber"
                                :current-page.sync="currentPage"
                                layout="total, prev, pager, next, jumper"
                                :total="totalNumber">
                            </el-pagination>
                            </div>
                        </div>

                    </el-col>
                </el-row>
            </div> 
        </div>
    </div>
</div>
</template>
<script>
    export default {
        data(){
            return{
                pictures:[
                    {'index':0,'url': require('../image/picture1.png')},
                    {'index':1,'url': require('../image/picture.png')},
                    {'index':2,'url': require('../image/picture2.png')},
                    {'index':4,'url': require('../image/picture.png')}
                ],
                isLogin:false,
                loginName:'',
                // noteData:[{title:'XXX',createDate:'2018-06-01',clickRate:233,commentNum:0,typeName:'HTML'},
                //     {title:'YYYYYYYYY',createDate:'2018-06-02',clickRate:999,commentNum:5,typeName:'JavaScript'},
                //     {title:'ZZZZZZZ',createDate:'2018-06-04',clickRate:233,commentNum:0,typeName:'HTML'},
                //     {title:'YYYYYYYYY',createDate:'2018-06-02',clickRate:999,commentNum:5,typeName:'JavaScript'},
                //     {title:'ZZZZZZZ',createDate:'2018-06-04',clickRate:233,commentNum:0,typeName:'HTML'}],
                noteData:[],
                currentPage: 1,
                pageNumber:5,
                totalNumber:0,
                nums:1
            }
        },
        mounted () {
            if(sessionStorage.getItem('name')){
                this.isLogin = true;
                this.loginName = sessionStorage.getItem("name");
            };
            this.getInfo(this.nums);
        },
        methods:{
            register(){
                this.$router.push("/register");
            },
            login(){
                this.$router.push("/login");
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getInfo(this.nums);
            },
            handleSizeChange(val) {
                this.pageNumber = val;
                this.getInfo(this.nums);
            },
            getInfo(num){
                this.nums =num;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getIndexListInfo', this.$qs.stringify({
                    num: this.nums,
                    currentPage:this.currentPage,
                    pageNumber:this.pageNumber
                })).then(res=>{
                    this.noteData = res.data;
                });

                this.$ajax.post('http://localhost:8080/GraduationDesign/getIndexListCount', this.$qs.stringify({
                    num: this.nums
                })).then(res=>{
                    this.totalNumber = res.data;
                })
            },
            visitedUser(userId,id){
                sessionStorage.setItem('visitedId',userId);//设置访问用户id
                sessionStorage.setItem('articleId',id);
                sessionStorage.setItem('menuIndex',3);
                this.$router.push("/home/checkBlog");
            },
            goToHome(){
                this.$router.push("/home");
            }
        }
    }
</script>
<style>
    .headStyleOne{
      width: 100%;
      height: 60px;
      background: #fff;
      box-shadow: 0 2px 4px 0 rgba(0,0,0,.05);
    }
    .bgcolorsStyle{
        width: 100%;
        height: 100%;
        background-color: #e4edf7;
    }
    .centerContentStyle{
        width: 1000px;
        height: auto;
        margin: 0 auto;
        background-color: #ffffff;
    }
    .el-carousel__item h3 {
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }
      
    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }
      
    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
    .topDiv{
      float:right;
      margin-right:20px;
      padding-top:20px;
      cursor:pointer;
      color:#333
    }
    .blog-contentOne{
        width:100%;
        min-height: 563px;
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