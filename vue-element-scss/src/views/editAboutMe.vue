<template>
	<div class="bgcolorsTwo">
		<div class="centerContentTwo">
			<div style="margin:0px auto 20px 400px;padding-top:20px">
				<!-- <img src="../image/side.jpg" alt="点击修改图片" /> -->
				<!-- <div class="img_cover">change</div> -->
				<el-upload 
			        class="avatar-uploaderOne"
			        :action="uploadUrl"
			        :show-file-list="false"
			        :on-success="handleAvatarSuccess"
			        :before-upload="beforeAvatarUpload">
			        <img v-if="tableData.headPortrait" :src="tableData.headPortrait" class="avatarOne">
			        <i v-else class="el-icon-plus avatar-uploaderOne-icon"></i>
			     </el-upload>

			</div>
			<table class="basicMass">
				<tr>
					<td class="key">名称：</td>
					<td class="value"><input type="text" name="name" v-model="tableData.realName"></td>
				</tr>
				<tr>
					<td class="key">性别：</td>
					<td class="value">
						<el-radio v-model="tableData.sex" label="0">男</el-radio>
  						<el-radio v-model="tableData.sex" label="1">女</el-radio>
  					</td>
				</tr>
				<tr>
					<td class="key">年龄：</td>
					<td class="value"><input type="text" name="age" v-model="tableData.age"></td>
				</tr>
				<tr>
					<td class="key">星座：</td>
					<td class="value"><input type="text" name="constellation" v-model="tableData.constellation"></td>
				</tr>
				<tr>
					<td class="key">故乡：</td>
					<td class="value"><input type="text" name="address" v-model="tableData.address"></td>
				</tr>
				<tr>
					<td class="key">邮箱：</td>
					<td class="value"><input type="text" name="email" v-model="tableData.mail"></td>
				</tr>
				<tr>
					<td class="key">个性签名：</td>
					<td class="value"><textarea class="textareaStyle" name="signature" v-model="tableData.signature"></textarea></td>
				</tr>
			</table>
			<div>
				<input type="button" name="" value="返回" class="bottomStyle" style="margin-left:480px" @click="getBack">
				<input type="button" name="" value="保存" class="bottomStyle" style="margin-left:20px" @click="getChange">
			</div>
		</div>
	</div>
</template>
<script>
export default {
	data () {
        return {
        	tableData:{
        	},
        	newPath:''		//保存图片，防止为了展示而改变图片路径
        }
     },
	mounted(){
		this.getInfo();
	},
	computed: {
		uploadUrl(){
        	return 'http://localhost:8080/GraduationDesign/upload';
      	}
	},
	methods:{
		getInfo(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getUserInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId')
                })).then(res => {
					this.tableData = res.data;
					if(this.tableData.headPortrait != null && this.tableData.headPortrait !=''){
						this.tableData.headPortrait = "http://localhost:8080/GraduationDesign/displayImage?path="+this.tableData.headPortrait;
						this.newPath = this.tableData.headPortrait;
					}
			});
		},
		getBack(){
			this.$router.push("/home/aboutMe");
		},
		getChange(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/updateUserInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    uId:this.tableData.id,
                    condition:JSON.stringify(this.tableData),
                    picturePath:this.newPath
                })).then(res => {
					if(res.data.code == 1){
						this.$message.success(res.data.msg);
						this.$router.push("/home/aboutMe");
					}
					else{
						this.$message.error(res.data.msg);
					}
			});
		},

		handleAvatarSuccess(res, file){
	        this.tableData.headPortrait = URL.createObjectURL(file.raw);
	        this.newPath = res.path;
	    },
	    beforeAvatarUpload(file){
	        const isJPGAndPNG = file.type === 'image/jpeg' || file.type === 'image/png';
	        const isLt2M = file.size / 1024 / 1024 < 2;

	        if (!isJPGAndPNG) {
	          this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
	        }
	        if (!isLt2M) {
	          this.$message.error('上传头像图片大小不能超过 2MB!');
	        }
	        return isJPGAndPNG && isLt2M;
	    },
	}
}
</script>
<style>
	.bgcolorsTwo{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContentTwo{
		width: 1000px;
		height: auto;
		margin: 0 auto;
	}
	.centerContentTwoImg{
		width: 178px;
		height: 178px;
		border-radius: 50%;
		border:1px solid #ffffff;
		margin:20px auto 20px 400px;
		position: relative;
		cursor:pointer;
	}
	.img_cover{
		width: 180px;
		height: 180px;
		border-radius: 50%;
		background-color: rgba(0, 0, 0, .50);
		position: absolute;  
        left: 0px;  
        bottom: 0px;
        font-size: 25px;
        color: #ffffff;
        z-index: 2;
	}
	.basicMass{
  		height: auto;
  		width: 400px;
  		/*word-wrap: break-word;*/
  		margin:5px auto;
  		color:#333333;
  	}
  	.basicMass tr{
  		height: 40px;
  		line-height: 40px;
  	}
  	.basicMass tr .key{
  		width: 180px;
  		text-align: right;
  		padding-right: 5px;
  	}
  	.basicMass tr .value input[type="text"]{
		text-align: left;
		border-radius: 5px;
		border: 1px solid #D2D2D2;
		height: 25px;
  		line-height: 25px;
  		width: 160px;
  		vertical-align:middle;
  	}
  	.textareaStyle{
  		width:200px;
  		vertical-align:middle;
  		height:45px;
  		border-radius:5px;
  		border: 1px solid #D2D2D2;
  	}
  	.bottomStyle{
  		border:0;
  		border-radius: 10px;
  		color: #ffffff;
  		background: #cc5d63;
  		width: 100px;
  		text-align: center;
  		height:40px;
  		margin-bottom: 20px;
  		cursor:pointer;
  	}
  .avatar-uploaderOne .el-upload {
    border: 1px solid #ef3131;
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    background-color: #ffffff;
  }
  .avatar-uploaderOne .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploaderOne-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatarOne {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>