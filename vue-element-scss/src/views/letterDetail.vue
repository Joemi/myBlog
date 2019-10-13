<template>
<div class="bgcolorsThreeLD">
	<div class="centerContentThreeLD">
		<el-form ref="form" :model="formData" label-width="150px">
			<el-form-item label="邮件标题：">{{formData.title}}</el-form-item>
			<el-form-item label="用户昵称：">{{formData.name}}</el-form-item>
			<el-form-item label="评论/留言内容：">{{formData.content}}</el-form-item>
			<el-form-item label="回复：">
				<el-input type="textarea" v-model="formData.recommend" style="width:400px"></el-input>
			</el-form-item>
			<el-form-item>
    			<el-button type="primary" @click="onSubmit()">发送</el-button>
    			<el-button @click="getBack()">返回</el-button>
  			</el-form-item>
		</el-form>
	</div>
</div>
</template>
<script>
	export default{
		data(){
			return{
				formData:{}
			}
		},
		mounted(){
			this.formData = JSON.parse(sessionStorage.getItem("letterInfo"));
			console.log(this.formData);
			console.log(sessionStorage.getItem("letterInfo"));
			if(this.formData.letterStatus == 0){
				this.readIt();
			}
        },
        methods:{
        	getBack(){
        		sessionStorage.removeItem("letterInfo");
        		this.$router.push("/home/letter");
        	},
        	onSubmit(){
        		this.$ajax.post('http://localhost:8080/GraduationDesign/replyLetter', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    visitorId:this.formData.visitorId,
                    content:this.formData.recommend
                })).then(res => {
                    if(res.data.code == 1){
                    	this.$message.success(res.data.msg);
                    	sessionStorage.removeItem("letterInfo");
        				this.$router.push("/home/letter");
                    }
                    else{
                    	this.$message.error(res.data.msg);
                    }
                })
        	},
        	readIt(){
        		this.$ajax.post('http://localhost:8080/GraduationDesign/readMessage', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    letterId:this.formData.id
                })).then(res => {

        		})
        	}
        }
	}
</script>
<style>
	.bgcolorsThreeLD{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContentThreeLD{
		width: 1000px;
		height: 100%;
		min-height: 483px;
		margin: 0 auto;
		background-color: #ffffff;
		padding:20px 0;
	}
</style>