<template>
	<div>
		<el-form :inline="true" :model="formData" class="demo-form-inline">
		  <el-form-item label="标题">
		    <el-input v-model="formData.title" placeholder="标题"></el-input>
		  </el-form-item>
		  <el-form-item label="分类">
			    <el-select v-model="formData.type" clearable placeholder="选择类型">
			    	<el-option
     					v-for="item in options"
      					:key="item.value"
      					:label="item.label"
      					:value="item.value">
   					 </el-option>
			    </el-select>
			</el-form-item>
		  <el-form-item>
		    <el-button type="primary" @click="onSearch()">查询</el-button>
		  </el-form-item>
		</el-form>

		<div style="margin:10px;border:1px solid #dcdfe6">
		  	<el-table :data="tableData" style="width: 100%">
				<el-table-column prop="createDate" label="时间" width="200"></el-table-column>
				<el-table-column prop="title" label="标题" width="400"></el-table-column>
				<el-table-column prop="abstract" label="摘要"  :formatter="formatter"></el-table-column>
				<el-table-column prop="typeName" label="类别" width="200" >
					<template slot-scope="scope">
						<el-tag type="success" disable-transitions>{{scope.row.typeName}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="操作" width="150">
					<template slot-scope="scope">
			        	<el-button @click.native.prevent="changeIt(scope.row.id,scope.row.userId, '1')" type="text"size="small">通过</el-button>
			        	<el-button @click.native.prevent="changeIt(scope.row.id,scope.row.userId, '2')" type="text"size="small">退回</el-button>
			      	</template>
			    </el-table-column>
		  	</el-table>
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

        <el-dialog title="提示" v-model="dialogVisible" :visible.sync="dialogVisible" size="tiny" width="30%" center>
            <span>确定要执行该操作吗</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click='changeStatus()'>确 定</el-button>
            </span>
        </el-dialog>

	</div>
</template>
<script>
export default{
	data(){
		return{
			formData:{
				title:'',
				type:''
			},
			options:[],
			tableData:[],
			currentPage: 1,
            pageNumber:10,
            totalNumber:0,
            dialogVisible:false,
            checkedId:'',		//博客ID
            nums:'',			//博客状态
            checkUserId:''		//用户ID
		}
	},
	mounted () {
		this.getParamList();
		this.getExamineManageList();
	},
	methods:{
		getParamList(){
          this.$ajax.post('http://localhost:8080/GraduationDesign/getParamListInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('adminId'),
                paramCode: 'type'
            })).then(res=>{
                this.options = res.data;
            })
        },
		getExamineManageList(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getExamineBlogInfo', this.$qs.stringify({
					adminId:sessionStorage.getItem("adminId"),
					title:this.formData.title,
					type:this.formData.type,
                    currentPage:this.currentPage,
                    pageNumber:this.pageNumber
                })).then(res=>{
                    this.tableData = res.data;
              
                });

                this.$ajax.post('http://localhost:8080/GraduationDesign/getExamineBlogCount', this.$qs.stringify({
                    adminId:sessionStorage.getItem("adminId"),
					title:this.formData.title,
					type:this.formData.type
                })).then(res=>{
                    this.totalNumber = res.data;
                })
		},
		formatter(row, column) {//格式化内容
    		return row.abstract;
  		},
  		handleCurrentChange(val) {
            this.currentPage = val;
            this.getExamineManageList();
        },
        handleSizeChange(val) {
            this.pageNumber = val;
            this.getExamineManageList();
        },
		onSearch(){
			this.getExamineManageList();
		},
		changeIt(id,userId,num){
			this.dialogVisible = true;
			this.checkedId = id;
			this.checkUserId = userId;
			this.nums = num;
		},
		changeStatus(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/examineBlog', this.$qs.stringify({
                    adminId:sessionStorage.getItem("adminId"),
					blogId:this.checkedId,
					userId:this.checkUserId,
					status:this.nums
                })).then(res=>{
                    if(res.data.code == 1){
                        this.$message.success(res.data.msg);
                        this.dialogVisible = false;
                        this.getExamineManageList();
                    }else{
                       this.$message.error(res.data.msg);
                       this.dialogVisible = false;
                    }
                })
		}
	}
}
</script>
<style>
	
</style>