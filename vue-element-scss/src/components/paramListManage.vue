<template>
	<div>

	<el-form :inline="true" :model="formData" class="demo-form-inline">
	  	<el-form-item label="名称">
	    	<el-input v-model="formData.paramName" placeholder="名称"></el-input>
	  	</el-form-item>
	    	<el-button type="primary" @click="onSearch()">查询</el-button>
	  	</el-form-item>
	  	</el-form-item style="margin-left:200px">
	    	<el-button type="primary" @click="openAdd()">新增</el-button>
	  	</el-form-item>
	</el-form>

	<div style="margin:10px;border:1px solid #dcdfe6">
	  	<el-table :data="tableData" style="width: 100%">
			<el-table-column prop="paramCode" label="码表代码" width="300"></el-table-column>
			<el-table-column prop="paramName" label="码表名称" width="300"></el-table-column>
			<el-table-column prop="paramValue" label="码表值"  width="300"></el-table-column>
			<el-table-column prop="paramText" label="码表内容" :formatter="formatter" ></el-table-column>
			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
		        	<el-button @click.native.prevent="openDel(scope.row.id)" type="text"size="small">删除</el-button>
		        	<el-button @click.native.prevent="openEdit(scope.row)" type="text"size="small">编辑</el-button>
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


    	<el-dialog title="新增码表" v-model="dialogVisible" :visible.sync="dialogVisible" size="tiny" width="30%" center>
            <el-form :model="addForm">
			    <el-form-item label="码表代码" label-width="120px">
			      <el-input v-model="addForm.paramCode" auto-complete="off" type="text" placeholder="请输入码表代码"></el-input>
			    </el-form-item>
			    <el-form-item label="码表名称" label-width="120px">
			      <el-input v-model="addForm.paramName" auto-complete="off" type="text" placeholder="请输入码表名称"></el-input>
			    </el-form-item>
			    <el-form-item label="码表值" label-width="120px">
			      <el-input v-model="addForm.paramValue" auto-complete="off" type="text" placeholder="请输入码表值"></el-input>
			    </el-form-item>
			    <el-form-item label="码表内容" label-width="120px">
			      <el-input v-model="addForm.paramText" auto-complete="off" type="text" placeholder="请输入码表内容"></el-input>
			    </el-form-item>
			</el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click='addParamList()'>确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="提示" v-model="dialogVisibleDel" :visible.sync="dialogVisibleDel" size="tiny" width="30%" center>
            <span>确定要执行该操作吗</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleDel = false">取 消</el-button>
                <el-button type="primary" @click='deleteParamList()'>确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="编辑码表" v-model="dialogVisibleEdit" :visible.sync="dialogVisibleEdit" size="tiny" width="30%" center>
            <el-form :model="editForm">
			    <el-form-item label="码表代码" label-width="120px">
			      <el-input v-model="editForm.paramCode" auto-complete="off" type="text" :disabled="true"></el-input>
			    </el-form-item>
			    <el-form-item label="码表名称" label-width="120px">
			      <el-input v-model="editForm.paramName" auto-complete="off" type="text" :disabled="true"></el-input>
			    </el-form-item>
			    <el-form-item label="码表值" label-width="120px">
			      <el-input v-model="editForm.paramValue" auto-complete="off" type="text" placeholder="请输入码表值"></el-input>
			    </el-form-item>
			    <el-form-item label="码表内容" label-width="120px">
			      <el-input v-model="editForm.paramText" auto-complete="off" type="text" placeholder="请输入码表内容"></el-input>
			    </el-form-item>
			</el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisibleEdit = false">取 消</el-button>
                <el-button type="primary" @click='editParamList()'>确 定</el-button>
            </span>
        </el-dialog>

	</div>
</template>
<script>
export default{
	data(){
		return{
			formData:{
				paramName:''
			},
			tableData:[],
			currentPage: 1,
            pageNumber:10,
            totalNumber:0,
            dialogVisible:false,
            addForm:{},
            dialogVisibleDel:false,
            deleteId:'',
            dialogVisibleEdit:false,
            editForm:{}
		}
	},
	mounted () {
		this.getParamInfo();
	},
	methods:{
		onSearch(){
			this.getParamInfo();
		},
		handleCurrentChange(val) {
            this.currentPage = val;
            this.getParamInfo();
        },
        handleSizeChange(val) {
            this.pageNumber = val;
            this.getParamInfo();
        },
        formatter(row, column) {//格式化内容
    		return row.paramText;
  		},
        getParamInfo(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getParamManageInfo', this.$qs.stringify({
				adminId:sessionStorage.getItem("adminId"),
				paramName:this.formData.paramName,
                currentPage:this.currentPage,
                pageNumber:this.pageNumber
            })).then(res=>{
                this.tableData = res.data;
          
            });

            this.$ajax.post('http://localhost:8080/GraduationDesign/getParamManageCount', this.$qs.stringify({
                adminId:sessionStorage.getItem("adminId"),
				paramName:this.formData.paramName
            })).then(res=>{
                this.totalNumber = res.data;
            })
        },
        openAdd(){
        	this.dialogVisible = true;
        },
        addParamList(){
        	this.$ajax.post('http://localhost:8080/GraduationDesign/addParamList', this.$qs.stringify({
                adminId:sessionStorage.getItem("adminId"),
				condition: JSON.stringify(this.addForm)
            })).then(res=>{
                if(res.data.code == 1){
		            this.$message.success(res.data.msg);
		            this.dialogVisible = false;
		            this.getParamInfo();
		        }
		        else{
		           	this.$message.error(res.data.msg);
		           	this.dialogVisible = false;
		        }
            })
        },
        openDel(id){
        	this.dialogVisibleDel = true;
        	this.deleteId = id;
        },
        deleteParamList(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/delParamList', this.$qs.stringify({
                adminId:sessionStorage.getItem("adminId"),
				paramId: this.deleteId
            })).then(res=>{
                if(res.data.code == 1){
		            this.$message.success(res.data.msg);
		            this.dialogVisibleDel = false;
		            this.getParamInfo();
		        }
		        else{
		           	this.$message.error(res.data.msg);
		           	this.dialogVisibleDel = false;
		        }
            })
        },
        openEdit(data){
        	this.dialogVisibleEdit = true;
        	this.editForm = data;
        },
        editParamList(){
        	if(this.editForm.paramValue == '' || this.editForm.paramText == ''){
        		this.$message.error("请将表单数据填写完整");
        	}
        	else{
        		this.$ajax.post('http://localhost:8080/GraduationDesign/editParamList', this.$qs.stringify({
	                adminId:sessionStorage.getItem("adminId"),
					paramId: this.editForm.Id,
					paramValue: this.editForm.paramValue,
					paramText: this.editForm.paramText
	            })).then(res=>{
	                if(res.data.code == 1){
			            this.$message.success(res.data.msg);
			            this.dialogVisibleEdit = false;
			            this.getParamInfo();
			        }
			        else{
			           	this.$message.error(res.data.msg);
			           	this.dialogVisibleEdit = false;
			           	this.getParamInfo();
			        }
	            });
        	}
        }
	}
}
</script>
<style>
	
</style>