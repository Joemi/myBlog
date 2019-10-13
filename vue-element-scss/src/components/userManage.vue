<template>
	<div>
		<el-form :inline="true" :model="formData" class="demo-form-inline">
		  <el-form-item label="用户名">
		    <el-input v-model="formData.userName" placeholder="用户名"></el-input>
		  </el-form-item>
		  <el-form-item>
		    <el-button type="primary" @click="onSearch()">查询</el-button>
		  </el-form-item>
		</el-form>

		<div style="margin:10px;border:1px solid #dcdfe6">
		  	<el-table :data="tableData" style="width: 100%">
				<el-table-column prop="createDate" label="时间" width="120"></el-table-column>
				<el-table-column prop="realName" label="昵称" width="120"></el-table-column>
				<el-table-column prop="age" label="年龄"  width="100"></el-table-column>
				<el-table-column prop="sexName" label="性别"  width="100"></el-table-column>
				<el-table-column prop="userStatus" label="状态" width="100" :filters="[{text:'正常',value:'正常'},{text:'禁用',value:'禁用'}]" 
				:filter-method="filterTag" filter-placement="bottom-end">
					<template slot-scope="scope">
						<el-tag :type="scope.row.userStatus === '正常'? 'success':'info'" disable-transitions>{{scope.row.userStatus}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="address" label="地址"  width="200"></el-table-column>
				<el-table-column prop="signature" label="个性签名" :formatter="formatter"></el-table-column>
				<el-table-column prop="constellation" label="星座"  width="100"></el-table-column>
				<el-table-column prop="mail" label="邮箱"  width="200"></el-table-column>
				<el-table-column label="操作" width="100">
					<template slot-scope="scope">
			        	<el-button @click.native.prevent="changeIt(scope.row.userId, 1)"type="text"size="small" v-if="scope.row.status == 0">禁用</el-button>
			        	<el-button @click.native.prevent="changeIt(scope.row.userId, 0)"type="text"size="small" v-if="scope.row.status == 1">恢复</el-button>
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
				userName:''
			},
			tableData:[],
			currentPage: 1,
            pageNumber:10,
            totalNumber:0,
            dialogVisible:false,
            checkedId:'',
            nums:0
		}
	},
	mounted () {
		this.getUserManageList();
	},
	methods:{
		getUserManageList(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getUserManageInfo', this.$qs.stringify({
					adminId:sessionStorage.getItem("adminId"),
					userName:this.formData.userName,
                    currentPage:this.currentPage,
                    pageNumber:this.pageNumber
                })).then(res=>{
                    this.tableData = res.data;
              
                });

                this.$ajax.post('http://localhost:8080/GraduationDesign/getUserManageCont', this.$qs.stringify({
                    adminId:sessionStorage.getItem("adminId"),
					userName:this.formData.userName,
                })).then(res=>{
                    this.totalNumber = res.data;
                })
		},
		formatter(row, column) {//格式化内容
    		return row.signature;
  		},
  		filterTag(value, row) {//过滤标签
    		return row.userStatus === value;
  		},
  		handleCurrentChange(val) {
            this.currentPage = val;
            this.getUserManageList();
        },
        handleSizeChange(val) {
            this.pageNumber = val;
            this.getUserManageList();
        },
		onSearch(){
			this.getUserManageList();
		},
		changeIt(userId,num){
			this.dialogVisible = true;
			this.checkedId = userId;
			this.nums = num;
		},
		changeStatus(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/changeUserStatus', this.$qs.stringify({
                    adminId:sessionStorage.getItem("adminId"),
					userId:this.checkedId,
					status:this.nums
                })).then(res=>{
                    if(res.data.cod==1){
                        this.$message.success(res.data.msg);
                        this.dialogVisible = false;
                        this.getUserManageList();
                    }else{
                       this.$message.error(res.data.msg);
                    }
                })
		}
	}
}
</script>
<style>
	
</style>