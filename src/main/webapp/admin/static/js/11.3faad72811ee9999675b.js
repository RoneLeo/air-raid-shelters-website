webpackJsonp([11],{YIsC:function(t,e){},gAlH:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i={name:"basetable",data:()=>({activeName:"first",modelTitle:"添加文件",tableData:[],tableData1:[],modelVisible:!1,addForm:{},file:{},size:10,page:1,totalElements:0,size1:10,page1:1,totalElements1:0,loading:!0,addLoading:!1,dialogTPVisible:!1,wjlj:"",wjmc:""}),created(){this.getData(1,this.page,this.size)},computed:{},methods:{reset(){this.wjlj="",this.wjmc=""},closeClear(){this.$refs.addForm.resetFields()},handleClick(t,e){"first"===t.name?this.getData(1,this.page,this.size):this.getData(2,this.page1,this.size1),console.log(t,e)},delFile(t,e){this.$confirm("此操作将永久删除该文件, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(()=>{this.$axios.post("/file/delete",this.$qs.stringify({id:e.id})).then(t=>{this.$message.success("已删除！"),"first"==this.activeName?this.getData(1,this.page,this.size):this.getData(2,this.page1,this.size1)})}).catch(()=>{})},formatterWjlx(t){return this.$common.dictParse(t.wjlx,this.$dict.fileType)},lookFile(t,e){this.wjmc=e.wjmc,this.wjlj="http://182.151.22.247:8081"+e.wjlj,this.dialogTPVisible=!0},modelClose(t){this.$refs[t].resetFields()},getFile(t){this.file=t.target.files[0],this.addForm.file=this.file},handleCurrentChange(t){this.page=t,this.getData(1,this.page,this.size)},handleCurrentChange1(t){this.page1=t,this.getData(2,this.page1,this.size1)},getData(t,e,a){this.loading=!0,this.$axios.post("/file/findAllByGsidByPage",this.$qs.stringify({wjlx:t,page:e,size:a})).then(e=>{200==e.resCode&&(this.loading=!1,1===t?(this.tableData=e.data.content,this.totalElements=e.data.totalElements):(this.tableData1=e.data.content,this.totalElements1=e.data.totalElements))})},add(){this.addForm={},this.file={},this.modelVisible=!0},saveEdit(t){this.$refs.addForm.validate(t=>{if(t){this.addLoading=!0;let t=new FormData;for(let e in this.addForm)t.append(e,this.addForm[e]);let e={headers:{"Content-Type":"multipart/form-data"}};this.$axios.post("/file/add",t,e).then(t=>{this.addLoading=!1,this.modelVisible=!1,"first"==this.activeName?this.getData(1,this.page,this.size):this.getData(2,this.page1,this.size1)})}})}}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"table"},[a("div",{staticClass:"crumbs"},[a("el-breadcrumb",{attrs:{separator:"/"}},[a("el-breadcrumb-item",[a("i",{staticClass:"el-icon-lx-cascades"}),t._v(" 文件管理")])],1)],1),t._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],staticClass:"container"},[a("el-tabs",{on:{"tab-click":t.handleClick},model:{value:t.activeName,callback:function(e){t.activeName=e},expression:"activeName"}},[a("el-tab-pane",{attrs:{label:"资质证书",name:"first"}},[a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:t.tableData,"show-header":!1}},[a("el-table-column",{attrs:{prop:"wjmc",label:"文件名称"}}),t._v(" "),a("el-table-column",{attrs:{prop:"cjsj",label:"创建时间",width:"200"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",icon:"el-icon-lx-attention"},on:{click:function(a){t.lookFile(e.$index,e.row)}}},[t._v("点击查看\n                            ")]),t._v(" "),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){t.delFile(e.$index,e.row)}}},[t._v("删除\n                            ")])]}}])})],1),t._v(" "),a("div",{staticStyle:{padding:"28px 8px"}},[a("el-button",{staticStyle:{float:"left"},attrs:{type:"primary"},on:{click:t.add}},[t._v("上传文件")]),t._v(" "),a("el-pagination",{staticStyle:{float:"right"},attrs:{"current-page":t.page,"page-size":t.size,layout:"prev, pager, next",total:t.totalElements},on:{"current-change":t.handleCurrentChange}})],1)],1),t._v(" "),a("el-tab-pane",{attrs:{label:"banner图片",name:"second"}},[a("el-table",{ref:"multipleTable",staticClass:"table",attrs:{data:t.tableData1,"show-header":!1}},[a("el-table-column",{attrs:{prop:"wjmc",label:"文件名称"}}),t._v(" "),a("el-table-column",{attrs:{prop:"cjsj",label:"创建时间",width:"200"}}),t._v(" "),a("el-table-column",{attrs:{label:"操作",align:"center",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{type:"text",icon:"el-icon-lx-attention"},on:{click:function(a){t.lookFile(e.$index,e.row)}}},[t._v("点击查看\n                            ")]),t._v(" "),a("el-button",{staticClass:"red",attrs:{type:"text",icon:"el-icon-delete"},on:{click:function(a){t.delFile(e.$index,e.row)}}},[t._v("删除\n                            ")])]}}])})],1),t._v(" "),a("div",{staticStyle:{padding:"28px 8px"}},[a("el-button",{staticStyle:{float:"left"},attrs:{type:"primary"},on:{click:t.add}},[t._v("上传文件")]),t._v(" "),a("el-pagination",{staticStyle:{float:"right"},attrs:{"current-page":t.page1,"page-size":t.size1,layout:"prev, pager, next",total:t.totalElements1},on:{"current-change":t.handleCurrentChange1}})],1)],1)],1)],1),t._v(" "),a("el-dialog",{attrs:{title:t.modelTitle,visible:t.modelVisible,width:"40%","close-on-click-modal":!1},on:{"update:visible":function(e){t.modelVisible=e},closed:t.closeClear}},[a("el-form",{ref:"addForm",attrs:{model:t.addForm,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"文件名称",prop:"wjmc",rules:[{required:!0,message:"文件名称不能为空",trigger:"blur"}]}},[a("el-input",{model:{value:t.addForm.wjmc,callback:function(e){t.$set(t.addForm,"wjmc",e)},expression:"addForm.wjmc"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"文件类型",prop:"wjlx",rules:[{required:!0,message:"文件类型不能为空",trigger:"blur"}]}},[a("el-select",{attrs:{placeholder:"请选择文件类型"},model:{value:t.addForm.wjlx,callback:function(e){t.$set(t.addForm,"wjlx",e)},expression:"addForm.wjlx"}},t._l(this.$dict.fileType,function(t){return a("el-option",{key:t.id+Math.random(),attrs:{label:t.name,value:t.id}})}),1)],1),t._v(" "),a("el-form-item",{attrs:{label:"文件"}},[a("input",{attrs:{type:"file"},on:{change:function(e){t.getFile(e)}}})])],1),t._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:function(e){t.modelVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary",loading:t.addLoading},on:{click:function(e){t.saveEdit(t.addForm)}}},[t._v("确 定")])],1)],1),t._v(" "),a("el-dialog",{attrs:{title:t.wjmc,visible:t.dialogTPVisible,width:"60%"},on:{"update:visible":function(e){t.dialogTPVisible=e},closed:t.reset}},[a("div",{staticStyle:{width:"100%",height:"400px",display:"flex","justify-content":"center","align-items":"center"}},[a("img",{staticStyle:{"max-width":"100%","max-height":"100%",margin:"auto","border-radius":"5px"},attrs:{src:t.wjlj,alt:""}})])])],1)},staticRenderFns:[]};var s=a("C7Lr")(i,l,!1,function(t){a("YIsC")},"data-v-0fc55bfb",null);e.default=s.exports}});