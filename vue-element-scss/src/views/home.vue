<template>
<div style="height:100%;width:100%">
  <div class="headStyle">
    <div style="float:right;margin-right:150px;padding-top:20px;color:#333;cursor:pointer" @click="goToIndex()">首页</div>
    <div v-if="isLogin">
      <div class="topDiv">{{loginName}}
        <ul>
          <li @click="changePW()">修改密码</li>
          <li @click="goToMessage()">消息中心</li>
          <li @click="cancel()">注销</li>
        </ul>
      </div>
    </div>
    <div v-else>
      <div class="topDiv" @click="register">注册</div>
      <div class="topDiv" @click="login">登录</div>
    </div>
    <el-badge :value="newMail" :max="99" class="item" style="float:right;margin-right:20px;margin-top:11px" v-if="isVisitor == false">
      <el-button type="info" icon="el-icon-message" circle @click="goToMessage()"></el-button>
    </el-badge>
  </div>
  <div class="pictureStyle">
    <img src="../image/side.jpg" />
  </div>
  <div class="menuStyle">
      <ul>
        <router-link :to="{path: '/home/homePage'}"><li :class="{active:1==index}" @click="changeMenu(1)">首页</li></router-link>
        <router-link :to="{path: '/home/aboutMe'}"><li :class="{active:2==index}" @click="changeMenu(2)">关于我</li></router-link>
        <router-link :to="{path: '/home/blog'}"><li :class="{active:3==index}" @click="changeMenu(3)">博文</li></router-link>
        <router-link :to="{path: '/home/leaving'}"><li :class="{active:4==index}" @click="changeMenu(4)">留言板</li></router-link>
        <router-link :to="{path: '/home/album'}"><li :class="{active:5==index}" @click="changeMenu(5)">相册</li></router-link>
      </ul>
  </div>

<el-dialog
  title="修改密码"
  :visible.sync="dialogVisible"
  width="30%" center>
  <el-form :model="form">
    <el-form-item label="原密码" label-width="120px">
      <el-input v-model="form.oldPassword" auto-complete="off" type="password" placeholder="请输入原密码"></el-input>
    </el-form-item>
    <el-form-item label="新密码" label-width="120px">
      <el-input v-model="form.newPassword" auto-complete="off" type="password" placeholder="请输入新密码"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" label-width="120px">
      <el-input v-model="form.re_newPassword" auto-complete="off" type="password" placeholder="请输入重复密码"></el-input>
    </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="changeIt()">确 定</el-button>
  </span>
</el-dialog>

  <router-view></router-view>

  <div class="footStyle"></div>
</div>
</template>
<script>

export default {
    data(){
      return {
        index: 1,
        isLogin: false,
        loginName:'',
        dialogVisible:false,
        form:{
          oldPassword:'',
          newPassword:'',
          re_newPassword:''
        },
        newMail:0,
        newMessage:'',
        isVisitor:false               //设置是否为访客
      };
    },
    mounted(){
      if(sessionStorage.getItem('menuIndex')){
        this.index = sessionStorage.getItem('menuIndex');
        this.changeMenu(this.index);
      }
      if(sessionStorage.getItem('name')){
        this.isLogin = true;
        this.loginName = sessionStorage.getItem("name");
      }

      if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
        this.isVisitor = true;
      }
      if(sessionStorage.getItem('nameId')){
        this.getMail();
      }
    },
    methods:{
      getMail(){
        this.$ajax.post('http://localhost:8080/GraduationDesign/mailInfo',this.$qs.stringify({
            id:sessionStorage.getItem("nameId")
          })).then(res=>{
            this.newMail = res.data;
            if(this.newMail == 0){
                this.newMessage = "您目前没有要处理的消息，开始创建新的文章吧"
                this.openInfoOne();
            }
            else{
                this.newMessage = "欢迎回来，您目前有"+this.newMail+"条信件未处理";
                this.openInfoTwo();
            }
          })
      },
      changeMailCount(){
        this.$ajax.post('http://localhost:8080/GraduationDesign/mailInfo',this.$qs.stringify({
            id:sessionStorage.getItem("nameId")
          })).then(res=>{
            this.newMail = res.data;
          })
      },
      openInfoOne() {
        this.$notify({
          title: '欢迎回来',
          message: this.newMessage,
          type: 'success'
        });
      },
      openInfoTwo() {
        this.$notify.info({
          title: '未读消息',
          message: this.newMessage
        });
      },
      register(){
        sessionStorage.removeItem("visitedId");
        sessionStorage.removeItem("menuIndex");
        this.$router.push("/register")
      },
      login(){
        sessionStorage.removeItem("visitedId");
        sessionStorage.removeItem("menuIndex");
        this.$router.push("/login")
      },
      changeMenu(nums){
        this.index = nums;
        sessionStorage.setItem('menuIndex',nums);
        if(sessionStorage.getItem("nameId")){
          this.changeMailCount();
        }
      },
      goToMessage(){
        if(sessionStorage.getItem("nameId")){
          this.changeMailCount();
        }
        sessionStorage.removeItem("visitedId");
        this.$router.push("/home/letter");
      },
      changePW(){
        this.dialogVisible = true;
        if(sessionStorage.getItem("nameId")){
          this.changeMailCount();
        }
      },
      changeIt(){
        var that = this;
        if(that.form.newPassword != that.form.re_newPassword){
          that.$message.error("两次密码输入不一致！")
        }else{
          this.$ajax.post('http://localhost:8080/GraduationDesign/changePW',this.$qs.stringify({
            id:sessionStorage.getItem("nameId"),
            oldPassword : that.form.oldPassword,
            newPassword : that.form.newPassword
          })).then(res=>{
            if(res.data.code==1){
              that.$message.success(res.data.msg);
              that.dialogVisible = false;
            }else{
              that.$message.error(res.data.msg);
            }
          })
        }
      },
      cancel(){
        this.$ajax.post('http://localhost:8080/GraduationDesign/cancel',this.$qs.stringify({
            id:sessionStorage.getItem("nameId")
          })).then(res=>{
          if(res.data.code==1){
            this.$message.success(res.data.msg);
            sessionStorage.removeItem("nameId");
            sessionStorage.removeItem("name");
            sessionStorage.removeItem("visitedId");
            this.$router.push("/login");
          }else{
            this.$message.error(res.data.msg);
          }
        })
      },
      goToIndex(){
        sessionStorage.removeItem("visitedId");
        sessionStorage.removeItem("menuIndex");
        this.$router.push("/index");
      }
    }
}
</script>
<style>
    .headStyle{
      width: 100%;
      height: 60px;
      background-color: #b3ad97;
    }
    .pictureStyle{
      height: 200px;
    }
    .pictureStyle img{
      width: 100%;
      height: 100%;
    }
    .menuStyle{
      width: 100%;
      height: 60px;
      line-height: 48px;
      background-color: #b4ac9a;
      margin: 0;
    }
    .menuStyle ul{
      list-style-type: none;
      margin: 0;
      padding: 0;
      overflow: hidden;
      margin-left: 30%;
    }
    .menuStyle ul li{
      float: left;
      margin-left: 50px;
      color: #ffffff;
      padding: 0 20px;
      cursor:pointer
    }
    .active{
      border-bottom:1px solid #ffffff;
    }
    .footStyle{
      width: 100%;
      height: 100px;
      background: -webkit-linear-gradient(left, #b3ad96, #a6a185, #97917a, #b3ad96);
      background: -o-linear-gradient(left, #b3ad96, #a6a185, #97917a, #b3ad96);
      background: linear-gradient(to right, #b3ad96, #a6a185, #97917a, #b3ad96);
    }
    .topDiv{
      float:right;
      margin-right:20px;
      padding-top:20px;
      cursor:pointer;
      color:#333
    }
    .topDiv ul{
      z-index: 10;
      display: none;
      position: absolute;
      background-color: #ffffff;
      color: #606266;
      margin: 0;
      padding: 0;
    }
    .topDiv ul li{
      width: 100px;
      height: 30px;
      line-height: 30px;
      text-align: center;
    }
    .topDiv:hover ul{
      display: block;
      margin-left: 0px;
      padding-left: 0px;
      overflow:hidden;
    }
    .topDiv:hover ul li{
      list-style: none;
      float: none;
    }
    .topDiv:hover ul li:hover {
      background-color: #f5f7fa;
    }
</style>