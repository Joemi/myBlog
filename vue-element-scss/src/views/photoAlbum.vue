<template>
<div class="bgcolorsFive">
	<div class="parent">
		<div class="child" v-for="picture in dataList">
			<img :src="picture.picturePath" @click="showCheckPic(picture.picturePath,picture.describes)">
			<p>{{picture.describes}}</p>
			<div style="margin-left:120px">
				<el-button type="primary" icon="el-icon-edit"  @click="showEditPic(picture.id,picture.picturePath,picture.describes)" v-if="isVisitor == false" circle></el-button>
				<el-button type="danger" icon="el-icon-delete" @click="deletepPic(picture.id,picture.picturePath)" circle></el-button>
			</div>
		</div>
<!-- 		<div class="child">
			<img src="../image/4.jpg">
			<p>eqeqee</p>
			<div style="margin-left:120px">
				<el-button type="primary" icon="el-icon-edit" circle></el-button>
				<el-button type="danger" icon="el-icon-delete" circle></el-button>
			</div>
		</div> -->
		<div class="child" v-if="isVisitor == false">
			<el-upload 
		        class="avatar-uploader"
		        :action="uploadPic"
		        :show-file-list="false"
		        :on-success="handleAvatarSuccess"
		        :before-upload="beforeAvatarUpload">
		        <i class="el-icon-plus avatar-uploader-icon" style="width:216px;height:216px;line-height:216px"></i>
		    </el-upload>
		</div>

		<el-dialog title="提示" v-model="dialogVisible" :visible.sync="dialogVisible" size="tiny" width="30%" center>
            <span>确定要删除吗</span>
            <span slot="footer" class="dialog-footer">
            	<el-button @click="dialogVisible = false">取 消</el-button>
            	<el-button type="primary" @click='deleteIt()'>确 定</el-button>
            </span>
        </el-dialog>

		<el-dialog  v-model="dialogVisibles" :visible.sync="dialogVisibles" size="tiny" center>
            <img :src="showPicturePath" style="width:100%">
            <!-- <img src="../image/4.jpg" style="width:100%"> -->
            <p v-if="checkPic">{{showDescribes}}</p>
            <el-input  v-else type="textarea" :rows="2" v-model="showDescribes" placeholder="请输入图片描述"></el-input>
            <span v-if="checkPic" slot="footer" class="dialog-footer">
            	<el-button @click="dialogVisibles = false">返 回</el-button>
            </span>
            <span v-else slot="footer" class="dialog-footer">
            	<el-button @click="dialogVisibles = false">取 消</el-button>
            	<el-button type="primary" @click='editPicture()'>修 改</el-button>
            </span>
        </el-dialog>
	</div>
</div>
</template>
<script>
	export default{
        data(){
            return {
            	isVisitor:false,               //设置是否为访客
            	dataList:{},					//图片列表
            	newPath:'',						//保存新的图片路径
            	dialogVisible:false,			//删除提示框隐藏
            	pictureId:'',					//存删除图片ID
            	deletePath:'',					//存删除图片路径
            	dialogVisibles:false,			//查看图片框是否隐藏
            	showDescribes:'',				//绑定描述内容
            	checkPic:false,					//是否为查看（否为编辑）
            	showPicturePath:'',				//绑定图片路径
            	describesId:'',					//存被编辑（描述）图片的ID
            }
        },
        created(){
        	if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
                this.isVisitor = true;
                getPictureListByVisiter();

            }
            else{
            	this.getPictureList();
            }
        },
        computed:{
        	uploadPic(){
        		return 'http://localhost:8080/GraduationDesign/upload';
      		}
        },
        methods:{
        	getPictureListByVisiter(){
        		var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getPictureList', this.$qs.stringify({
                    id: sessionStorage.getItem('visitedId')
                })).then(res => {
                    that.dataList = res.data;
                    for(var i = 0;i < res.data.length; i++){
                    	that.dataList[i].picturePath = "http://localhost:8080/GraduationDesign/displayImage?path=" + res.data[i].picturePath;
                    }
                })
        	},
        	getPictureList(){
        		var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getPictureList', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId')
                })).then(res => {
                    that.dataList = res.data;
                    for(var i = 0;i < res.data.length; i++){
                    	that.dataList[i].picturePath = "http://localhost:8080/GraduationDesign/displayImage?path=" + res.data[i].picturePath;
                    }
                })
        	},
		    handleAvatarSuccess(res, file){
		        //this.ruleForm.picturePath = URL.createObjectURL(file.raw);
		        this.newPath = res.path;

		        this.$ajax.post('http://localhost:8080/GraduationDesign/addPicture', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    picturePath: this.newPath
                })).then(res => {
		        	if(res.data.code == 1){
		              this.$message.success(res.data.msg);
		              this.getPictureList();
		            }
		            else{
		              this.$message.error(res.data.msg);
		            }
		        })
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

		    deletepPic(picId,picPath){
		    	this.dialogVisible = true;
		    	this.pictureId = picId;
		    	this.deletePath = picPath;
		    },
		    deleteIt(){
		    	this.$ajax.post('http://localhost:8080/GraduationDesign/deletePicture', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    pictureId: this.pictureId,
                    picturePath: this.deletePath
                })).then(res => {
		        	if(res.data.code == 1){
		              this.$message.success(res.data.msg);
		              this.dialogVisible = false;
		              this.getPictureList();
		            }
		            else{
		              this.$message.error(res.data.msg);
		              this.dialogVisible = false;
		            }
		        })

		    },

		    showEditPic(id,picpath,describes){
		    	this.dialogVisibles = true;
		    	this.checkPic = false;
		    	this.describesId = id;
		    	this.showDescribes = describes;
		    	this.showPicturePath = picpath;
		    },

		    showCheckPic(picpath,describes){
		    	this.dialogVisibles = true;
		    	this.checkPic = true;
		    	this.showDescribes = describes;
		    	this.showPicturePath = picpath;
		    },

		    editPicture(){
		    	this.$ajax.post('http://localhost:8080/GraduationDesign/updateDescribe', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    pictureId: this.describesId,
                    describe: this.showDescribes
                })).then(res => {
		        	if(res.data.code == 1){
		              this.$message.success(res.data.msg);
		              this.dialogVisibles = false;
		              this.getPictureList();
		            }
		            else{
		              this.$message.error(res.data.msg);
		              this.dialogVisibles = false;
		            }
		        })
		    }

        }

    }
</script>
<style>
	.bgcolorsFive{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
/*	.centerContentFive{
		width: 1000px;
		height: auto;
		margin: 0 auto;
		background-color: #ffffff;
	}*/
	.parent{
		width:1000px;
		/*height: 100%;*/
		min-height: 523px;
		-moz-column-count:4;
		-webkit-column-count:4;
		column-count:4;
		margin: 0 auto;
		background-color: #ffffff;
	}
	.child{
		margin-bottom: 20px;
		-moz-page-break-inside:avoid;
		-webkit-column-break-inside:avoid;
		break-inside: avoid;
		color: #fff;
		border-radius: 5px;
		box-shadow: 0px 1px 2px rgba(0,0,0,0.3);
		padding: 10px;
	}
	.child img{
		width: 100%;
		max-width: 220px;
		height: auto;
	}
	.child p{
		color: #9c8888;
	}
</style>