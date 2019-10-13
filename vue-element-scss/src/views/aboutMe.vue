<template>
	<div class="bgcolorsTwo">
		<div class="centerContentTwo">
			<!-- <img src="../image/side.jpg" class="centerContentTwoImg" /> -->
			 <img v-if="tableData.headPortrait" :src="tableData.headPortrait" class="centerContentTwoImg" />
			 <i v-else class="el-icon-plus centerContentTwoImg" style="text-align:center;line-height: 178px;"></i>
			<table class="basicMass">
				<tr>
					<td class="key">名称：</td>
					<td class="value">{{tableData.realName}}</td>
				</tr>
				<tr>
					<td class="key">性别：</td>
					<td class="value">{{tableData.sexName}}</td>
				</tr>
				<tr>
					<td class="key">年龄：</td>
					<td class="value">{{tableData.age}}</td>
				</tr>
				<tr>
					<td class="key">星座：</td>
					<td class="value">{{tableData.constellation}}</td>
				</tr>
				<tr>
					<td class="key">故乡：</td>
					<td class="value">{{tableData.address}}</td>
				</tr>
				<tr>
					<td class="key">邮箱：</td>
					<td class="value">{{tableData.mail}}</td>
				</tr>
				<tr>
					<td class="key">个性签名：</td>
					<td class="value">{{tableData.signature}}</td>
				</tr>
			</table>
			<div><input type="button" name="" value="编辑" class="bottomStyle" @click="editIt" v-if="isVisitor==false"></div>
		</div>
	</div>
</template>
<script>
export default {
	data () {
        return {
        	tableData:{},
        	isVisitor:false               //设置是否为访客
        }
     },
	mounted(){
		 if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
	        this.isVisitor = true;
	        this.getInfoByVisitor();
	      }
	      else{
	        this.getInfo();
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
					}
					if(this.tableData.sex == 0){
						this.tableData.sexName = "男";
					}
					else if(this.tableData.sex == 1){
						this.tableData.sexName = "女";
					}
					else{
						this.tableData.sexName = "";
					}
			});
		},
		getInfoByVisitor(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getUserInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('visitedId')
                })).then(res => {
					this.tableData = res.data;
					if(this.tableData.headPortrait != null && this.tableData.headPortrait !=''){
						this.tableData.headPortrait = "http://localhost:8080/GraduationDesign/displayImage?path="+this.tableData.headPortrait;
					}
					if(this.tableData.sex == 0){
						this.tableData.sexName = "男";
					}
					else if(this.tableData.sex == 1){
						this.tableData.sexName = "女";
					}
					else{
						this.tableData.sexName = "";
					}
			});
		},
		editIt(){
			this.$router.push("editAboutMe");
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
  	.basicMass tr .value{
		text-align: left;
		word-wrap: break-word;
  		word-break: break-all;
  		vertical-align:middle;
  	}
  	.bottomStyle{
  		border:0;
  		border-radius: 10px;
  		color: #ffffff;
  		background: #cc5d63;
  		width: 100px;
  		text-align: center;
  		height:40px;
  		margin-left: 600px;
  		margin-bottom: 20px;
  		cursor:pointer
  	}
</style>