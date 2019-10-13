<template>
	<div class="bgcolorsThree">
		<div class="centerContentThree">
      <div class="title">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="博文标题" prop="title">
            <el-input v-model="ruleForm.title" style="width:220px" placeholder="请输入标题"></el-input>
          </el-form-item>
          <el-form-item label="博文类型" prop="type">
            <el-select v-model="ruleForm.type" placeholder="请选择博文类型">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="博文摘要" prop="abstract_" v-if="isEdit">
            <el-input type="textarea" v-model="ruleForm.abstract_" style="width:400px" placeholder="请输入摘要"></el-input>
          </el-form-item>
          <el-form-item label="博文摘要" prop="abstract_" v-else>
            <el-input type="textarea" v-model="ruleForm.abstract_" style="width:400px" placeholder="请输入摘要"></el-input>
          </el-form-item>
        </el-form>
      </div>
			<markdown-editor v-model="ruleForm.content" :configs="configs" ref="markdownEditor"></markdown-editor>
      <el-upload style="padding:10px"
        class="avatar-uploader"
        :action="uploadUrl"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <img v-if="ruleForm.picturePath" :src="ruleForm.picturePath" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

<!--       <div class="avatar-uploader" style="padding:10px">
        <div tabindex="0" class="el-upload el-upload--text" @click="fileSelect()">
          <img v-if="ruleForm.imageUrl" :src="ruleForm.imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <input type="file" id="fileUpload" name="file" class="el-upload__input" @change="fileChange" 
          accept="image/png,image/jpg,image/jpeg">
        </div>
      </div> -->

      <div style="text-align:center;padding:20px">
        <span v-if="isEdit">
          <el-button   type="primary" @click="editBlog()">更新</el-button>
        </span>
        <span v-else>
          <el-button  type="primary" @click="submitBlog()">提交</el-button>
        </span>
        <el-button @click="getBack()">返回</el-button>
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
        ruleForm:{
          title:'',
          type:'',
          abstract_:'',
          content:'' ,                               // markdown编辑器内容
          picturePath:''
        },
        rules:{
          title:[{ required: true, message: '请输入标题', trigger: 'blur' }],
          type:[{ required: true, message: '请选择类型', trigger: 'change' }],
          abstract_:[{ required: true, message: '请输入摘要', trigger: 'blur' }],
          abstract:[{ required: true, message: '请输入摘要', trigger: 'blur' }]
        },
        options: [],
        pictureFile:'',
        isEdit:false,
        newPath:''    //保存图片，防止为了展示而改变图片路径
  		}
  	},
  	computed: {
    	simplemde() {
      	return this.$refs.markdownEditor.simplemde;
    	},
      uploadUrl(){
        return 'http://localhost:8080/GraduationDesign/upload';
      }
  	},
  	mounted(){
      this.getParamList();
      if(sessionStorage.getItem('articleId')){
          this.isEdit = true;
          this.getBlogInfo();
      }
  	},
    methods:{
      getParamList(){
          // this.ruleForm.imageUrl = "http://localhost:8080/GraduationDesign/displayImage?path=F:/DesignMe/vue-element-scss/src/image/Lotus.jpg";
          this.$ajax.post('http://localhost:8080/GraduationDesign/getParamListInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                paramCode: 'type'
            })).then(res=>{
                this.options = res.data;
            })
        },
        getBlogInfo(){
          this.$ajax.post('http://localhost:8080/GraduationDesign/checkBlogOne', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                blogId: sessionStorage.getItem('articleId')
            })).then(res=>{
                this.ruleForm = res.data;
                if(this.ruleForm.picturePath != null && this.ruleForm.picturePath !=''){
                  this.newPath = this.ruleForm.picturePath;
                  this.ruleForm.picturePath = "http://localhost:8080/GraduationDesign/displayImage?path="+this.ruleForm.picturePath;
                }
            })
        },

      handleAvatarSuccess(res, file){
        this.ruleForm.picturePath = URL.createObjectURL(file.raw);
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

      getBack(){
        sessionStorage.removeItem('articleId');
        this.$router.push("/home/blog");
      },
      submitBlog(){
        this.$ajax.post('http://localhost:8080/GraduationDesign/addBlog', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                condition: JSON.stringify(this.ruleForm),
                picturePath:this.newPath
        })).then(res=>{
            if(res.data.code == 1){
              this.$message.success(res.data.msg);
              this.$router.push("/home/blog");
            }
            else{
              this.$message.error(res.data.msg);
            }
        })
      },
      editBlog(){
        this.$ajax.post('http://localhost:8080/GraduationDesign/editBlog', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                blogId: sessionStorage.getItem('articleId'),
                condition: JSON.stringify(this.ruleForm),
                picturePath:this.newPath
        })).then(res=>{
            if(res.data.code == 1){
            this.$message.success(res.data.msg);
            sessionStorage.removeItem('articleId');
            this.$router.push("/home/blog");
          }
          else{
            this.$message.error(res.data.msg);
          }
        })
      },
    }
}
</script>
<style>
@import '~simplemde/dist/simplemde.min.css';
@import '~highlight.js/styles/atom-one-dark.css';
@import '~github-markdown-css';
	.bgcolorsThree{
		width: 100%;
		height: 100%;
		background-color: #e8e9d6;
	}
	.centerContentThree{
		width: 1000px;
		height: auto;
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
</style>