<template>
<div class="bgcolorsThreel">
<div class="centerContentThreel">
	<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
  		<el-menu-item index="1">全部邮件</el-menu-item>
  		<el-menu-item index="2">未读邮件</el-menu-item>
  		<el-menu-item index="3">已读邮件</el-menu-item>
  	</el-menu>
  	<el-row style="padding:20px">
  		<el-button type="primary" icon="el-icon-message" @click="changeStatus(1)" >一键已读</el-button>
		<el-button type="danger" icon="el-icon-delete" @click="changeStatus(2)">一键删除</el-button>
  	</el-row>
  	<div style="margin:10px;border:1px solid #dcdfe6">
  	<el-table :data="tableData" style="width: 100%">
		<el-table-column prop="createDate" label="时间" width="180"></el-table-column>
		<el-table-column prop="name" label="昵称" width="180"></el-table-column>
		<el-table-column prop="title" label="标题" :formatter="formatter"></el-table-column>
		<el-table-column prop="statuName" label="状态" width="100" :filters="[{text:'已读',value:'已读'},{text:'未读',value:'未读'}]" 
		:filter-method="filterTag" filter-placement="bottom-end">
			<template slot-scope="scope">
				<el-tag :type="scope.row.statuName === '已读'? 'primary':'success'" disable-transitions>{{scope.row.statuName}}</el-tag>
			</template>
		</el-table-column>
		<el-table-column label="操作" width="120">
			<template slot-scope="scope">
				<el-button @click.native.prevent="checkRow(scope.$index, tableData)"type="text"size="small">查看</el-button>
	        	<el-button @click.native.prevent="deleteRow(scope.$index, tableData)"type="text"size="small">删除</el-button>
	      	</template>
	    </el-table-column>
  	</el-table>
  	</div>

  	<div style="margin-top:10px;margin-left:10px">
        <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageNumber"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalNumber">
        </el-pagination>
    </div>
</div>
</div>
</template>
<script>
	export default{
        data(){
        	return{
        		activeIndex:'1',
        		tableData:[],
        		currentPage: 1,
                pageNumber:10,
                totalNumber:0
        	}
        },
        mounted(){
        	this.getLm();
        },
        methods:{
        	handleSelect(key,keyPath){
        		this.activeIndex = key;
        		this.getLm();
        	},
        	formatter(row, column) {//格式化内容
        		return row.title;
      		},
      		filterTag(value, row) {//过滤标签
        		return row.letterStatus === value;
      		},

      		getLm(){
      			var that = this;
                this.$ajax.post('http://localhost:8080/GraduationDesign/getLetterInfo', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    currentPage: that.currentPage,
                    pageNumber: that.pageNumber,
                    type:that.activeIndex
                })).then(res => {
                    that.tableData = res.data;
                })

                this.$ajax.post('http://localhost:8080/GraduationDesign/getLetterCount', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    type:that.activeIndex
                })).then(res => {
                    that.totalNumber = res.data;
                })

            },

            changeStatus(type){
            	var that = this;
            	var emailType = "";
            	if(that.activeIndex == 1){
            		emailType = 2;
            	}
            	else if(that.activeIndex == 2){
            		emailType = 0;
            	}
            	else{
            		emailType = 1;
            	}
            	this.$ajax.post('http://localhost:8080/GraduationDesign/changeLetterStatus', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    type:type,
                    eType: emailType
                })).then(res => {
                    if(res.data.code == 1){
                    	that.$message.success(res.data.msg);
                    	this.getLm();
                    }
                    else if(res.data.code == 2){
                    	that.$message.warning(res.data.msg);
                    }
                    else{
                    	that.$message.error(res.data.msg);
                    }
                })
            },

      		handleCurrentChange(val) {
            	this.currentPage = val;
            	this.getLm();
            },
            handleSizeChange(val) {
            	this.pageNumber = val;
            	this.getLm();
            },

        	deleteRow(index, data){
        		this.$ajax.post('http://localhost:8080/GraduationDesign/deleteLetter', this.$qs.stringify({
                    id: sessionStorage.getItem('nameId'),
                    letterId:data[index].id
                })).then(res => {
                    if(res.data.code == 1){
                    	this.$message.success(res.data.msg);
                    	this.getLm();
                    }
                    else{
                    	this.$message.error(res.data.msg);
                    }
                })
        	},
        	checkRow(index, data){
        		sessionStorage.setItem("letterInfo", JSON.stringify(data[index]));
        		this.$router.push("/home/letterDetail");
        	}
        }
    }
</script>
<style>
	.bgcolorsThreel{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContentThreel{
		width: 1000px;
		min-height: 523px;
		margin: 0 auto;
		background-color: #ffffff;
	}
</style>