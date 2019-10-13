<template>
	<div class="bgcolors">
		<div class="centerContent">
			<ul class="cbp_tmtimeline">
				<li v-for="item in dataList">
					<time class="cbp_tmtime"><span>{{item.createDate.substring(5,10)}}</span> <span>{{item.createDate.substring(0,4)}}</span></time>
					<div class="cbp_tmicon"></div>
					<div class="cbp_tmlabel" >
						<h2 style="cursor:pointer" @click="viewThisBlog(item.id)">{{item.title}}</h2>
						<p><span class="blogpic" v-if="item.picturePath" ><img :src="getUrl(item.picturePath)"></span>{{item.abstract_}}</p>
                        <a  target="_blank" class="readmore"  @click="viewThisBlog(item.id)" >阅读全文</a>
					</div>
				</li>
			</ul>

			<div  v-if = "dataList == ''">
                <div style="text-align:center;padding-top:20px" >暂时还没有博文，立即去创建吧！</div>
            </div>
		</div>
	</div>
</template>
<script>
export default {
	data () {
        return {
        	dataList:[],
        	isVisitor:false               //设置是否为访客
        }
     },
     computed: {
  	},
	mounted(){
		if(sessionStorage.getItem('visitedId') && sessionStorage.getItem('visitedId') != sessionStorage.getItem('nameId')){
        	this.isVisitor = true;
        	this.getListByVisitor();
      	}
      	else{
      		this.getList();
      	}
	},
	methods:{
		getList(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getBlogInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('nameId'),
                title: '',
                type: '',
                currentPage: 1,
                pageNumber: 5
            })).then(res=>{
                this.dataList = res.data;
            })
		},
		getUrl(path){
        	return "http://localhost:8080/GraduationDesign/displayImage?path="+path;
      	},
      	viewThisBlog(id){
      		sessionStorage.setItem('articleId',id);
      		sessionStorage.setItem('menuIndex',3);
            this.$router.push("/home/checkBlog");
      	},

      	getListByVisitor(){
			this.$ajax.post('http://localhost:8080/GraduationDesign/getBlogInfo', this.$qs.stringify({
                id :  sessionStorage.getItem('visitedId'),
                title: '',
                type: '',
                currentPage: 1,
                pageNumber: 5
            })).then(res=>{
                this.dataList = res.data;
            })
		},
	}
}
</script>
<style>
	.bgcolors{
		width: 100%;
		height: auto;
		background-color: #e8e9d6;
	}
	.centerContent{
		width: 1000px;
		height: 100%;
		min-height: 523px;
		background-color: #ffffff;
		margin: 0 auto;
	}
	.cbp_tmtimeline { margin: 0; padding: 0; list-style: none; position: relative; }
	.cbp_tmtimeline:before { content: ''; position: absolute; top: 0; bottom: 0; width: 5px; background: #a7daf9; left: 158px; margin-left: -10px; }
	.cbp_tmtimeline > li { position: relative; }
	.cbp_tmtimeline > li .cbp_tmtime { display: block; width: 100px; padding-right: 30px; position: absolute; margin-left: 20px }
	.cbp_tmtimeline > li .cbp_tmtime span { display: block; text-align: right; }
	.cbp_tmtimeline > li .cbp_tmtime span:first-child { font-size: 14px; color: #bdd0db; }
	.cbp_tmtimeline > li .cbp_tmtime span:last-child { font-size: 22px; color: #a7daf9; }
	/*.cbp_tmtimeline > li:nth-child(odd) .cbp_tmtime span:last-child { color: #6cbfee; }*/
	.cbp_tmtimeline > li .cbp_tmlabel { margin: 0 30px 15px 180px; background: #a7daf9; color: #fff; padding: 2em; position: relative; border-radius: 5px; height: 150px; }
	/*.cbp_tmtimeline > li:nth-child(odd) .cbp_tmlabel { background: #a7daf9; }*/
	.cbp_tmtimeline > li .cbp_tmlabel h2 { margin-top: 0px; padding: 0 0 10px 0; font-size: 16px; text-shadow: rgba(14, 14, 14, 0.71) 1px 1px 1px; }
	.cbp_tmtimeline > li .cbp_tmlabel:after { right: 100%; border: solid transparent; content: " "; height: 0; width: 0; position: absolute; pointer-events: none; border-right-color: #3594cb; border-width: 10px; top: 10px; }
	/*.cbp_tmtimeline > li:nth-child(odd) .cbp_tmlabel:after { border-right-color: #579dc5; }*/
	.cbp_tmtimeline > li .cbp_tmicon { width: 10px; height: 10px; font-family: 'ecoico'; speak: none; font-style: normal; font-weight: normal; font-variant: normal; text-transform: none; font-size: 1.4em; line-height: 40px; -webkit-font-smoothing: antialiased; position: absolute; color: #fff; background: #46a4da; border-radius: 50%; box-shadow: 0 0 0 3px #afdcf8; text-align: center; left: 170px; top: 15px; margin: 0 0 0 -25px; }
	.blogpic { width: 200px; height: 120px; overflow: hidden; display: block; float: left; margin-right: 20px; }
	.blogpic img { width: 100%; height: auto; margin: auto; }
	a.readmore { background: #e65a62; color: #fff; padding: 5px 10px; float: right; margin: 20px 0 0 0; border-radius: 5px;text-decoration:none; position: absolute; bottom: 15%;right:4%;cursor:pointer;}
	.cbp_tmtimeline li .cbp_tmtime { -webkit-transition: all 1s; -moz-transition: all 1s; -o-transition: all 1s; }
	.cbp_tmtimeline li:hover .cbp_tmicon { box-shadow: 0 0 0 3px #ececec; }
	.cbp_tmtimeline li:hover .cbp_tmtime { background: #a7daf9; border-radius: 20px 0 0 20px; }
	.cbp_tmtimeline li:hover .cbp_tmtime span { color: #fff; }
</style>