<template>
	<div>

		<el-container style="min-height: 885px; border: 1px solid #eee">
			<el-header>
        <span >基于Vue.js的博客分享平台后台管理</span>
        <span v-if="isAdminLogin" style="float:right;cursor:pointer;" @click="removeLogin()">注销</span>
        <span v-else style="float:right;cursor:pointer;" @click="loginDialogVisible=true">登录</span>
      </el-header>
		  	<el-container>
		    	<el-aside width="220px">
		    		<el-menu
                          :default-active="$route.path"
                          class="el-menu-vertical-demo" router>
                          <el-menu-item index="/manageHome/userManage">
                            <i class="el-icon-news"></i>
                            <span slot="title">用户管理</span>
                          </el-menu-item>
                          <el-menu-item index="/manageHome/examineManage">
                            <i class="el-icon-star-off"></i>
                            <span slot="title">审核管理</span>
                          </el-menu-item>
                          <el-menu-item index="3">
                            <i class="el-icon-view"></i>
                            <span slot="title">资讯管理</span>
                          </el-menu-item>
                          <el-menu-item index="/manageHome/paramManage">
                            <i class="el-icon-document"></i>
                            <span slot="title">码表管理</span>
                          </el-menu-item>
                        </el-menu>
		    	</el-aside>
		    	<el-main>
		    		<router-view></router-view>
		    	</el-main>
		  	</el-container>
		</el-container>

  <el-dialog title="管理员登录" :visible.sync = "loginDialogVisible" width="30%"  center>
    <el-form :model="form">
      <el-form-item label="帐 号" :label-width="formLabelWidth">
        <el-input v-model="form.adminName" placeholder="请输入管理员ID" clearable></el-input>
      </el-form-item>
      <el-form-item label="密 码" :label-width="formLabelWidth">
        <el-input v-model="form.adminPW" placeholder="请输入密码" type ="password" clearable></el-input>
      </el-form-item>
    </el-form> 
    <span slot="footer" class="dialog-footer">
      <el-button @click="goToIndex()">首页</el-button>
      <el-button @click="getAdminLogin()">登录</el-button>
    </span>
  </el-dialog>

	</div>
</template>
<script>
export default{
	data(){
		return{
      loginDialogVisible:false,     //登录弹框
      form:{                        //定义并绑定输入框
        adminName:'',
        adminPW:''
      },
      formLabelWidth:'100px',         //label宽度
      isAdminLogin:false,
		}
	},
	mounted(){
    if(!sessionStorage.getItem("adminId")){
      this.loginDialogVisible = true;
    }
	},
	methods:{
    getAdminLogin(){
      this.$ajax.post('http://localhost:8080/GraduationDesign/adminLogin', this.$qs.stringify({
          userId:this.form.adminName,
          password:this.form.adminPW
        })).then(res=>{
            if(res.data.code==1){
                this.$message.success(res.data.msg);
                sessionStorage.setItem('adminId',res.data.UID);
                this.loginDialogVisible = false;
                this.isAdminLogin = true;
            }else{
                this.$message.error(res.data.msg);
            }
        })
    },
    goToIndex(){
      sessionStorage.removeItem("visitedId");
      sessionStorage.removeItem("menuIndex");
      this.$router.push("/index");
    },
    removeLogin(){
      this.$ajax.post('http://localhost:8080/GraduationDesign/adminCancel', this.$qs.stringify({
          adminId:sessionStorage.getItem("adminId")
        })).then(res=>{
            if(res.data.code==1){
                this.$message.success(res.data.msg);
                sessionStorage.removeItem("adminId");
                this.isAdminLogin = false;
                this.$router.push("/manageHome");
            }else{
                this.$message.error(res.data.msg);
            }
        })
    }
	}
}
</script>
<style>
.el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
    height: 100%;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>